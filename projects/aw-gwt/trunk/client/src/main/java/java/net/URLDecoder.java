package java.net;

import java.io.UnsupportedEncodingException;

public class URLDecoder {

	public static String decode(String s, String enc) throws UnsupportedEncodingException {
		if (!"UTF-8".equals(enc)) throw new UnsupportedEncodingException(enc);
		return com.google.gwt.http.client.URL.decodeQueryString(s);
	}
}
