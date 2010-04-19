package com.adaptiweb.gwt.rpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GwtServiceRegister extends GenericServlet {

	private Pattern uriPattern;
	
	private final Map<String, RemoteServiceServlet> gwtServices;
	
	private Map<String, Integer> mappingGroups;

	@Autowired
	public GwtServiceRegister(Map<String, RemoteServiceServlet> gwtServices) {
		this.gwtServices = gwtServices;
		for (Map.Entry<String, RemoteServiceServlet> entry : gwtServices.entrySet())
			if (!entry.getKey().startsWith("gwt") || !entry.getKey().endsWith("Service"))
				throw new IllegalArgumentException("Illecal service name '" + entry.getKey()
						+ "'! (" + entry.getValue() + ")");
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		uriPattern = Pattern.compile(servletConfig.getInitParameter("uriPattern"));
		mappingGroups = parseMappingGroups(servletConfig.getInitParameter("mappingGroups"));
		
		if (!mappingGroups.containsKey("gwtService")) {
			throw new IllegalStateException("Missing gwtService mapping group!");
		}
		
		for (RemoteServiceServlet service : gwtServices.values()) service.init(servletConfig);
	}

	private Map<String, Integer> parseMappingGroups(String initParameter) {
		Pattern entryPattern = Pattern.compile("([^:]+):(\\d+)");
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		for (String entry : initParameter.split(",")) {
			Matcher m = entryPattern.matcher(entry.trim());
			if (!m.matches()) throw new IllegalStateException("Invalid parameter value: " + initParameter);
			result.put(m.group(1), new Integer(m.group(2)));
		}
		return result;
	}

	public void destroy() {
		for (RemoteServiceServlet service : gwtServices.values()) service.destroy();
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String serviceName = getServiceBeanName((HttpServletRequest) request);

		if (!gwtServices.containsKey(serviceName))
			throw new ServletException("Unknown service " +  serviceName + "!");
		
		if (request.getCharacterEncoding() == null) request.setCharacterEncoding("utf-8");
		gwtServices.get(serviceName).service(request, response);
	}

	public String getServiceBeanName(HttpServletRequest request) throws ServletException {
		String uri = request.getRequestURI().substring(request.getContextPath().length());

		Matcher m = uriPattern.matcher(uri);
		
		if(!m.matches())
			throw new ServletException("Incorrect service path! (" + uri + ")");
		
		for (Entry<String, Integer> mapping : mappingGroups.entrySet())
			request.setAttribute(mapping.getKey(), m.group(mapping.getValue()));
		
		return gwtServiceToBeanName((String) request.getAttribute("gwtService"));
	}

	private String gwtServiceToBeanName(String uriName) {
		StringBuilder result = new StringBuilder(10 + uriName.length());
		result.append("gwt");
		
		boolean activeState = true;
		
		for(char c : uriName.toCharArray()) {
			if(c == '-') activeState = true;
			else if(activeState) { result.append(Character.toUpperCase(c)); activeState = false; }
			else result.append(c);
		}
		result.append("Service");
		
		return result.toString();
	}
}
