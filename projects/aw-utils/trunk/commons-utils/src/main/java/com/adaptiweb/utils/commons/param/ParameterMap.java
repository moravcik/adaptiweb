package com.adaptiweb.utils.commons.param;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParameterMap extends LinkedHashMap<Parameter, String> {

	private static final long serialVersionUID = 1L;

	public interface Provider {
		ParameterMap toParameterMap();
	}
	
	public interface Binder extends Provider, Parameter.Source {
		void bindParameterMap(ParameterMap parameters);
	}
	
	public interface Ignorable {
		boolean isIgnored();
		void setIgnored(boolean ignored);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<Parameter, String> entry : entrySet())
			result.append('&').append(entry.getKey()).append('=').append(entry.getValue());
		return result.length() > 0 ? result.substring(1) : "";
	}
	
	public <T> void bindParameters(T target, ParameterBinder<T>... binders) {
		for (ParameterBinder<T> binder : binders) {
			boolean isParsed = containsKey(binder.getParameter());
			binder.bindValue(target, isParsed ? get(binder.getParameter()) : null, isParsed);
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
		public static <T> ParameterMap toParameterMap(T input, ParameterExtractor<T>... extractors) {
			ParameterMap parameterMap = new ParameterMap();
			for (ParameterExtractor<T> extractor : extractors) {
				Object extractedValue = extractor.extractValue(input);
				parameterMap.put(extractor.getParameter(), formatValue(extractedValue));
			}
			return parameterMap;
		}
	}

	public static boolean isIgnored(Object obj) {
		return obj instanceof Ignorable && ((Ignorable) obj).isIgnored();
	}
	
}
