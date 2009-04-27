package com.adaptiweb.gwt.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParameterCollection extends LinkedHashMap<String,String> {

	private static final long serialVersionUID = 1L;

	public ParameterCollection(String parameters) {
		parse(parameters);
	}

	public void parse(String values) {
		if(values.startsWith("?") || values.startsWith("#"))
			values = values.substring(1);
		
		for(String param : values.split("&")) {
			String[] nameAndValue = param.split("=");
			if(nameAndValue.length == 2) {
				put(nameAndValue[0], nameAndValue[1]);
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Map.Entry<String, String> entry : entrySet())
			result.append('&').append(entry.getKey()).append('=').append(entry.getValue());
		return result.length() > 0 ? result.substring(1) : "";
	}

	public double getDoubleValue(String name) {
		return Double.parseDouble(get(name));
	}

	public double getDoubleValue(String name, double defaultValue) {
		return containsKey(name) ? getDoubleValue(name) : defaultValue;
	}

	public int getIntValue(String name) {
		return Integer.parseInt(get(name));
	}

	public int getIntValue(String name, Integer defaultValue) {
		return containsKey(name) ? getIntValue(name) : defaultValue;
	}

	public boolean getBooleanValue(String name) {
		String value = get(name);
		return "1".equals(value) || "y".equalsIgnoreCase(value) || "t".equalsIgnoreCase(value);
	}

	public boolean getBooleanValue(String name, Boolean defaultValue) {
		return containsKey(name) ? getBooleanValue(name) : defaultValue;
	}

}
