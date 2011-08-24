package com.adaptiweb.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;

/**
 * This filter allows to exclude requests to static content (like GWT resources)
 * from spring's dispatcher and uses default dispatcher instead of it.
 * <p>
 * It accept two init parameters:<ol>
 * <li><b>includes</b> - whitespace separated string of ant patterns
 * <li><b>excludes</b> - whitespace separated string of ant patterns
 * </ol>
 * All request with request URI which match any includes pattern and
 * do not match any excludes pattern is forwarded to container's
 * default request dispatcher. It means that no servlet and filters
 * (followed after this filter in web.xml) will handle matched request.
 * </p>
 */
public class DefaultRequestDispatcherFilter implements Filter {

	private static AntPathMatcher antPathMatcher = new AntPathMatcher();
	private String[] includes;
	private String[] excludes;
	private RequestDispatcher defaultRequestDispatcher;

	@Override
	public void destroy() {
		includes = null;
		excludes = null;
		defaultRequestDispatcher = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String requestPath = requestPath((HttpServletRequest) request);

		if (includes(requestPath) && !excludes(requestPath)) {
			defaultRequestDispatcher.forward(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
	}

	private String requestPath(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length());
	}

	private boolean includes(String requestPath) {
		return includes == null || matchAny(requestPath, includes);
	}

	private boolean excludes(String requestPath) {
		return excludes != null && matchAny(requestPath, excludes);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		includes = whiteSpaceSeparatedStringArray(filterConfig.getInitParameter("includes"));
		excludes = whiteSpaceSeparatedStringArray(filterConfig.getInitParameter("excludes"));
		defaultRequestDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
	}

	private static String[] whiteSpaceSeparatedStringArray(String initParameter) {
		if (initParameter == null) return null;
		String[] items = initParameter.split("[\\s]+");
		List<String> result = new ArrayList<String>(items.length);

		for (String item : items) {
			item = item.trim();
			if (item.isEmpty()) continue;
			if (item.charAt(0) != '/') item = "/" + item;
			result.add(item);
		}

		return result.toArray(new String[result.size()]);
	}

	private static boolean matchAny(String path, String[] patterns) {
		for (String pattern : patterns) {
			if (antPathMatcher.isPattern(pattern) ? antPathMatcher.match(pattern, path) : pattern.equals(path))
				return true;
		}
		return false;
	}
}
