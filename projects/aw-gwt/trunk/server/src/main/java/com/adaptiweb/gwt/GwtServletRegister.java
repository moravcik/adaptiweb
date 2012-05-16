package com.adaptiweb.gwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Autowired(required=false) RpcRequestContext rpcContext;
	
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
					? (RemoteServiceServlet) gwtService : new RemoteServiceServletContextWrapper(gwtService);
				servlet.init(config);
				gwtServlets.put(gwtServiceId, servlet);
			}
		}
	}

	public boolean containsServlet(String gwtServiceId) {
		return gwtServlets.containsKey(gwtServiceId);
	}
	
	public Collection<RemoteServiceServlet> getServlets() {
		return gwtServlets.values();
	}

	public void handleServletService(String gwtServiceId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (hasRpcContext()) rpcContext.setRequestAndResponse(request, response);
		gwtServlets.get(gwtServiceId).service(request, response);
		if (hasRpcContext()) rpcContext.setRequestAndResponse(null, null);
	}
	
	private boolean hasRpcContext() {
		return rpcContext != null;
	}
	
	private class RemoteServiceServletContextWrapper extends RemoteServiceServlet {
		private static final long serialVersionUID = 1L;

		private RemoteServiceServletContextWrapper(Object delegate) {
			super(delegate);
		}
		
		@Override
		protected void onAfterResponseSerialized(String serializedResponse) {
			if (hasRpcContext()) rpcContext.fillHeadersToResponse();
		}

	}
	
}
