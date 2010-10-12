package com.adaptiweb.server.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Headers {
	
	public static final String HEADER_FROM = "From";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CHARGE_TO = "Charge-To";
    public static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String HEADER_PRAGMA = "Pragma";

	private static final String REQUEST_ATTRIBUTE_NAME = Headers.class.getName();
	
	private final HttpServletRequest request;

	private Headers(HttpServletRequest request) {
		this.request = request;
	}

	public static Headers getInstance(HttpServletRequest request) {
		Object instance = request.getAttribute(REQUEST_ATTRIBUTE_NAME);
		if (instance instanceof Headers) return (Headers) instance;
		
		Headers headers = new Headers(request);
		request.setAttribute(REQUEST_ATTRIBUTE_NAME, headers);
		return headers;
	}
	
	public String getValue(String name) {
		return request.getHeader(name);
	}
	
	public void print(Appendable out) {
		@SuppressWarnings("unchecked")
		Enumeration<String> names = request.getHeaderNames();
		if (names != null) while (names.hasMoreElements()) {
			String name = names.nextElement();
			appendQuietly(out, name);
		}
	}

	private void appendQuietly(Appendable out, String name) {
		try {
			out.append(name).append(": ").append(request.getHeader(name)).append('\n');
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
