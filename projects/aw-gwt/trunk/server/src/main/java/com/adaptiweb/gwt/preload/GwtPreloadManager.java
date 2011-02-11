package com.adaptiweb.gwt.preload;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;

/**
 * <h5>Usage:</h5>
 * 
 * <i>GWT service...</i>
 * <pre>
 * public interface UserService extends RempteService {
 *   UserCredential loadUserCredential();
 * }
 * </pre>
 * <i>Implementation...</i>
 * <pre>
 * public class GwtUserService extends RemoteServiceServlet implements UserService {
 * <b>{@code  @Preload(name="userCredential",modules="mainGwtModule")}</b>
 *   public UserCredential loadUserCredential() {...}
 * }
 * </pre>
 * <i>Controller...</i>
 * <pre>
 *{@code @Autowired}
 * protected GwtPreloadManager preloadManager;
 *   .
 *   .
 *   .
 *   Map<String,String> serializedObjects = preloadManager.getPreloadValues(<b>"mainGwtModule"</b>);
 *   if (!serializedObjects.isEmpty())
 *     request.setAttribute("serializedObjects", serializedObjects);
 * </pre>
 * <i>View...</i>
 * <pre>
 * &lt;c:if test="${serializedObjects != null}"&gt;
 *   &lt;script language="javascript" type="text/javascript"&gt;
 *     &lt;c:forEach var="entry" items="${serializedObjects}"&gt;
 *       var ${entry.key}='${entry.value}';
 *     &lt;/c:forEach&gt;
 *   &lt;/script&gt;
 * &lt;/c:if&gt;
 * </pre>
 * <i>Clients code...</i>
 * <pre>
 * SerializationStreamFactory ssf = GWT.create(UserService.class);
 * UserCredential user = (UserCredential) 
 *   GwtGoodies.getSerializedObject(<b>"userCredential"</b>, ssf);
 * </pre>
 */
public class GwtPreloadManager {
	
	private static final String ALL_MODULES = null;

	@Autowired
	protected SerializationPolicyProvider policyProvider;
	
	private final Map<String, List<Loader>> loaders = new HashMap<String, List<Loader>>();
	
	@Autowired
	public void setServlets(Collection<RemoteServiceServlet> gwtServlets) {
		Field delegateField = ReflectionUtils.findField(RemoteServiceServlet.class, "delegate");
		ReflectionUtils.makeAccessible(delegateField);
		for (RemoteServiceServlet servlet : gwtServlets) {
			Object delegate = ReflectionUtils.getField(delegateField, servlet);
			Class<?> delegateType = delegate.getClass();
			for (Class<?> iterfaceType : findRemoteSeviceInterfaces(delegateType)) {
				for (Method method : iterfaceType.getMethods()) {
					Preload annotation = getPreloadAnnotation(method, delegateType);
					if (annotation != null) register(servlet, delegate, method, annotation);
				}
			}
		}
	}
	
	private void register(RemoteServiceServlet servlet, Object delegate, Method method, Preload annotation) {
		String name = annotation.name();
		if (name.length() == 0) name = getDefaultName(method);
		Loader loader = new Loader(method, delegate, servlet, name);
		
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

	private Preload getPreloadAnnotation(Method method, Class<?> serviceType) {
		Method implMethod = ReflectionUtils.findMethod(serviceType, method.getName(), method.getParameterTypes());
		return implMethod.isAnnotationPresent(Preload.class) ? implMethod.getAnnotation(Preload.class) :
			method.isAnnotationPresent(Preload.class) ? method.getAnnotation(Preload.class) :
			null;
	}

	private Iterable<Class<?>> findRemoteSeviceInterfaces(Class<?> gwtDelegateType) {
		ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		for (Class<?> it : ClassUtils.getAllInterfacesForClass(gwtDelegateType))
			if (it != RemoteService.class && RemoteService.class.isAssignableFrom(it))
				result.add(it);
		return result;
	}

	public Map<String, String> getPreloadValues(String gwtModul, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>(determineResultSize(gwtModul));

		putValues(ALL_MODULES, request, result);
		putValues(gwtModul, request, result);
		
		return result;
	}
	
	private void putValues(String moduleName, HttpServletRequest request, Map<String, String> result) {
		if (loaders.containsKey(moduleName)) {
			for (Loader loader : loaders.get(moduleName)) {
				String value = loader.load(policyProvider, request);
				if (value != null) result.put(loader.variableName, value);
			}
		}
	}

	private int determineResultSize(String gwtModul) {
		int result = 0;
		if (loaders.containsKey(null)) result += loaders.get(null).size();
		if (loaders.containsKey(gwtModul)) result += loaders.get(gwtModul).size();
		return result ;
	}

	private static class Loader {
		private final Method method;
		private final Object delegate;
		private final RemoteServiceServlet servlet;
		private final String variableName;
		
		public Loader(Method method, Object delegate, RemoteServiceServlet servlet, String variableName) {
			this.method = method;
			this.delegate = delegate;
			this.servlet = servlet;
			this.variableName = variableName;
		}

		public String load(SerializationPolicyProvider policyProvider, HttpServletRequest request) {
			ExtendedRemoteServiceServlet customizedService = servlet instanceof ExtendedRemoteServiceServlet ? ((ExtendedRemoteServiceServlet) servlet) : null; 
			if (customizedService != null) customizedService.setThreadLocalRequest(request); 
			try {
				Object result = method.invoke(delegate);
				if (result == null) return null;
				SerializationPolicy policy = policyProvider.getPolicyFor(method.getDeclaringClass());
				return RPC.encodeResponseForSuccess(method, result, policy);
			} catch (SerializationException e) {
				e.printStackTrace();
				return null;
			} catch (Exception ex) {
				ReflectionUtils.handleReflectionException(ex);
				throw new IllegalStateException("Should never get here");
			} finally {
				if (customizedService != null) customizedService.removeThreadLocalRequest();
			}
		}
	}
	
}
