package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBoxBase;

public class MinMaxValidation extends AbstractValidationModel {
	
	private static final String DEFAULT_INFINITY_REPRESENTATION = "infinity";

	private static final String DEFAULT_ERROR_MESSAGE = "Number $0 is not from the interval [$1,$2]!";
	
	private final HandlerRegistration registration;
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	private String infinityRepresentation = DEFAULT_INFINITY_REPRESENTATION;
	private Object lastTestedValue;
	private final Object min;
	private final Object max;

	public MinMaxValidation(final TextBoxBase textBox, final Long min, final Long max) {
		this.min = min;
		this.max = max;
		registration = textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validate(textBox.getText(), min, max);
			}
		});
		validate(textBox.getText(), min, max);
	}
	
	protected void validate(String text, Long min, Long max) {
		lastTestedValue = text;
		try { 
			long value = Long.parseLong(text);
			validate(value, min, max);
		} catch (NumberFormatException e) {
			setValid(false);
		}
	}

	public MinMaxValidation(final TextBoxBase textBox, final Double min, final Double max) {
		this.min = min;
		this.max = max;
		registration = textBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validate(textBox.getText(), min, max);
			}
		});
		validate(textBox.getText(), min, max);
	}

	protected void validate(String text, Double min, Double max) {
		lastTestedValue = text;
		try { 
			double value = Double.parseDouble(text.replace(',', '.'));
			validate(value, min, max);
		} catch (NumberFormatException e) {
			setValid(false);
		}
	}

	public <T extends Number> MinMaxValidation(final NumberModel<T> model, final Long min, final Long max) {
		this.min = min;
		this.max = max;
		registration = model.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validate(model.getNumber(), min, max);
			}
		});
		validate(model.getNumber(), min, max);
	}

	protected void validate(Number number, Long min, Long max) {
		lastTestedValue = number;
		if (number == null) setValid(true);
		else validate(number.longValue(), min, max);
	}
	
	public <T extends Number> MinMaxValidation(final NumberModel<T> model, final Double min, final Double max) {
		this.min = min;
		this.max = max;
		registration = model.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validate(model.getNumber(), min, max);
			}
		});
		validate(model.getNumber(), min, max);
	}

	protected void validate(Number number, Double min, Double max) {
		lastTestedValue = number;
		if (number == null) setValid(true);
		else validate(number.doubleValue(), min, max);
	}
	
	protected void validate(long value, Long min, Long max) {
		assert min != null || max != null;
		boolean minValid = min == null || value >= min.longValue();
		boolean maxValid = max == null || value <= max.longValue();
		setValid(minValid && maxValid);
	}
	
	protected void validate(double value, Double min, Double max) {
		assert min != null || max != null;
		boolean minValid = min == null || value >= min.doubleValue();
		boolean maxValid = max == null || value <= max.doubleValue();
		setValid(minValid && maxValid);
	}
	
	public void discard() {
		registration.removeHandler();
	}
	
	@Override
	public String getErrorMessage() {
		return GwtGoodies.format(errorMessage, lastTestedValue, 
				min == null ? infinityRepresentation : min,
				max == null ? infinityRepresentation : max);
	}
	
	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE}
	 * @param errorMessage can has three parameters: <ul>
	 * <li>numberValue
	 * <li>minAlowedValue or infinity
	 * <li>maxAllwedValue or infinity
	 * </ul>
	 * Note: infinity is represented by value passed to {@link #setInfinityRepresentation(String)}.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Default: {@value #DEFAULT_INFINITY_REPRESENTATION}
	 * @param infinityValue
	 */
	public void setInfinityRepresentation(String infinityValue) {
		this.infinityRepresentation = infinityValue;
	}
}
