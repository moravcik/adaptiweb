package com.adaptiweb.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * Proxy for GWT resources. 
 */
public class GwtResourceFilter implements Filter {
	
	private FilterConfig config;
	private Pattern includePattern;
	private Pattern excludePattern;
	private String pathPrefix;

	@Override
	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = getRequestPath(request);

		assert log("gwt-resource: " + path);
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Content-Type", getMimeType(path));
		resp.setHeader("Cache-Control", path.contains(".cache.") ? "public, max-age=31536000" : "no-cache");
		
		InputStream input = openResourceDirectly(path);
		if(input == null) chain.doFilter(request, response);
		else writeAndCloseResourceStream(input, resp);
	}

	protected String getRequestPath(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		return req.getRequestURI().substring(req.getContextPath().length());
	}

	protected InputStream openResourceDirectly(String path) {
		if (includePattern == null || !includePattern.matcher(path).matches()) return null;
		if (excludePattern != null && excludePattern.matcher(path).matches()) return null;
		return config.getServletContext().getResourceAsStream(pathPrefix + path); 
	}

	private boolean log(String msg) {
		System.out.println(msg);
		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		includePattern = asPatern(initParameter("includePattern"));
		excludePattern = asPatern(initParameter("excludePattern"));
		pathPrefix = initParameter("pathPrefix", "");
	}

	protected Pattern asPatern(String expression) {
		return expression == null ? null : Pattern.compile(expression);
	}

	protected String initParameter(String name) {
		return initParameter(name, null);
	}
	
	protected String initParameter(String name, String defaultValue) {
		String result = config.getInitParameter(name);
		if (result == null) result = config.getServletContext().getInitParameter(name);
		return result == null ? defaultValue : result;
	}

	protected void writeAndCloseResourceStream(InputStream input, HttpServletResponse resp) throws IOException {
		try {
			IOUtils.copy(input, resp.getOutputStream());
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	protected String getMimeType(String path) {
		return config.getServletContext().getMimeType(path);
	}

}
