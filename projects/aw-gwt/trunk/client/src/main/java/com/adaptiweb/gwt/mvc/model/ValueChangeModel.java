package com.adaptiweb.gwt.mvc.model;

import com.adaptiweb.gwt.mvc.Model;
import com.google.gwt.user.client.ui.HasValue;

public interface ValueChangeModel<T> extends HasValue<T>, HasValueChangeInitHandlers<T>, Model {

	boolean hasValue();
	
}
