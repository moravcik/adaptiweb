package com.adaptiweb.server.spring;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DelegatingServletProxy extends GenericServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Servlet servlet;
	
	public String getServletInfo() {
		return "Servlet Delegator";
	}

	@Override
	public void init() throws ServletException {
		WebApplicationContext applicationContext = WebApplicationContextUtils
			.getRequiredWebApplicationContext(getServletContext());
		servlet = (Servlet) applicationContext.getBean(getServletConfig().getServletName());
		servlet.init(getServletConfig());
	}
	
	@Override
	public void destroy() {
		servlet.destroy();
	}

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		servlet.service(request, response);
	}
}
