package com.adaptiweb.gwt.mvc.model;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class DefaultStringModel extends AbstractHasHandlers implements StringModel {
	
	private String value;
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return handlers.addHandler(ValueChangeEvent.getType(), handler);
	}

		@Override
	public String getText() {
		return value;
	}

	@Override
	public void setText(String value) {
		if(GwtGoodies.areEquals(this.value, value)) return;
		ValueChangeEvent.fire(this, this.value = value);
	}

}
