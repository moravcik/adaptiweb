package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.shared.GwtEvent;

public class ValidationEvent extends GwtEvent<ValidationHandler> {

	/**
	 * Handler type.
	 */
	private static Type<ValidationHandler> TYPE;

	public static void fire(HasValidationHandlers source, boolean value) {
		if (TYPE != null) {
			ValidationEvent event = new ValidationEvent(value);
			source.fireEvent(event);
		}
	}

	public static Type<ValidationHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ValidationHandler>();
		}
		return TYPE;
	}

	private final boolean value;

	protected ValidationEvent(boolean value) {
		this.value = value;
	}

	@Override
	public final Type<ValidationHandler> getAssociatedType() {
		return TYPE;
	}

	public boolean isValid() {
		return value;
	}

	@Override
	protected void dispatch(ValidationHandler handler) {
		handler.onValidationChange(this);
	}
}
