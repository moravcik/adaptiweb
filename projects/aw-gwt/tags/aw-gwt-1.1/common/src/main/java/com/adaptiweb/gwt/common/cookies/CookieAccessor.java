package com.adaptiweb.gwt.common.cookies;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

public class CookieAccessor implements CookieDefinition {
	
	private final long maxAge;
	private final String name;
	private final String path;
	
	public CookieAccessor(String name) {
		this(name, null, FOR_SESSION);
	}

	public CookieAccessor(String name, long maxAgeInMillis) {
		this(name, null, maxAgeInMillis);
	}
	
	public CookieAccessor(String name, String path, long maxAgeInMillis) {
		this.name = name;
		this.path = path;
		this.maxAge = maxAgeInMillis;
	}
	
	public boolean exists() {
		return getValue() != null;
	}

	public void setValue(String value) {
		Cookies.setCookie(name(), value, isSessionScope() ? null : expires(), null, path, false);
	}

	public String getValue() {
		return Cookies.getCookie(name());
	}
	
	public void clear() {
		if (path != null) Cookies.removeCookie(name(), path);
		else Cookies.removeCookie(name());
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
