package com.adaptiweb.utils.ci;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Examples of variables provided by <code>this</code> implementation of {@link VariableSource}:<ul>
 * <li><code>${<b>{@value #CONTEXT_ROOT}</b>}</code> - returns <code>ServletContext.getRealPath("")</code>
 * <li><code>${<b>{@value #CONTEXT_PATH}</b>}</code> - returns <code>ServletContext.getContextPath()</code>
 * <li><code>${<b>{@value #SERVER_INFO}</b>}</code> - returns <code>ServletContext.getServerInfo()</code>
 * <li><code>${<b>{@value #SERVLET_CONTEXT_NAME}</b>}</code> - returns <code>ServletContext.getServletContextName()</code>
 * <li><code>${<b>{@value #CONTEXT_PREFIX}</b>.<b>{@value #ATTRIBUTE}</b>.<i>&lt;attributeName&gt;</i>}</code> - returns <code>String.valueOf(ServletContext.getAttribute(<i>&lt;attributeName&gt;</i>))</code>
 * <li><code>${<b>{@value #CONTEXT_PREFIX}</b>.<b>{@value #INIT_PARAM}</b>.<i>&lt;paramName&gt;</i>}</code> - returns <code>ServletContext.getInitParameter(<i>&lt;paramName&gt;</i>)</code>
 * <li><code>${<b>{@value #CONTEXT_PREFIX}</b>.<b>{@value #MIME_TYPE}</b>.<i>&lt;fileName&gt;</i>}</code> - returns <code>ServletContext.getMimeType(<i>&lt;fileName&gt;</i>)</code>
 * </ul>
 * <p>Any emphases name (in variable) can be replaced by custom name. See {@link #setMapping(Map)} method to know how to do it.
 * @see ServletContext
 */
public class ServletContextVariableSource implements VariableSource {

	public static final String MIME_TYPE = "mimeType";

	public static final String INIT_PARAM = "initParam";

	public static final String ATTRIBUTE = "attribute";

	public static final String CONTEXT_PREFIX = "context";

	public static final String SERVLET_CONTEXT_NAME = "servletContextName";

	public static final String SERVER_INFO = "serverInfo";

	public static final String CONTEXT_PATH = "contextPath";

	public static final String CONTEXT_ROOT = "contextRoot";
	
	private static final Set<String> knownParams = new HashSet<String>(Arrays.asList(
			MIME_TYPE, INIT_PARAM, ATTRIBUTE, CONTEXT_PREFIX, SERVLET_CONTEXT_NAME, SERVER_INFO, CONTEXT_PATH, CONTEXT_ROOT)); 

	/**
	 * Mapping custom variable names to standard.
	 */
	private Map<String,String> mapping = Collections.emptyMap();
	
	private ServletContext servletContext;
	
	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/**
	 * TODO
	 * <p>Examples:<ul>
	 * <li><code>&lt;entry key="servletContextRoot" value="contextRoot"/&gt;</code>
	 * <br>This overlay evaluating ${contextRoot} - only ${servletContextRoot} can be evaluated.
	 * <li><code>&lt;entry key="servletContextRoot" value="contextRoot"/&gt;</code>
	 * <br><code>&lt;entry key="contextRoot" value="contextRoot"/&gt;</code>
	 * <br>This allows use both ${contextRoot} and ${servletContextRoot}, like alias.
	 * <li><code style="text-decoration: line-through">&lt;entry key="server<b style="color:red">.</b>info" value="serverInfo"/&gt;</code>
	 * <br>This is not allowed - using character '.' is not allowed in custom name.
	 * <li><code>&lt;entry key="servletContext" value="context"/&gt;</code>
	 * <br><code>&lt;entry key="init" value="initParam"/&gt;</code>
	 * <br>This allows use ${servletContext.init.SOME_PROPERTY} instead of ${context.initParam.SOME_PROPERTY} 
	 * </ul>
	 * @param mapping - map custom variable names to standard
	 */
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	@Override
	public String getRawValue(String variableName) throws NullPointerException {
		String prefix = null;
		
		if (mapping.containsKey(variableName)) variableName = mapping.get(variableName);
		else prefix = extractPrefix(variableName);
		
		if (!knownParams.contains(prefix == null ? variableName : prefix)) return null;
		
		if (prefix == null) {
			if (CONTEXT_ROOT.equals(variableName)) return servletContext.getRealPath("");
			if (CONTEXT_PATH.equals(variableName)) return servletContext.getContextPath();
			if (SERVER_INFO.equals(variableName)) return servletContext.getServerInfo();
			if (SERVLET_CONTEXT_NAME.equals(variableName)) return servletContext.getServletContextName();
		}
		else if (CONTEXT_PREFIX.equals(prefix)) {
			variableName = removePrefix(variableName);
			String selector = extractPrefix(variableName);
			variableName = removePrefix(variableName);
			if (ATTRIBUTE.equals(selector)) return String.valueOf(servletContext.getAttribute(variableName));
			if (INIT_PARAM.equals(selector)) return servletContext.getInitParameter(variableName);
			if (MIME_TYPE.equals(selector)) return servletContext.getMimeType(variableName);
		}
		return null;
	}

	private String removePrefix(String variableName) {
		int index = variableName.indexOf(".");
		if (index < 0) throw new IllegalArgumentException(variableName);
		return variableName.substring(index + 1);
	}

	private String extractPrefix(String variableName) {
		int index = variableName.indexOf(".");
		if (index < 0) return null;
		String prefix = variableName.substring(0, index);
		return mapping.containsKey(prefix) ? mapping.get(prefix) : prefix;
	}
}
