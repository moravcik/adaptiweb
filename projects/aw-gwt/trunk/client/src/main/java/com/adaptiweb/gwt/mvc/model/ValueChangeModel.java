package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.user.client.ui.HasValue;

public interface ValueChangeModel<T> extends HasValue<T>, HasValueChangeInitHandlers<T> {

	boolean hasValue();
	
	/**
	 * instead of this overwrite method # - which method?
	 * 
	 * setValue with fireEvents parameter has not the same meaning:
	 * - setValue(T value, boolean fireEvents) sets new value only when isEquals returns false
	 * - which is not the same as when we need to force new value with no matter to result of isEquals method
	 * - in this case isEquals does not have to be called because we are sure we need to set new value
	 * - overwriting of original setValue method is not suitable, because we want to distinguish between forcing
	 * and non-forcing new value
	 */
	//@Deprecated
	void setValueForce(T value);
	
	//@Deprecated // in some cases we need to force to reload value (to all handlers)
	void reloadValue();

}
