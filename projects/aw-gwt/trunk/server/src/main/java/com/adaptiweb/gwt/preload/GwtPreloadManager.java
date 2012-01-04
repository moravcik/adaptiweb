package com.adaptiweb.gwt.preload;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.support.HandlerMethodInvoker;
import org.springframework.web.bind.annotation.support.HandlerMethodResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

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
public class GwtPreloadManager extends AnnotationMethodHandlerAdapter {
	
	private static final String ALL_MODULES = null;
	
	private static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();
	
	@Autowired
	protected SerializationPolicyProvider policyProvider;
	
	private final Map<String, List<Loader>> loaders = new HashMap<String, List<Loader>>();
	
	/**
	 * Preload methods must be defined in interfaces, due to Spring AOP Proxy support 
	 * @param gwtServlets
	 */
	@Autowired
	public void setServlets(Collection<RemoteServiceServlet> gwtServlets) {
		Field delegateField = ReflectionUtils.findField(RemoteServiceServlet.class, "delegate");
		ReflectionUtils.makeAccessible(delegateField);
		for (RemoteServiceServlet servlet : gwtServlets) {
			Object delegate = ReflectionUtils.getField(delegateField, servlet);
			registerDelegate(delegate);
		}
	}
	
	private void registerDelegate(Object delegate) {
		Class<?> delegateType = AopUtils.isAopProxy(delegate) 
			? AopUtils.getTargetClass(delegate) 
			: delegate.getClass();
		for (Class<?> interfaceType : ClassUtils.getAllInterfacesForClass(delegateType)) {
			registerDelegateInterface(interfaceType, delegateType, delegate);
		}
	}
	
	private void registerDelegateInterface(Class<?> interfaceType, Class<?> delegateType, Object delegate) {
		for (Method method : interfaceType.getMethods()) {
			Method implMethod = getImplMethod(method, delegateType);
			GwtPreload annotation = getPreloadAnnotation(method, implMethod);
			if (annotation != null) {
				registerDelegateMethod(delegate, method, annotation);
			}
		}
	}
	
	private void registerDelegateMethod(Object delegate, Method method, GwtPreload annotation) {
		String name = annotation.name();
		if (name.length() == 0) name = getDefaultName(method);
		Class<?> serviceInterface = !annotation.serviceInterface().equals(RemoteService.class) 
			? annotation.serviceInterface() : method.getDeclaringClass();
		Loader loader = new Loader(method, delegate, name, serviceInterface);
		
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

	private Method getImplMethod(Method method, Class<?> serviceType) {
		return ReflectionUtils.findMethod(serviceType, method.getName(), method.getParameterTypes());
	}
	
	private GwtPreload getPreloadAnnotation(Method method, Method implMethod) {
		return implMethod.isAnnotationPresent(GwtPreload.class) 
			? implMethod.getAnnotation(GwtPreload.class) : method.isAnnotationPresent(GwtPreload.class) 
				? method.getAnnotation(GwtPreload.class) : null;
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
				String value = loader.doLoad(policyProvider, request);
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
		private final String variableName;
		private final Class<?> serviceInterface;
		
		private Loader(Method method, Object delegate, String variableName, Class<?> serviceInterface) {
			this.method = method;
			this.delegate = delegate;
			this.variableName = variableName;
			this.serviceInterface = serviceInterface;
		}

		private String doLoad(SerializationPolicyProvider policyProvider, HttpServletRequest request) {
			try {
				HandlerMethodInvoker methodInvoker = new PreloadMehodInvoker();
				ServletWebRequest webRequest = new ServletWebRequest(request, null /*response*/);
				Object result = methodInvoker.invokeHandlerMethod(method, delegate, webRequest, new BindingAwareModelMap() /*just as argument*/);			
				if (result == null) return null;
				SerializationPolicy policy = policyProvider.getPolicyFor(serviceInterface);
				String encoded = RPC.encodeResponseForSuccess(method, result, policy);
				return StringEscapeUtils.escapeJavaScript(encoded);
			} catch (SerializationException e) {
				e.printStackTrace();
				return null;
			} catch (Exception ex) {
				ReflectionUtils.handleReflectionException(ex);
				throw new IllegalStateException("Should never get here");
			}
		}
	}
	
	/**
	 * Fragments from {@link AnnotationMethodHandlerAdapter.ServletHandlerMethodInvoker}   
	 */
	private static class PreloadMehodInvoker extends HandlerMethodInvoker {
		
		private PreloadMehodInvoker() {
			super(new HandlerMethodResolver());
		}

		@Override
		protected WebDataBinder createBinder(NativeWebRequest webRequest, Object target, String objectName) throws Exception {
			return new ServletRequestDataBinder(target, objectName);
		}
		@Override
		protected void doBind(WebDataBinder binder, NativeWebRequest webRequest) throws Exception {
			ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
			servletBinder.bind(webRequest.getNativeRequest(ServletRequest.class));
		}
		@Override
		protected HttpInputMessage createHttpInputMessage(NativeWebRequest webRequest) throws Exception {
			HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
			return new ServletServerHttpRequest(servletRequest);
		}
		@Override
		protected Object resolveCookieValue(String cookieName, @SuppressWarnings("rawtypes") Class paramType, NativeWebRequest webRequest)
				throws Exception {

			HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
			Cookie cookieValue = WebUtils.getCookie(servletRequest, cookieName);
			if (Cookie.class.isAssignableFrom(paramType)) {
				return cookieValue;
			}
			else if (cookieValue != null) {
				return URL_PATH_HELPER.decodeRequestString(servletRequest, cookieValue.getValue());
			}
			else {
				return null;
			}
		}

		@Override
		@SuppressWarnings({"unchecked"})
		protected String resolvePathVariable(String pathVarName, @SuppressWarnings("rawtypes") Class paramType, NativeWebRequest webRequest)
				throws Exception {

			HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
			Map<String, String> uriTemplateVariables =
					(Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			if (uriTemplateVariables == null || !uriTemplateVariables.containsKey(pathVarName)) {
				throw new IllegalStateException(
						"Could not find @PathVariable [" + pathVarName + "] in @RequestMapping");
			}
			return uriTemplateVariables.get(pathVarName);
		}

		@Override
		protected Object resolveStandardArgument(Class<?> parameterType, NativeWebRequest webRequest) throws Exception {
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			//HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

			if (ServletRequest.class.isAssignableFrom(parameterType) ||
					MultipartRequest.class.isAssignableFrom(parameterType)) {
				Object nativeRequest = webRequest.getNativeRequest(parameterType);
				if (nativeRequest == null) {
					throw new IllegalStateException(
							"Current request is not of type [" + parameterType.getName() + "]: " + request);
				}
				return nativeRequest;
			}
			else if (HttpSession.class.isAssignableFrom(parameterType)) {
				return request.getSession();
			}
			else if (Principal.class.isAssignableFrom(parameterType)) {
				return request.getUserPrincipal();
			}
			else if (Locale.class.equals(parameterType)) {
				return RequestContextUtils.getLocale(request);
			}
			else if (InputStream.class.isAssignableFrom(parameterType)) {
				return request.getInputStream();
			}
			else if (Reader.class.isAssignableFrom(parameterType)) {
				return request.getReader();
			}
			return super.resolveStandardArgument(parameterType, webRequest);
		}
	}
	
}
