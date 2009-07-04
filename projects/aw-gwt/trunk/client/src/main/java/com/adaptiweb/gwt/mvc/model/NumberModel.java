package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;

public interface NumberModel<T extends Number> extends HasValueChangeHandlers<T> {
	
	T getNumber();
	
	void setNumber(T value);
}
