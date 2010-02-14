package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.mvc.model.ValueChangeModel;

public class NotNullValidationModel extends ValueChangeValidationModel {
	public static final String DEFAULT_ERROR_MESSAGE = "Required value is null!"; 
	
	private <T> NotNullValidationModel(ValueChangeModel<T> vcm, ValidationSource source) {
		super(vcm, source);
	}

	public static <T> NotNullValidationModel create(final ValueChangeModel<T> vcm) {
		NotNullValidationModel instance = new NotNullValidationModel(vcm, new ValidationSource() {
			@Override
			public boolean isValid() {
				return vcm.hasValue();
			}
		});
		instance.setErrorMessage(DEFAULT_ERROR_MESSAGE);
		return instance;
	}

}
