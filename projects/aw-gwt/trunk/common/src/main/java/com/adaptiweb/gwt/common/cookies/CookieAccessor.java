package com.adaptiweb.gwt.common.cookies;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

public class CookieAccessor implements CookieDefinition {
	
	private final long maxAge;
	private final String name;
	
	public CookieAccessor(String name) {
		this(name, FOR_SESSION);
	}

	public CookieAccessor(String name, long maxAgeInMillis) {
		this.name = name;
		this.maxAge = maxAgeInMillis;
	}
	
	public boolean exists() {
		return getValue() != null;
	}

	public void setValue(String value) {
		if (isSessionScope()) Cookies.setCookie(name(), value);
		else Cookies.setCookie(name(), value, expires());
	}

	public String getValue() {
		return Cookies.getCookie(name());
	}
	
	public void clear() {
		Cookies.removeCookie(name());
	}
	
	public void extend() {
		String value = getValue();
		if(value != null) setValue(value);
	}

	public boolean isSessionScope() {
		return maxAge == FOR_SESSION;
	}

	public Date expires() {
		return isSessionScope() ? null : new Date(System.currentTimeMillis() + maxAge);
	}
	
	public String name() {
		return name;
	}
}
