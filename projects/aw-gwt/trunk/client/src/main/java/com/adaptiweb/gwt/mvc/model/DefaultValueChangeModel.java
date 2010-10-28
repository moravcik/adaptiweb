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
	public boolean hasValue() {
		return value != null;
	}
	
	@Override
	public void setValue(T value) {
		setValue(value, true);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return handlers.addHandler(ValueChangeEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addValueChangeHandlerAndInit(ValueChangeHandler<T> handler) {
		HandlerRegistration result = handlers.addHandler(ValueChangeEvent.getType(), handler);
		handler.onValueChange(new ValueChangeEvent<T>(this.value) {});
		return result;
	}

	/**
	 * Override when comparing JavaScriptObject instances
	 */
	protected boolean shouldChange(T value) {
		return false == GwtGoodies.areEquals(this.value, value);
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		if (shouldChange(value)) setValueForce(value, fireEvents);
	}
	
	private void setValueForce(T value, boolean fireEvents) {
		this.value = value;
		if (fireEvents) ValueChangeEvent.fire(this, value);
	}
	
}
