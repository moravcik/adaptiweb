package java.net;

import java.io.UnsupportedEncodingException;

public class URLEncoder {

	public static String encode(String s, String enc) throws UnsupportedEncodingException {
		if (!"UTF-8".equals(enc)) throw new UnsupportedEncodingException(enc);
		return com.google.gwt.http.client.URL.encodeQueryString(s);
	}
	
}
