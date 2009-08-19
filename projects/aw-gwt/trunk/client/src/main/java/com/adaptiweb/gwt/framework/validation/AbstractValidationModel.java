package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.logic.AbstractLogicModel;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractValidationModel extends AbstractLogicModel implements ValidationModel {
	
	protected AbstractValidationModel() {
		super();
	}

	protected AbstractValidationModel(boolean initialLogicValue) {
		super(initialLogicValue);
	}

	protected AbstractValidationModel(Object source, boolean initialLogicValue) {
		super(source, initialLogicValue);
	}

	protected AbstractValidationModel(Object source) {
		super(source);
	}
	
	@Override
	public boolean isValid() {
		return getLogicValue();
	}
	
	protected void setValid(boolean value) {
		setLogicValue(value);
	}
	
	@Override
	protected void fireValueChangeEvent() {
		super.fireValueChangeEvent();
		ValidationEvent.fire(this);
	}

	@Override
	public HandlerRegistration addValidationHandler(ValidationHandler handler, boolean fireInitEvent) {
		if(fireInitEvent) ValidationEvent.init(this, handler);
		return handlers.addHandler(ValidationEvent.getType(), handler);
	}

}
