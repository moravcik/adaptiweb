package com.adaptiweb.gwt.framework.validation;

import java.util.Arrays;
import java.util.List;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.util.ConcatUtils;
import com.google.gwt.user.client.ui.HasValue;

public class OneOfArrayValidationModel extends ValueChangeValidationModel {

	public static final String DEFAULT_ERROR_MESSAGE = "Value must be one of: $0";
	
	private <T> OneOfArrayValidationModel(HasValue<T> vcm, ValidationSource source) {
		super(vcm, source);
	}

	public static <T> OneOfArrayValidationModel create(final HasValue<T> vcm, T... values) {
		final List<T> valueList = Arrays.asList(values);

		OneOfArrayValidationModel instance = new OneOfArrayValidationModel(vcm, new ValidationSource() {
			@Override
			public boolean isValid() {
				return valueList.contains(vcm.getValue());
			}
		});
		instance.setErrorMessage(GwtGoodies.format(DEFAULT_ERROR_MESSAGE, ConcatUtils.concat(", ", values)));
		return instance;
	}

}
