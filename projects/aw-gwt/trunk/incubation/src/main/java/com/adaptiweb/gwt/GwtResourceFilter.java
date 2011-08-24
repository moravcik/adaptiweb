package com.adaptiweb.gwt;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proxy for GWT resources. 
 */
public class GwtResourceFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(GwtResourceFilter.class);
	
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

		logger.debug("gwt-resource: {}", path);
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Content-Type", getMimeType(path));
		
		if (path.contains(".cache.")) {
		    SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			int tzMillis = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
			cal.add(Calendar.MILLISECOND, -tzMillis);
			resp.setHeader("Date", formatter.format(cal.getTime()) + " GMT");
			// RFC2616 says to never give a cache time of more than a year
	        // http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21
			cal.add(Calendar.YEAR, 1);
			resp.setHeader("Expires", formatter.format(cal.getTime()) + " GMT");
			resp.setHeader("Cache-Control", "public, max-age=" + (365*24*60*60 /* one year in seconds */));
		}
		else if (path.contains(".nocache.")) {
			resp.setHeader("Cache-Control", "max-age=0, no-store, no-cache, must-revalidate");
			resp.setHeader("Pragma", "no-cache");
			resp.setHeader("Expires", "Fri, 02 Jan 1970 00:00:00 GMT");
		}
		
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
