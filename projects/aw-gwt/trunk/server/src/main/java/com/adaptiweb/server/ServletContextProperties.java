package com.adaptiweb.server;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * Examples of properties:<ul>
 * <li><code>${<b>{@value #CONTEXT_ROOT}</b>}</code> - returns <code>ServletContext.getRealPath("")</code>
 * <li><code>${<b>{@value #CONTEXT_PATH}</b>}</code> - returns <code>ServletContext.getContextPath()</code>
 * <li><code>${<b>{@value #SERVER_INFO}</b>}</code> - returns <code>ServletContext.getServerInfo()</code>
 * <li><code>${<b>{@value #SERVLET_CONTEXT_NAME}</b>}</code> - returns <code>ServletContext.getServletContextName()</code>
 * <li><code>${<b>{@value #CONTEXT_PREFIX}</b>.<b>{@value #ATTRIBUTE}</b>.<i>&lt;attributeName&gt;</i>}</code> - returns <code>String.valueOf(ServletContext.getAttribute(<i>&lt;attributeName&gt;</i>))</code>
 * <li><code>${<b>{@value #CONTEXT_PREFIX}</b>.<b>{@value #INIT_PARAM}</b>.<i>&lt;paramName&gt;</i>}</code> - returns <code>ServletContext.getInitParameter(<i>&lt;paramName&gt;</i>)</code>
 * <li><code>${<b>{@value #CONTEXT_PREFIX}</b>.<b>{@value #MIME_TYPE}</b>.<i>&lt;fileName&gt;</i>}</code> - returns <code>ServletContext.getMimeType(<i>&lt;fileName&gt;</i>)</code>
 * </ul>
 * @see ServletContext
 */
@SuppressWarnings("serial")
public class ServletContextProperties extends Properties {

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

	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public String getProperty(String key) {
		String prefix = extractPrefix(key);
		
		if (!knownParams.contains(prefix == null ? key : prefix)) return null;
		
		if (prefix == null) {
			if (CONTEXT_ROOT.equals(key)) return servletContext.getRealPath("");
			if (CONTEXT_PATH.equals(key)) return servletContext.getContextPath();
			if (SERVER_INFO.equals(key)) return servletContext.getServerInfo();
			if (SERVLET_CONTEXT_NAME.equals(key)) return servletContext.getServletContextName();
		}
		else if (CONTEXT_PREFIX.equals(prefix)) {
			key = removePrefix(key);
			String selector = extractPrefix(key);
			key = removePrefix(key);
			if (ATTRIBUTE.equals(selector)) return String.valueOf(servletContext.getAttribute(key));
			if (INIT_PARAM.equals(selector)) return servletContext.getInitParameter(key);
			if (MIME_TYPE.equals(selector)) return servletContext.getMimeType(key);
		}
		return null;
	}
	
	/**
	 * Needed for {@link PropertyPlaceholderConfigurer}
	 */
	@Override
	public Enumeration<?> propertyNames() {
		final Iterator<String> knownParamsIterator = knownParams.iterator();
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {
				return knownParamsIterator.hasNext();
			}
			@Override
			public String nextElement() {
				return knownParamsIterator.next();
			}
		};
	}
	
	private String removePrefix(String variableName) {
		int index = variableName.indexOf(".");
		if (index < 0) throw new IllegalArgumentException(variableName);
		return variableName.substring(index + 1);
	}

	private String extractPrefix(String variableName) {
		int index = variableName.indexOf(".");
		if (index < 0) return null;
		return variableName.substring(0, index);
	}
}
