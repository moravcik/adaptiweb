package com.adaptiweb.gwt.util;

import com.google.gwt.user.client.Window.Location;


public final class UrlParameters {
	
	private UrlParameters() {};
	
	public static Double getDoubleParameter(String linkParameterName) {
		String stringValue = Location.getParameter(linkParameterName);
		
		if(stringValue == null) return null;
		try {
			return Double.valueOf(stringValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer getIntegerParameter(String linkParameterName) {
		String stringValue = Location.getParameter(linkParameterName);
		
		if(stringValue == null) return null;
		try {
			return Integer.valueOf(stringValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Boolean getBooleanValue(String parameterName) {
		String value = Location.getParameter(parameterName);
		return value == null ? null : value.equals("1")
				| value.equalsIgnoreCase("yes") | value.equalsIgnoreCase("y") 
				| value.equalsIgnoreCase("true") | value.equalsIgnoreCase("t");
	}
}
