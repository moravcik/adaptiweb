package com.adaptiweb.gwt;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@Component
public class GwtServletRegister {

	Map<String, RemoteServiceServlet> gwtServlets = new HashMap<String, RemoteServiceServlet>();

	@Autowired
	public GwtServletRegister(Collection<RemoteService> gwtServices, final ServletContext servletContext) throws ServletException {
		ServletConfig config = new ServletConfig() {
			@Override
			public String getServletName() {
				return null;
			}
			@Override
			public ServletContext getServletContext() {
				return servletContext;
			}
			@SuppressWarnings("rawtypes")
			@Override
			public Enumeration getInitParameterNames() {
				return null;
			}
			@Override
			public String getInitParameter(String name) {
				return null;
			}
		};
		for (RemoteService gwtService : gwtServices) {
			RemoteServiceRelativePath gwtPath = AnnotationUtils.findAnnotation(gwtService.getClass(), RemoteServiceRelativePath.class);
			if (gwtPath == null) throw new IllegalArgumentException("Missing RemoteServiceRelativePath annotation!");
			String gwtServiceId = StringUtils.substringBetween(gwtPath.value(), "gwt/", ".rpc");
			if (StringUtils.isNotBlank(gwtServiceId)) {
				RemoteServiceServlet servlet = gwtService instanceof RemoteServiceServlet 
					? (RemoteServiceServlet) gwtService : new RemoteServiceServlet(gwtService);
				servlet.init(config);
				gwtServlets.put(gwtServiceId, servlet);
			}
		}
	}

	public boolean containsServlet(String gwtServiceId) {
		return gwtServlets.containsKey(gwtServiceId);
	}
	
	public RemoteServiceServlet getServlet(String gwtServiceId) {
		return gwtServlets.get(gwtServiceId);
	}
		
	public Collection<RemoteServiceServlet> getServlets() {
		return gwtServlets.values();
	}
}
