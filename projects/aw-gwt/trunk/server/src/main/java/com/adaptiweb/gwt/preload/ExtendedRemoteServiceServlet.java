package com.adaptiweb.gwt.preload;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This extension of {@link RemoteServiceServlet} for GWT's RPC allows set {@link HttpServletRequest request}
 * from outside without calling {@link #service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} method.
 * 
 * <p>Set request object is necessary if we need obtain result of service method 
 * (which works with {@link HttpServletRequest request}) from another bean.
 * <br>For example, when we want include RPC responses directly on page into JavaScript variables.
 */
public class ExtendedRemoteServiceServlet extends RemoteServiceServlet {
	

	void setThreadLocalRequest(HttpServletRequest request) {
		if (getThreadLocalRequest() != null) throw new IllegalStateException();
		perThreadRequest.set(request);
	}

	void removeThreadLocalRequest() {
		perThreadRequest.remove();
	}
	
	protected String getContextURL() {
		HttpServletRequest req = getThreadLocalRequest();
		StringBuffer url = req.getRequestURL();
		int uriPathSize = req.getRequestURI().length() - req.getContextPath().length();
		return url.substring(0, url.length() - uriPathSize);
	}

}
