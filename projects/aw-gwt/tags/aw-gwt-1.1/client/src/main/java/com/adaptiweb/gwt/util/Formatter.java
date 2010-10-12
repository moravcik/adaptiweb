package com.adaptiweb.gwt.util;

public interface Formatter<T> {

	String format(T value);

	T parse(String text) throws IllegalArgumentException;
	
}
