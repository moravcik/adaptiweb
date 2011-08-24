package com.adaptiweb.server.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {

	private static final Pattern IP_ADDRESS = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+"); 
	
	public static String normalizeDomainName(String serverName) {
		if (IP_ADDRESS.matcher(serverName).matches()) return serverName;
		int index = serverName.lastIndexOf('.');
		if (index == -1) return serverName;
		index = serverName.lastIndexOf('.', index - 1);
		return index == -1 ? serverName : serverName.substring(index + 1);
	}
	
	public static String getNormalizedServerName() {
		return normalizeDomainName(getRequest().getServerName());
	}
	
	public static HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		if (ra != null && ra instanceof ServletRequestAttributes) {
			return ((ServletRequestAttributes) ra).getRequest();
		} else return null;
	}
	
	public static String getContextUrl() {
		HttpServletRequest req = getRequest();
		StringBuffer url = req.getRequestURL();
		int uriPathSize = req.getRequestURI().length() - req.getContextPath().length();
		return url.substring(0, url.length() - uriPathSize);
	}
	
}
