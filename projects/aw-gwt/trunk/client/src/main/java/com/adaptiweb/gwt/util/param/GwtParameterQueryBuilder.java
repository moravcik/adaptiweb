package com.adaptiweb.gwt.util.param;

import com.adaptiweb.utils.commons.param.ParameterQueryBuilder;
import com.google.gwt.http.client.URL;

public class GwtParameterQueryBuilder extends ParameterQueryBuilder {
	
	public static final UrlEncoderProvider clientUrlEncoder = new UrlEncoderProvider() {
		public String encode(String value) { return URL.encodeQueryString(value); }
	};

	@Override
	public String toUrlQueryString() {
		return toUrlQueryString(clientUrlEncoder);
	}

}
