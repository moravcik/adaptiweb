package com.adaptiweb.utils.commons.param;

import java.util.Map;

public class ParameterQueryBuilder {
	
	public interface UrlEncoderProvider {
		String encode(String value);
	}
	
	public static final UrlEncoderProvider plainUrlEncoder = new UrlEncoderProvider() {
		@Override
		public String encode(String value) {
			return value;
		}
	};
	
	private static String prepareParameter(boolean needAmp, Map.Entry<String, String> paramValue, UrlEncoderProvider encoder) {
		return paramValue.getValue() == null 
			? "" : formatParam(needAmp, paramValue.getKey(), paramValue.getValue(), encoder);
	}

	private static String formatParam(boolean needAmp, String paramName, String paramValue, UrlEncoderProvider encoder) {
		return (needAmp ? "&" : "") + paramName + "=" + encoder.encode(paramValue);
	}

	public static String toUrlQueryString(ParameterMap parameterMap, UrlEncoderProvider encoder) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> paramValue : parameterMap.entrySet()) {
			result.append(prepareParameter(result.length() > 0, paramValue, encoder));
		}
		return result.toString();
	}

	public static <T> String toUrlQueryString(T input, UrlEncoderProvider encoder, Parameter<T>... parameters) {
		return toUrlQueryString(ParameterMap.Factory.toParameterMap(input, parameters), encoder);
	}

}
