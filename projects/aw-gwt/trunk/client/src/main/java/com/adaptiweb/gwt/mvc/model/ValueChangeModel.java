package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.user.client.ui.HasValue;

public interface ValueChangeModel<T> extends HasValue<T>, HasValueChangeInitHandlers<T> {

	boolean hasValue();
	
	void setValueForce(T value);
	
	void reloadValue();

}
