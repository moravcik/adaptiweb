package com.adaptiweb.utils.commons.param;

import java.util.HashMap;
import java.util.Map;

public class ParameterQueryParser {

	public interface UrlDecoderProvider {
		String decode(String value);
	}

	public static final class PlainUrlDecoder implements UrlDecoderProvider {
		@Override
		public String decode(String value) {
			return value;
		}
	}
	
	public static ParameterMap parseParameters(String queryString, UrlDecoderProvider decoder, Parameter... parameters) {
		ParameterMap parameterMap = new ParameterMap();
		Map<String, Parameter> parameterNames = prepareParameterNames(parameters);
		String parameterQuery = queryString.startsWith("?") || queryString.startsWith("#") ? queryString.substring(1) : queryString;
		for (String param : parameterQuery.split("&")) {
			String[] nameAndValue = param.split("=");
			if (nameAndValue.length == 2 && parameterNames.containsKey(nameAndValue[0])) {
				parameterMap.put(parameterNames.get(nameAndValue[0]), decoder.decode(nameAndValue[1]));
			}
		}
		return parameterMap;
	}

	private static Map<String, Parameter> prepareParameterNames(Parameter[] parameters) {
		Map<String, Parameter> parameterNames = new HashMap<String, Parameter>();
		for (Parameter param : parameters) parameterNames.put(param.getParameterName(), param);
		return parameterNames;
	}
	
}
