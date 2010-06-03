package com.adaptiweb.utils.commons.param;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ParameterMap extends LinkedHashMap<String, String> {
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : entrySet())
			result.append('&').append(entry.getKey())
				.append('=').append(entry.getValue());
		return result.length() > 0 ? result.substring(1) : "";
	}
	
	@SuppressWarnings("unchecked")
	public void bindParameters(Object target, Parameter<?>... parameters) {
		for (Parameter param : parameters) {
			param.bindValue(target, containsKey(param.getParameterName()) 
					? get(param.getParameterName()) : null);
		}
	}

	private static String formatValue(Object value) {
		if (value == null) return null;
		if (value instanceof String) return value.toString();
		if (value instanceof Boolean) return ((Boolean) value) ? "1" : "0";
		if (value instanceof Enum<?>) return ((Enum<?>) value).name();
		return String.valueOf(value);
	}

	public static class Factory {
		public static <T> ParameterMap toParameterMap(T input, Parameter<T>... parameters) {
			ParameterMap parameterMap = new ParameterMap();
			for (Parameter<T> parameter : parameters) {
				Object extractedValue = parameter.extractValue(input);
				parameterMap.put(parameter.getParameterName(), formatValue(extractedValue));
			}
			return parameterMap;
		}
	}

}
