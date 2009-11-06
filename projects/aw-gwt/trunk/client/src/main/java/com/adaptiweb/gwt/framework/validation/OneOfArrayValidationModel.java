package com.adaptiweb.gwt.framework.validation;

import java.util.Arrays;
import java.util.List;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.mvc.model.ValueChangeModel;
import com.adaptiweb.gwt.util.ConcatUtils;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

//TODO create test
public class OneOfArrayValidationModel extends AbstractValidationModel {

	public static final String DEFAULT_ERROR_MESSAGE = "Value must be one of: $0";
	
	private String errorMessage;

	private final List<?> valueList;
	
	private final HandlerRegistration registration;

	private <T> OneOfArrayValidationModel(ValueChangeModel<T> vcm, T[] values) {
		valueList = Arrays.asList(values);
		errorMessage = GwtGoodies.format(DEFAULT_ERROR_MESSAGE, ConcatUtils.concat(", ", valueList.toArray()));
		registration = vcm.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validate(event.getValue());
			}
		});
		validate(vcm.getValue());
	}

	protected void validate(Object value) {
		setValid(valueList.contains(value));
	}

	public void discard() {
		registration.removeHandler();
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static <T> OneOfArrayValidationModel create(final ValueChangeModel<T> vcm, T... values) {
		return new OneOfArrayValidationModel(vcm, values);
	}

}
