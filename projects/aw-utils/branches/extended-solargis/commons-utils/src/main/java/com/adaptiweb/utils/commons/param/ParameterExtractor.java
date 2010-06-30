package com.adaptiweb.utils.commons.param;

public interface ParameterExtractor<T> {
	Parameter<T> getParameter();
	Object extractValue(T source);

	public static class Helper {

		public static Object extractValue(Object value, Object defaultValue) {
			if (value != null && defaultValue != null && value.equals(defaultValue)) return null;
			else if (value == null) return defaultValue;
			else return value;
		}
		
	}
}
