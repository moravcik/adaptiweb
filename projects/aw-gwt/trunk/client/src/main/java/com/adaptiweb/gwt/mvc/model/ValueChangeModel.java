package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;

public interface ValueChangeModel<T> extends HasValueChangeHandlers<T> {

	T getValue();
	
	void setValue(T value);

}
