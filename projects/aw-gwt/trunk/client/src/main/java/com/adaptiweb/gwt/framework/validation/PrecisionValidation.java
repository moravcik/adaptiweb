package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class PrecisionValidation extends AbstractValidationModel {

	private final HandlerRegistration registration;
	
	public <T extends Number>
	PrecisionValidation(final NumberModel<T> model, final int decimalPlaces) {
		registration = model.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validate(model.getNumber(), decimalPlaces);
			}
		});
		validate(model.getNumber(), decimalPlaces);
	}
	
	protected void validate(Number value, int decemaPlaces) {
		if (value == null) setValid(true);
		else validate(value.doubleValue(), decemaPlaces);
	}
	
	protected void validate(double value, int decemaPlaces) {
		value = value * Math.pow(10, decemaPlaces);
		setValid(Math.round(value) == value);
	}

	public void discard() {
		registration.removeHandler();
	}
}
