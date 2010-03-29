package com.adaptiweb.gwt.mvc.model;

import com.google.gwt.user.client.ui.HasValue;

public interface ValueChangeModel<T> extends HasValue<T>, HasValueChangeInitHandlers<T> {

	boolean hasValue();
	
	/**
	 * setValue with fireEvents parameter has not the same meaning:
	 * - setValue(T value, boolean fireEvents) sets new value only when isEquals returns false
	 * - which is not the same as when we need to force new value with no matter to result of isEquals method
	 * - in this case isEquals does not have to be called because we are sure we need to set new value
	 * - overwriting of original setValue method is not suitable, because we want to distinguish between forcing
	 * and non-forcing new value
	 * 
	 * Yes, I known how you mean this, but this method break encalupsation of model behavior.
	 * When you need something like this, create a sub interface or special implementation of model.
	 * Do not force every model to have this not standard behavior! Because it breaks behavior which I set up.
	 * Anyway, I think, that you can implement what you need by overwriting method
	 * {@link DefaultValueChangeModel#shouldChange(Object) } to always return true.
	 */
	@Deprecated
	void setValueForce(T value);
	
	/**
	 * This method also breaks main behavior of {@link ValueChangeModel} - fire event only when model was changed.
	 */
	@Deprecated // in some cases we need to force to reload value (to all handlers)
	void reloadValue();

}
