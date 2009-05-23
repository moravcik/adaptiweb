package com.adaptiweb.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
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
	
	private static final String GWT_MODULE = "gwtModule";

	//TODO make configurable url pattern matching
	//TODO better is ant-like pattern 
	private static final Pattern SUPPORTED_EXTENSIONS = Pattern.compile(
			".*\\.(css|js|html|png|gif)", Pattern.CASE_INSENSITIVE);
	
	private ServletContext context;

	private String gwtModule;

	@Override
	public void destroy() {
		context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());
		
		if(SUPPORTED_EXTENSIONS.matcher(path).matches()) {
			String resource = "/WEB-INF/" + gwtModule + path;
			InputStream input = context.getResourceAsStream(resource);
			
			if(input == null) chain.doFilter(request, response);
			else {
				assert log("gwt-resource: " + path);
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.setHeader("Content-Type", context.getMimeType(path));
				resp.setHeader("Cache-Control", path.contains(".cache.") ?
						"public, max-age=31536000" : "no-cache");
				writeResourceStream(input, resp);
			}
		}
		else chain.doFilter(request, response);
	}

	private boolean log(String msg) {
		System.out.println(msg);
		return true;
	}

	private void writeResourceStream(InputStream input, HttpServletResponse resp) throws IOException {
		try {
			IOUtils.copy(input, resp.getOutputStream());
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
		gwtModule = filterConfig.getInitParameter(GWT_MODULE);
		
		if(gwtModule == null)
			gwtModule = context.getInitParameter(GWT_MODULE);
		
		if(gwtModule == null)
			throw new ServletException(
				"Missing '" + GWT_MODULE + "' - context-param or filter-param for filter " + filterConfig.getFilterName() + "!");
	}

}
