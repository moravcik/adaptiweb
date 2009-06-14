package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractValidationModel implements ValidationModel {
	
	private final HandlerManager handlers = new HandlerManager(this);
	private boolean value;

	@Override
	public HandlerRegistration addValidationHandler(ValidationHandler handler) {
		return handlers.addHandler(ValidationEvent.getType(), handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlers.fireEvent(event);
	}
	
	public boolean isValid() {
		return value;
	}
	
	protected void setValid(boolean value) {
		if (this.value == value) return;
		ValidationEvent.fire(this, this.value = value);
	}

}
