package com.adaptiweb.gwt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;


public class GwtModuleFiter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(GwtModuleFiter.class);
	
	private static final AntPathMatcher pathMatcher = new AntPathMatcher();
	
	private GwtModuleBean gwtModuleBean;
	
	private final Map<String, List<String>> includes = new HashMap<String, List<String>>();
	
	private final Map<String, List<String>> excludes = new HashMap<String, List<String>>();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (filterConfig == null) return;
		Enumeration<?> names = filterConfig.getInitParameterNames();
		
		try {
			while (names.hasMoreElements()) {
				String name = String.valueOf(names.nextElement());
				String parts[] = name.split(":");
				
				if (parts.length == 2 && "module".equals(parts[0].trim()))
					registerModule(parts[1].trim(), filterConfig.getInitParameter(name), filterConfig);
			}
		} catch (MalformedURLException e) {
			throw new ServletException(e);
		}
	}

	private void registerModule(String moduleName, String mapping, FilterConfig filterConfig) throws MalformedURLException {
		if (filterConfig.getServletContext().getResource("/" + moduleName) == null) {
			logger.error("Unkown GWT module: {}", moduleName);
			throw new InvalidParameterException("Unknow gwt module: " + moduleName);
		}
		for (String line : mapping.split("\n")) {
			int index = line.indexOf(':');
			if (index == -1) throw new InvalidParameterException("Invalid URL mapping entry: " + line.trim());
			
			String prefix = line.substring(0, index).trim();
			String pattern = line.substring(index + 1).trim();
			if (prefix.length() != 1) throw new InvalidParameterException("Invalid prefix (" + prefix + ") of URL mapping: " + line.trim());
			if (!pattern.startsWith("/")) pattern = "/" + pattern;
			
			switch(prefix.charAt(0)) {
			case '+':
				List<String> modules = includes.get(pattern);
				if (modules == null) includes.put(pattern, modules = new ArrayList<String>(1));
				modules.add(moduleName);
				break;
			case '-':
				List<String> patterns = excludes.get(moduleName);
				if (patterns == null) includes.put(moduleName, patterns = new ArrayList<String>(1));
				patterns.add(pattern);
				break;
			case '%':
				throw new UnsupportedOperationException("TODO");
			default :
				throw new InvalidParameterException("Unknown prefix (" + prefix + ") of URL mapping: " + line.trim());
			}
		}
		
		logger.info("GWT module '{}' sucessfully registerd.", moduleName);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String contextPath = request.getContextPath();
		String requestUri = request.getRequestURI().substring(contextPath.length());
		
		Set<String> matchesModules = new TreeSet<String>();
		
		collectIncludedModules(requestUri, matchesModules);
		removeExcludedModules(requestUri, matchesModules);
		
		if (!matchesModules.isEmpty()) {
			logger.info("Uri '{}' maches GWT modules: {}", requestUri, matchesModules);
			
			GwtModuleBean gwtModule = getGwtModuleBean();
			gwtModule.setContextPath(request.getContextPath());
			gwtModule.setName(matchesModules.iterator().next());
			
			request.setAttribute("gwtModule", gwtModule);
		}

		chain.doFilter(request, response);
	}

	private void removeExcludedModules(String requestUri, Set<String> matchesModules) {
		for (Entry<String,List<String>> exclude: excludes.entrySet()) {
			if (matchesModules.contains(exclude.getKey())) {
				for (String pattern : exclude.getValue()) {
					if (pathMatcher.match(pattern, requestUri)) {
						matchesModules.remove(exclude.getKey());
					}
				}
			}
		}
	}

	private void collectIncludedModules(String requestUri, Set<String> matchesModules) {
		for (Entry<String,List<String>> include : includes.entrySet()) {
			if (pathMatcher.match(include.getKey(), requestUri)) {
				matchesModules.addAll(include.getValue());
			}
		}
	}
	
	@Autowired(required=false)
	protected void setGwtModuleBean(GwtModuleBean gwtModuleBean) {
		this.gwtModuleBean = gwtModuleBean;
	}
	
	protected GwtModuleBean getGwtModuleBean() {
		return gwtModuleBean == null ? new GwtModuleBean() : gwtModuleBean;
	}

	@Override
	public void destroy() {
	}
}
