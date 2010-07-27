package com.adaptiweb.utils.commons;

public final class ExceptionUtils {
	
	private ExceptionUtils() {}
	
	public static String lightStackTrace(Throwable e) {
		StringBuilder result = new StringBuilder();
		appendMessage(result, e);
		
		while ((e = e.getCause()) != null)
			appendMessage(result.append("\nCaused by: "), e);
		
		return result.toString();
	}

	private static StringBuilder appendMessage(StringBuilder buffer, Throwable exception) {
		return buffer.append(exception.getClass().getName()).append(": ").append(exception.getMessage());
	}
}
