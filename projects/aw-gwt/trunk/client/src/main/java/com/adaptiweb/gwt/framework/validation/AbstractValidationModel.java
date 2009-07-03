package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractValidationModel extends AbstractHasHandlers implements ValidationModel {
	
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
		return handlers.addHandler(ValidationEvent.getType(), handler);
	}

}
