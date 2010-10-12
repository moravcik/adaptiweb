package com.adaptiweb.gwt.mvc.model;

import com.adaptiweb.gwt.mvc.Model;
import com.google.gwt.user.client.ui.HasValue;

public interface NumberModel<T extends Number> extends HasValue<T>, Model {
	
	T getNumber();
	
	void setNumber(T value);
}
