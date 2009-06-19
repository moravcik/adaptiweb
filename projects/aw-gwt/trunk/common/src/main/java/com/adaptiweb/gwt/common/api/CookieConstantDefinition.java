package com.adaptiweb.gwt.common.api;

import java.util.Date;


public interface CookieConstantDefinition {

	public static long MINUTE = 1000 * 60;
	public static long HOUR = MINUTE * 60;
	public static long DAY = HOUR  * 24;
	public static long YEAR = DAY * 365;
	
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
