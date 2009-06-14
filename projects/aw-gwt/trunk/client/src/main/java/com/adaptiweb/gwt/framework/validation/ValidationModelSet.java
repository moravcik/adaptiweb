package com.adaptiweb.gwt.framework.validation;

public interface ValidationModelSet extends ValidationModel, Iterable<ValidationModel> {
	
	boolean add(ValidationModel...models);
	
	boolean remove(ValidationModel...models);
	
	int size();
}
