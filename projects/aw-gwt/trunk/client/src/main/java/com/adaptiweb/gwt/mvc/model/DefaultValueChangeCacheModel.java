package com.adaptiweb.gwt.mvc.model;

public class DefaultValueChangeCacheModel<T> extends DefaultValueChangeModel<T> implements ValueChangeCacheModel<T> {

	private T cacheValue;
	
	@Override
	public T getCacheValue() {
		return cacheValue;
	}

	public void setValueForced(T value) {
		cacheValue = getValue();
		super.setValueForced(value);
	};
	
}
