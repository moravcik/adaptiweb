package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractValidationModel extends AbstractHasHandlers implements ValidationModel, HasDebugInfo {
	
	private boolean valid;
	
	protected AbstractValidationModel() {}
	
	protected AbstractValidationModel(boolean initialValidStatus) {
		valid = initialValidStatus;
	}

	protected void setValid(boolean value) {
		if (this.valid == value) return;
		this.valid = value;
		ValidationEvent.fire(this);
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public HandlerRegistration addValidationHandler(ValidationHandler handler) {
		new ValidationEvent(this).dispatch(handler);
		return handlers.addHandler(ValidationEvent.getType(), handler);
	}

	@Override
	public String toDebugString() {
		return toDebugString(GwtGoodies.simpleClassName(getClass().getName()));
	}

	protected String toDebugString(String type) {
		return type + ":[" + isValid() + "]";
	}
}
