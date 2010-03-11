package com.adaptiweb.gwt.common.api;

import java.util.Date;


public interface CookieConstantDefinition {

	public static final long FOR_SESSION = -1;
	public static final long MINUTE = 1000 * 60;
	public static final long HOUR = MINUTE * 60;
	public static final long DAY = HOUR  * 24;
	public static final long YEAR = DAY * 365;
	
	public static CookieConstantDefinition JSESSIONID = new CookieConstantDefinition() {
		@Override
		public Date expires() {
			return null;
		}
		@Override
		public String name() {
			return "JSESSIONID";
		}
	};
	
	public String name();
	
	public Date expires();

}
