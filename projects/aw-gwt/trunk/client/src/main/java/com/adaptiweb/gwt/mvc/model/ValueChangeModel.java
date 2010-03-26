package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.user.client.ui.HasValue;

public interface ValueChangeModel<T> extends HasValue<T>, HasValueChangeInitHandlers<T> {

	boolean hasValue();
	
	/**
	 * @deprecated instead of this overwrite method #
	 * 
	 */
	@Deprecated
	void setValueForce(T value);
	
	@Deprecated
	void reloadValue();

}
