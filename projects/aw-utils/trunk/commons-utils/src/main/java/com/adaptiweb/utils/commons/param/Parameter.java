package com.adaptiweb.utils.commons.param;


public interface Parameter<T> {
	String getParameterName();
	Object extractValue(T source);
	void bindValue(T target, String value, ParameterMap params);

	interface Source<T> {
		Parameter<T>[] getParameters();
	}

	public static class Helper {
		public static Object extractValue(Object value, Object defaultValue) {
			if (value != null && defaultValue != null && value.equals(defaultValue)) return null;
			else if (value == null) return defaultValue;
			else return value;
		}

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
	}
	
}
