package com.adaptiweb.server.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.adaptiweb.gwt.common.api.CookieConstantDefinition;

public class Cookies {
	
	private static final String REQUEST_ATTRIBUTE_NAME = Cookies.class.getName();

	private final Map<String, Cookie> cookies = new HashMap<String, Cookie>();
	
	public static Cookies getInstance(HttpServletRequest request) {
		Object instance = request.getAttribute(REQUEST_ATTRIBUTE_NAME);
		if (instance instanceof Cookies) return (Cookies) instance;
		
		Cookies cookies = new Cookies(request.getCookies());
		request.setAttribute(REQUEST_ATTRIBUTE_NAME, cookies);
		return cookies;
	}

	private Cookies(Cookie[] cookies) {
		if (cookies != null)
			for (Cookie cookie : cookies)
				this.cookies.put(cookie.getName(), cookie);
	}

	public String getValue(CookieConstantDefinition definition) {
		return getValue(definition.name());
	}

	public String getValue(String name) {
		return getValue(name, null);
	}
	
	public String getValue(String name, String defaultValue) {
		Cookie cookie = getCookie(name);
		return cookie == null ? defaultValue : cookie.getValue();
	}
	
	public Cookie getCookie(String name) {
		return cookies.get(name);
	}
	
	public boolean exists(String name) {
		return cookies.containsKey(name);
	}
}
