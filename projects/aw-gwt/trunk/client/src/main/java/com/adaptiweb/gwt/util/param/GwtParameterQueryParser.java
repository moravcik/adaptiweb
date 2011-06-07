package com.adaptiweb.gwt.util.param;

import com.adaptiweb.utils.commons.param.ParameterMap;
import com.adaptiweb.utils.commons.param.ParameterQueryParser;
import com.adaptiweb.utils.commons.param.ParameterQueryParser.UrlDecoderProvider;
import com.google.gwt.http.client.URL;

public class GwtParameterQueryParser {

	private static final UrlDecoderProvider clientUrlDecoder = new UrlDecoderProvider() {
		public String decode(String value) { return URL.decodeQueryString(value); };
	};

	public static ParameterMap parseParameters(String queryString) {
		return ParameterQueryParser.parseParameters(queryString, clientUrlDecoder);
	}
	
}
