package com.adaptiweb.gwt.mvc.model;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class DefaultValueChangeModel<T> extends AbstractHasHandlers implements ValueChangeModel<T> {

	private T value;
	
	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		if (GwtGoodies.areEquals(this.value, value)) return;
		setValueForced(value);
	}

	@Override
	public void reloadValue() {
		setValueForced(this.value);
	}
	
	@Override
	public void setValueForced(T value) {
		ValueChangeEvent.fire(this, this.value = value);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return handlers.addHandler(ValueChangeEvent.getType(), handler);
	}

}
