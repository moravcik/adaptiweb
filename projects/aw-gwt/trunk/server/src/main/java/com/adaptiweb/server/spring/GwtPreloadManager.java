package com.adaptiweb.server.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;

public class GwtPreloadManager {
	
	private static final String ALL_MODULES = null;

	@Autowired
	protected SerializationPolicyProvider policyProvider;
	
	private final Map<String, List<Loader>> loaders = new HashMap<String, List<Loader>>();
	
	@Autowired
	public void init(Collection<RemoteServiceServlet> gwtServices) {
		for (RemoteServiceServlet service : gwtServices) {
			Class<? extends RemoteServiceServlet> serviceType = service.getClass();
			for (Class<?> iterfaceType : findRemoteSeviceInterfaces(serviceType)) {
				for (Method method : iterfaceType.getMethods()) {
					Preload annotation = getPreloadAnnotation(method, serviceType);
					if (annotation != null) register(service, method, annotation);
				}
			}
		}
	}
	
	private void register(RemoteServiceServlet service, Method method, Preload annotation) {
		String name = annotation.name();
		if (name.length() == 0) name = getDefaultName(method);
		Loader loader = new Loader(method, service, name);
		
		for (String module : annotation.modules()) putLoader(module, loader);
		if (annotation.modules().length == 0) putLoader(null, loader);
	}

	private String getDefaultName(Method method) {
		Class<?> returnType = method.getReturnType();
		if (returnType.isArray())
			return returnType.getComponentType().getName().replace('.', '_') + "_array";
		return returnType.getName().replace('.', '_');
	}

	private void putLoader(String module, Loader loader) {
		List<Loader> list = loaders.get(module);
		if (list == null) loaders.put(module, list = new ArrayList<Loader>());
		list.add(loader);
	}

	private Preload getPreloadAnnotation(Method method, Class<? extends RemoteServiceServlet> serviceType) {
		Method implMethod = ReflectionUtils.findMethod(serviceType, method.getName(), method.getParameterTypes());
		return implMethod.isAnnotationPresent(Preload.class) ? implMethod.getAnnotation(Preload.class) :
			method.isAnnotationPresent(Preload.class) ? method.getAnnotation(Preload.class) :
			null;
	}

	private Iterable<Class<?>> findRemoteSeviceInterfaces(Class<? extends RemoteServiceServlet> gwtServiceType) {
		ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		for (Class<?> it :  collectAllInterfaces(gwtServiceType))
			if (it != RemoteService.class && RemoteService.class.isAssignableFrom(it))
				result.add(it);
		return result;
	}

	public Map<String, String> getPreloadValues(String gwtModul) {
		Map<String, String> result = new HashMap<String, String>(determineResultSize(gwtModul));

		putValues(ALL_MODULES, result);
		putValues(gwtModul, result);
		
		return result;
	}
	
	private void putValues(String moduleName, Map<String, String> result) {
		if (loaders.containsKey(moduleName)) {
			for (Loader loader : loaders.get(moduleName)) {
				String value = loadValue(loader);
				if (value != null) result.put(loader.variableName, value);
			}
		}
	}

	private String loadValue(Loader loader) {
		try {
			return loader.load(policyProvider);
		} catch (SerializationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private int determineResultSize(String gwtModul) {
		int result = 0;
		if (loaders.containsKey(null)) result += loaders.get(null).size();
		if (loaders.containsKey(gwtModul)) result += loaders.get(gwtModul).size();
		return result ;
	}

	private static class Loader {
		private final Method method;
		private final RemoteServiceServlet service;
		private final String variableName;
		
		public Loader(Method method, RemoteServiceServlet service, String variableName) {
			this.method = method;
			this.service = service;
			this.variableName = variableName;
		}

		public String load(SerializationPolicyProvider policyProvider) throws SerializationException {
			Object result = ReflectionUtils.invokeMethod(method, service);
			if (result == null) return null;
			SerializationPolicy policy = policyProvider.getPolicyFor(method.getDeclaringClass());
			return RPC.encodeResponseForSuccess(method, result, policy);
		}
	}
	
	//TODO mmove to some ReclectionUtils
	private static Class<?>[] collectAllInterfaces(Class<?> type) {
		HashSet<Class<?>> result = new HashSet<Class<?>>();
		LinkedList<Class<?>> queue = new LinkedList<Class<?>>();
		queue.add(type);
		
		while (!queue.isEmpty()) {
			type = queue.remove();
			
			Class<?> superType = type.getSuperclass();
			if (superType != null && !Object.class.equals(superType))
				queue.add(superType);
			
			for (Class<?> it : type.getInterfaces()) {
				result.add(it);
				queue.add(it);
			}
		}
		return result.toArray(new Class<?>[result.size()]);
	}
}
