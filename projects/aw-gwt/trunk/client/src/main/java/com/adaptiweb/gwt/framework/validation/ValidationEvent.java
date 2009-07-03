package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.shared.GwtEvent;

public class ValidationEvent extends GwtEvent<ValidationHandler> {

	private static Type<ValidationHandler> TYPE;

	public static void fire(ValidationModel model) {
		if (TYPE != null) model.fireEvent(new ValidationEvent(model));
	}

	public static Type<ValidationHandler> getType() {
		if (TYPE == null) TYPE = new Type<ValidationHandler>();
		return TYPE;
	}

	private final boolean valid;
	private final ValidationModel model;

	protected ValidationEvent(ValidationModel model) {
		this.valid = model.isValid();
		this.model = model;
	}

	@Override
	public final Type<ValidationHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean isValid() {
		return valid;
	}
	
	public ValidationModel getModel() {
		return model;
	}

	@Override
	public String toDebugString() {
		return super.toDebugString()
			+ " valid=" + isValid()
			+ ", model.valid=" + getModel().isValid();
	}

	@Override
	protected void dispatch(ValidationHandler handler) {
		handler.onValidationChange(this);
	}
}
