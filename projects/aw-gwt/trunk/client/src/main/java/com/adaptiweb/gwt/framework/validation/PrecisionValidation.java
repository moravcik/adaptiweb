package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class PrecisionValidation extends AbstractValidationModel {

	private static final String DEFAULT_ERROR_MESSAGE = "Number $0 exceeded maximum $1 decimal places!";
	
	private final HandlerRegistration registration;
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	private Number lastTestedValue;
	private final int maxDeciamlPlaces;
	
	public <T extends Number> PrecisionValidation(final NumberModel<T> model, final int decimalPlaces) {
		maxDeciamlPlaces = decimalPlaces;
		registration = model.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validate(model.getNumber());
			}
		});
		validate(model.getNumber());
	}
	
	protected void validate(Number value) {
		lastTestedValue = value;
		setValid(value == null ? true : eval(value.doubleValue()));
	}
	
	private boolean eval(double value) {
		value = value * Math.pow(10, maxDeciamlPlaces);
		return Math.round(value) == value;
	}

	public void discard() {
		registration.removeHandler();
	}

	@Override
	public String getErrorMessage() {
		return GwtGoodies.format(errorMessage, lastTestedValue, maxDeciamlPlaces);
	}
	
	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE}
	 * @param errorMessage can has two parameters:<ul>
	 * <li>numberValue
	 * <li>{@link #maxDeciamlPlaces}
	 * </ul>
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
