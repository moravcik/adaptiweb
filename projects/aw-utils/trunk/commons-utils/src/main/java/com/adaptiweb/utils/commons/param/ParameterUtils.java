package com.adaptiweb.utils.commons.param;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ParameterUtils {

	public static final UrlEncoder defaultUrlEncoder = new UrlEncoder() {
		@Override
		public String encode(String value) {
			try { return URLEncoder.encode(value, "UTF-8"); }
			catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
		}
	};

	public static final UrlDecoder defaultUrlDecoder = new UrlDecoder() {
		@Override
		public String decode(String value) {
			try { return URLDecoder.decode(value, "UTF-8"); }
			catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
		}
	};

}
