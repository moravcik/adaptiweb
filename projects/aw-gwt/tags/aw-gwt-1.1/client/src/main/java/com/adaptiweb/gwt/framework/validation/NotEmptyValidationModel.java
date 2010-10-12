package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.table.model.ListModel;

public class NotEmptyValidationModel extends ListValidationModel {

	public static final String DEFAULT_ERROR_MESSAGE = "List is empty!"; 
	
	private <T> NotEmptyValidationModel(final ListModel<T> lm, ValidationSource source) {
		super(lm, source);
	}

	public static <T> NotEmptyValidationModel create(final ListModel<T> lm) {
		NotEmptyValidationModel instance = new NotEmptyValidationModel(lm, new ValidationSource() {
			@Override
			public boolean isValid() {
				return !lm.isEmpty();
			}
		});
		instance.setErrorMessage(DEFAULT_ERROR_MESSAGE);
		return instance;
	}

}
