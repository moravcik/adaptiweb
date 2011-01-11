package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class ValueChangeValidationModel extends AbstractValidationModel {
	private String errorMessage;
	private final HandlerRegistration registration;

	public <T> ValueChangeValidationModel(HasValueChangeHandlers<T> vcm, final ValidationSource source) {
		registration = vcm.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				setValid(source.isValid());
			}
		});
		setValid(source.isValid());
	}

	public void discard() {
		registration.removeHandler();
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static <T> ValueChangeValidationModel create(final HasValueChangeHandlers<T> vcm, ValidationSource source) {
		return new ValueChangeValidationModel(vcm, source);
	}

}
