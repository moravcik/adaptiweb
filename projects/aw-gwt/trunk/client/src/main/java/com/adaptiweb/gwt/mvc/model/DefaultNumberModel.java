package com.adaptiweb.gwt.mvc.model;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class DefaultNumberModel<T extends Number> extends AbstractHasHandlers implements NumberModel<T> {
	
	private T value;

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return handlers.addHandler(ValueChangeEvent.getType(), handler);
	}

	@Override
	public T getNumber() {
		return value;
	}

	@Override
	public void setNumber(T value) {
		if (GwtGoodies.areEquals(this.value, value)) return;
		ValueChangeEvent.fire(this, this.value = value);
	}

}
