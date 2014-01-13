package com.adaptiweb.utils.commons.param;

public enum QueryDelimiter {
	STANDARD("?"), HASH("#");
	
	String delimiter;
	
	private QueryDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
}
