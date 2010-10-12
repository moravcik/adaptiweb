package com.adaptiweb.gwt.mvc.model;


public class DefaultNumberModel<T extends Number> extends DefaultValueChangeModel<T> implements NumberModel<T> {
	
	@Override
	public T getNumber() {
		return getValue();
	}

	@Override
	public void setNumber(T value) {
		setValue(value);
	}

}
