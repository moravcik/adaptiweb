package com.adaptiweb.server.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

	private static final String PATTERN_0_255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private static final Pattern PATTERN_IP = Pattern.compile("^(?:" + PATTERN_0_255 + "\\.){3}" + PATTERN_0_255 + "$");
	
	// private IP ranges
	private static final long IP_10_0_0_0 			= ipToLong("10.0.0.0");
	private static final long IP_10_255_255_255 	= ipToLong("10.255.255.255");
	private static final long IP_172_16_0_0 		= ipToLong("172.16.0.0"); 
	private static final long IP_172_31_255_255 	= ipToLong("172.31.255.255");
	private static final long IP_192_168_0_0 		= ipToLong("192.168.0.0");
	private static final long IP_192_168_255_255 	= ipToLong("192.168.255.255");

	// http://stackoverflow.com/a/21884642/1167517
	private static final String[] IP_HEADERS_TO_TRY = { 
	    "X-Forwarded-For",
	    "Proxy-Client-IP",
	    "WL-Proxy-Client-IP",
	    "HTTP_X_FORWARDED_FOR",
	    "HTTP_X_FORWARDED",
	    "HTTP_X_CLUSTER_CLIENT_IP",
	    "HTTP_CLIENT_IP",
	    "HTTP_FORWARDED_FOR",
	    "HTTP_FORWARDED",
	    "HTTP_VIA",
	    "REMOTE_ADDR" };

	public static String longToIp(long longIp) {
		int octet3 = (int) ((longIp >> 24) % 256);
		int octet2 = (int) ((longIp >> 16) % 256);
		int octet1 = (int) ((longIp >> 8) % 256);
		int octet0 = (int) ((longIp) % 256);
		return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
	}

	public static long ipToLong(String ip) {
		String[] octets = ip.split("\\.");
		return (Long.parseLong(octets[0]) << 24)
			+ (Integer.parseInt(octets[1]) << 16)
			+ (Integer.parseInt(octets[2]) << 8)
			+ Integer.parseInt(octets[3]);
	}
	
	public static boolean isIpPrivate(String ip) {
		long longIp = ipToLong(ip);
		return (longIp >= IP_10_0_0_0 && longIp <= IP_10_255_255_255)
			|| (longIp >= IP_172_16_0_0 && longIp <= IP_172_31_255_255)
			|| (longIp >= IP_192_168_0_0 && longIp <= IP_192_168_255_255);
	}

	public static boolean isIpValid(String ip) {
		return PATTERN_IP.matcher(ip).matches();
	}

	public static String getIp(HttpServletRequest request) {
	    for (String header : IP_HEADERS_TO_TRY) {
	        String ip = request.getHeader(header);
	        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
	        	ip = ip.split(",")[0].trim();
				if (isIpValid(ip) && !isIpPrivate(ip)) {
					return ip;
				}
	        }
	    }
	    return request.getRemoteAddr();
	}
	
}
