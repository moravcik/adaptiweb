package com.adaptiweb.gwt.util;


public final class ConcatUtils {
	
	private ConcatUtils() {}
	
	public static String concat(String glue, String...items) {
		StringBuilder result = new StringBuilder();
		
		for(String item : items) {
			if(item != null) {
				if(result.length() > 0) result.append(glue);
				result.append(item);
			}
		}
		
		return result.toString();
	}
	
}
