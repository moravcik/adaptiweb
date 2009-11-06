package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.mvc.model.ValueChangeModel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class NotNullValidationModel extends AbstractValidationModel {

	public static final String DEFAULT_ERROR_MESSAGE = "Required value is null!"; 
	
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	
	private final HandlerRegistration registration;

	private <T> NotNullValidationModel(ValueChangeModel<T> vcm) {
		registration = vcm.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validate(event.getValue());
			}
		});
		validate(vcm.getValue());
	}

	protected void validate(Object value) {
		setValid(value != null);
	}

	public void discard() {
		registration.removeHandler();
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE}
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static <T> NotNullValidationModel create(final ValueChangeModel<T> vcm) {
		return new NotNullValidationModel(vcm);
	}

}
