package com.adaptiweb.gwt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Supposed to be configured as request-scoped:
 * <pre>
 * 	&lt;bean class="com.adaptiweb.gwt.RpcRequestContext" scope="request"&gt;
 *	  &lt;aop:scoped-proxy/&gt;
 *	&lt;/bean&gt;
 * </pre>
 */
public class RpcRequestContext {
	String rpcService;
	
	Map<String, String> responseHeaders = new HashMap<String, String>();
	
	HttpServletRequest request;
	HttpServletResponse response;

	void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public String getRpcService() {
		return rpcService;
	}
	
	public void setRpcService(String rpcService) {
		this.rpcService = rpcService;
	}
	
	public void setResponseHeader(String headerName, String headerValue) {
		responseHeaders.put(headerName, headerValue);
	}

	public void fillHeadersToResponse() {
		for (String rpcHeaderName : responseHeaders.keySet()) {
			String headerValue = responseHeaders.get(rpcHeaderName);
			response.setHeader(rpcHeaderName, headerValue);
		}
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
}
