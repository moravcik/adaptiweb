package com.adaptiweb.gwt.mvc.model;

public class DefaultValueChangeCachedModel<T> extends DefaultValueChangeModel<T> implements ValueChangeCachedModel<T> {

	private T valueBacked;
	
	@Override
	public T getValueCached() {
		return valueBacked;
	}

	@Override
	public boolean hasValueCached() {
		return valueBacked != null;
	}

	@Override
	public void setValueCached(T value) {
		if (shouldChange(value)) {
			this.valueBacked = getValue();
			super.setValueForce(value);
		}
	}
	
	@Override
	public void setValue(T value) {
		if (shouldChange(value)) {
			valueBacked = null;
			super.setValueForce(value);
		}
	};
	
}
