package com.adaptiweb.utils.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.adaptiweb.utils.commons.param.ParameterQueryBuilder.UrlEncoderProvider;

// taken out from com.adaptiweb.utils.commons.param package due to enclosing GWT module

public class ParameterUtils {
	
	public static final UrlEncoderProvider serverUrlEncoder = new UrlEncoderProvider() {
		@Override
		public String encode(String value) {
			try { return URLEncoder.encode(value, "UTF-8"); }
			catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
		}

	};
}
