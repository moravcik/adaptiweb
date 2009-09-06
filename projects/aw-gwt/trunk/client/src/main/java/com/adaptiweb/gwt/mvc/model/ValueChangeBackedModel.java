package com.adaptiweb.gwt.mvc.model;

public interface ValueChangeBackedModel<T> extends ValueChangeModel<T> {

	/**
	 * @return Previous value
	 */
	T getValueBacked(); // TODO may extend ValueChangeEvent to contain also cacheValue
	
	void setValueBacked(T value);
	
	boolean hasValueBacked();
}
