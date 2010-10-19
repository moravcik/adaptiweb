package com.adaptiweb.gwt.common.cookies;

import java.util.Date;

public interface CookieDefinition {

	public static final long FOR_SESSION = -1;
	public static final long MINUTE = 1000 * 60;
	public static final long HOUR = MINUTE * 60;
	public static final long DAY = HOUR  * 24;
	public static final long YEAR = DAY * 365;
	
	public String name();
	
	public Date expires();

	public void setValue(String value);
	
	public String getValue();
	
	public void extend();
	
	public void clear();
	
	public boolean exists();
	
	public boolean isSessionScope();
}
