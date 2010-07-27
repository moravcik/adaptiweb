package com.adaptiweb.utils.commons.param;

import java.util.ArrayList;
import java.util.List;

public interface ParameterBinder<T> extends ParameterExtractor<T> {
	void bindValue(T target, String value);

	public static class Helper {

		public static boolean getBooleanValue(String value) {
			return "1".equals(value) || "y".equalsIgnoreCase(value) || "t".equalsIgnoreCase(value);
		}

		public static boolean getBooleanValue(String value, Boolean defaultValue) {
			return isNotEmpty(value) ? getBooleanValue(value) : defaultValue;
		}

		public static double getDoubleValue(String value) {
			return Double.parseDouble(value);
		}

		public static double getDoubleValue(String value, double defaultValue) {
			return isNotEmpty(value) ? getDoubleValue(value) : defaultValue;
		}

		public static <T extends Enum<T>> T getEnumValue(Class<T> enumType, String value) {
			return Enum.valueOf(enumType, value);
		}

		public static <T extends Enum<T>> T getEnumValue(Class<T> enumType, String value, T defaultValue) {
			return isNotEmpty(value) ? getEnumValue(enumType, value) : defaultValue;
		}

		public static int getIntValue(String value) {
			return Integer.parseInt(value);
		}

		public static int getIntValue(String value, Integer defaultValue) {
			return isNotEmpty(value) ? getIntValue(value) : defaultValue;
		}

		public static long getLongValue(String value) {
			return Long.parseLong(value);
		}

		public static long getLongValue(String value, Long defaultValue) {
			return isNotEmpty(value) ? getLongValue(value) : defaultValue;
		}

		public static String getStringValue(String value, String defaultValue) {
			return isNotEmpty(value) ? value : defaultValue;
		}

		private static boolean isNotEmpty(String value) {
			return value != null && value.length() > 0;
		}
		
		public static Parameter[] toParameters(ParameterBinder<?>[] binders) {
			List<Parameter> parameters = new ArrayList<Parameter>();
			for (ParameterBinder<?> binder : binders) parameters.add(binder.getParameter());
			return parameters.toArray(new Parameter[parameters.size()]);
		}
	}
}
