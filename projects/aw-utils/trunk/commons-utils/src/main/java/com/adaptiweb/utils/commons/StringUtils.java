package com.adaptiweb.utils.commons;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
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

	public static String trim(String str, CharSequence trimingCharacters) {
		char[] tc = asSortedCharArray(trimingCharacters);
		return trimLeft(trimRight(str, tc), tc); 
	}

	private static String trimLeft(String str, char[] sortedCharArray) {
		int i = 0;
		while(i < str.length() && Arrays.binarySearch(sortedCharArray, str.charAt(i)) > -1) i++;
		return i == 0 ? str : str.substring(i);
	}

	public static String trimLeft(String str, CharSequence trimingCharacters) {
		return trimLeft(str, asSortedCharArray(trimingCharacters)); 
	}

	public static String trimRight(String str, CharSequence trimingCharacters) {
		return trimRight(str, asSortedCharArray(trimingCharacters)); 
	}

	private static String trimRight(String str, char[] sortedCharArray) {
		int i = str.length();
		while(i > 0 && Arrays.binarySearch(sortedCharArray, str.charAt(i - 1)) > -1) i--;
		return i == str.length() ? str : str.substring(0, i);
	}

	public static char[] asSortedCharArray(CharSequence chars) {
		char[] tc = new char[chars.length()];
		for(int i = 0; i < tc.length; i++) tc[i] = chars.charAt(i);
		Arrays.sort(tc);
		return tc;
	}
}
