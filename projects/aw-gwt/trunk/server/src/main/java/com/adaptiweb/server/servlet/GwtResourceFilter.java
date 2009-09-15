package com.adaptiweb.server.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Proxy for GWT resources. 
 */
public class GwtResourceFilter implements Filter {
	
	private ServletContext context;

	@Override
	public void destroy() {
		context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());

		assert log("gwt-resource: " + path);
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Content-Type", context.getMimeType(path));
		resp.setHeader("Cache-Control", path.contains(".cache.") ? "public, max-age=31536000" : "no-cache");
		
		chain.doFilter(request, response);
	}

	private boolean log(String msg) {
		System.out.println(msg);
		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
	}

}
