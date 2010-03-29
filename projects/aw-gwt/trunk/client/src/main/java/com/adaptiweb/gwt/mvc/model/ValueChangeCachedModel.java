package com.adaptiweb.gwt.mvc.model;

public interface ValueChangeCachedModel<T> extends ValueChangeModel<T> {

	/**
	 * @return Previous value
	 */
	T getValueCached(); // TODO may extend ValueChangeEvent to contain also cacheValue
	
	void setValueCached(T value);
	
	boolean hasValueCached();
}
