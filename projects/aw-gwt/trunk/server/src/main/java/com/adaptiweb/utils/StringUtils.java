package com.adaptiweb.utils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StringUtils {
	
	private StringUtils() {}
	
	private static final Pattern PROXY_DEF_PATTERN = Pattern.compile("([^\\:]+)(?:\\:(\\d+))?");

	public static Proxy parseProxy(String proxyDefinition) {
		if(proxyDefinition == null || proxyDefinition.length() == 0) return Proxy.NO_PROXY;
		
		Matcher m = PROXY_DEF_PATTERN.matcher(proxyDefinition);
		if(m.matches()) {
			String hostName = m.group(1);
			int port = m.group(2) == null ? 80 : Integer.parseInt(m.group(2));
			return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));
		}
		else throw new IllegalArgumentException(proxyDefinition);
	}
}
