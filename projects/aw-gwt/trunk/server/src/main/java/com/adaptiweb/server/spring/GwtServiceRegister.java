package com.adaptiweb.server.spring;

import java.io.IOException;
import java.util.Map;
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
	
	private final static long serialVersionUID = 1L;

	private final static Pattern SERVICE_TYPE_FROM_URI = Pattern.compile("/gwt/([^/]+)(?:/(.+))?");
	
	private final Map<String, RemoteServiceServlet> gwtServices;

	@Autowired
	public GwtServiceRegister(Map<String, RemoteServiceServlet> gwtServices) {
		this.gwtServices = gwtServices;
		for (Map.Entry<String, RemoteServiceServlet> entry : gwtServices.entrySet())
			if (!entry.getKey().startsWith("gwt") || !entry.getKey().endsWith("Service"))
				throw new IllegalArgumentException("Illecal service name '" + entry.getKey()
						+ "'! (" + entry.getValue() + ")");
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		for (RemoteServiceServlet service : gwtServices.values())
			service.init(servletConfig);
	}

	public void destroy() {
		for (RemoteServiceServlet service : gwtServices.values())
			service.destroy();
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String serviceName = getServiceBeanName((HttpServletRequest) request);

		if (!gwtServices.containsKey(serviceName))
			throw new ServletException("Unknown service " +  serviceName + "!");
		
		gwtServices.get(serviceName).service(request, response);
	}

	public static final String getServiceBeanName(HttpServletRequest request) throws ServletException {
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		Matcher m = SERVICE_TYPE_FROM_URI.matcher(uri);
		
		if(!m.matches())
			throw new ServletException("Incorrect service path! (" + uri + ")");
		
		if(m.group(2) != null) request.setAttribute("subject", m.group(2));
		
		String uriName = m.group(1);
		
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
