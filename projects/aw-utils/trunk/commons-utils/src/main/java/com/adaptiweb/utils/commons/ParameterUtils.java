package com.adaptiweb.utils.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.adaptiweb.utils.commons.param.ParameterQueryBuilder.UrlEncoderProvider;
import com.adaptiweb.utils.commons.param.ParameterQueryParser.UrlDecoderProvider;

// taken out from com.adaptiweb.utils.commons.param package due to enclosing GWT module

public class ParameterUtils {
	
	public static final UrlEncoderProvider serverUrlEncoder = new UrlEncoderProvider() {
		@Override
		public String encode(String value) {
			try { return URLEncoder.encode(value, "UTF-8"); }
			catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
		}
	};
	
	public static final UrlDecoderProvider serverUrlDecoder = new UrlDecoderProvider() {
		@Override
		public String decode(String value) {
			try { return URLDecoder.decode(value, "UTF-8"); }
			catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
		}
	};

}
