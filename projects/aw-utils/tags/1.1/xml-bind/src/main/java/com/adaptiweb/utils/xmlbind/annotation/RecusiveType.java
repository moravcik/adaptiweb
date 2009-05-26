package com.adaptiweb.utils.xmlbind.annotation;


public enum RecusiveType {
	AUTO,
	YES,
	NO;
	
	public boolean is(Class<?> type) {
		if(this.equals(AUTO)) {
			return type.getName().startsWith("sk.ana.");
				
		}
		
		return this.equals(YES);	
	}
}
