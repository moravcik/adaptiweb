package com.adaptiweb.server.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adaptiweb.server.Cookies;

public class UUIDFilter implements Filter {
	
	private static final String UUID_COOKIE_NAME = "UUID";
	private static final String UUID_REQUEST_ATTRIBUTE = "UUID";
	private static final int SECONDS_IN_TEN_YEAR = 60 * 60 * 24 * 365 * 10;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		manageUUID(request, response);
		chain.doFilter(req, res);
	}

	private static void manageUUID(HttpServletRequest request, HttpServletResponse response) {
		Cookies cookies = Cookies.getInstance(request);
		
		Cookie uuid = cookies.getCookie(UUID_COOKIE_NAME);
		if (uuid == null) uuid = new Cookie(UUID_COOKIE_NAME, UUID.randomUUID().toString());
		uuid.setMaxAge(SECONDS_IN_TEN_YEAR);
		uuid.setPath("/");

		request.setAttribute(UUID_REQUEST_ATTRIBUTE, uuid.getValue());
		response.addCookie(uuid);
	}

	public static String getUUID(HttpServletRequest request) {
		return String.valueOf(request.getAttribute(UUID_REQUEST_ATTRIBUTE));
	}

}
