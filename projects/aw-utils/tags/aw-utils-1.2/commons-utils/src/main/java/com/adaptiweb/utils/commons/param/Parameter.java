package com.adaptiweb.utils.commons.param;

public interface Parameter {
	String getParameterName();
	
	public interface Source {
		Parameter[] getParameters();
	}
	
}
