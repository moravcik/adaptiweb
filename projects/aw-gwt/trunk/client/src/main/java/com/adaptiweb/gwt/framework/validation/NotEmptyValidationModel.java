package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.table.model.ListChangeEvent;
import com.adaptiweb.gwt.framework.table.model.ListChangeHandler;
import com.adaptiweb.gwt.framework.table.model.ListModel;
import com.google.gwt.event.shared.HandlerRegistration;

public class NotEmptyValidationModel extends AbstractValidationModel {

	public static final String DEFAULT_ERROR_MESSAGE = "List is empty!"; 
	
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	
	private final HandlerRegistration registration;

	private <T> NotEmptyValidationModel(final ListModel<T> lm) {
		registration = lm.addHandler(new ListChangeHandler<T>() {
			@Override
			public void onListChange(ListChangeEvent<T> event) {
				validate(lm.isEmpty());
			}
		});
		validate(lm.isEmpty());
	}

	protected void validate(boolean empty) {
		setValid(!empty);
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

	public static <T> NotEmptyValidationModel create(final ListModel<T> lm) {
		return new NotEmptyValidationModel(lm);
	}

}
