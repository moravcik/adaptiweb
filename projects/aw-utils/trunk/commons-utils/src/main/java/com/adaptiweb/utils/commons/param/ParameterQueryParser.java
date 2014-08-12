package com.adaptiweb.utils.commons.param;


public class ParameterQueryParser {

	public static final UrlDecoder plainUrlDecoder = new UrlDecoder() {
		@Override
		public String decode(String value) {
			return value;
		}
	};
	
	public static ParameterMap parseParameters(String queryString, UrlDecoder decoder) {
		ParameterMap parameterMap = new ParameterMap();
		String parameterQuery = queryString;
		if (queryString.startsWith("?") || queryString.startsWith("#")) parameterQuery = queryString.substring(1);
		else if (queryString.startsWith("/#")) parameterQuery = queryString.substring(2);
		for (String param : parameterQuery.split("&")) {
			String[] nameAndValue = param.split("=");
			if (nameAndValue.length == 2) { 
				parameterMap.put(nameAndValue[0], decoder.decode(nameAndValue[1]));
			} else if (nameAndValue.length == 1) {
				parameterMap.put(nameAndValue[0], null);
			}
		}
		return parameterMap;
	}

}
