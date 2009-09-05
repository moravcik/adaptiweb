package com.adaptiweb.gwt.mvc.model;

public interface ValueChangeCacheModel<T> extends ValueChangeModel<T> {

	/**
	 * @return Previous value
	 */
	T getCacheValue(); // TODO may extend ValueChangeEvent to contain also cacheValue
	
}
