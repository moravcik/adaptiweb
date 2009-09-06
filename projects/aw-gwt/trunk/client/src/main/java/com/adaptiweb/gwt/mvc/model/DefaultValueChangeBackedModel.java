package com.adaptiweb.gwt.mvc.model;

public class DefaultValueChangeBackedModel<T> extends DefaultValueChangeModel<T> implements ValueChangeBackedModel<T> {

	private T valueBacked;
	
	@Override
	public T getValueBacked() {
		return valueBacked;
	}

	@Override
	public boolean hasValueBacked() {
		return valueBacked != null;
	}

	@Override
	public void setValueBacked(T value) {
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
