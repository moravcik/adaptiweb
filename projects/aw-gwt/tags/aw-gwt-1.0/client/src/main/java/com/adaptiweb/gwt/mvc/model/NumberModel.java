package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.user.client.ui.HasValue;

public interface NumberModel<T extends Number> extends HasValue<T> {
	
	T getNumber();
	
	void setNumber(T value);
}
