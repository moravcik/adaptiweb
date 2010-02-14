package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.table.model.ListChangeEvent;
import com.adaptiweb.gwt.framework.table.model.ListChangeHandler;
import com.adaptiweb.gwt.framework.table.model.ListRefreshEvent;
import com.adaptiweb.gwt.framework.table.model.ListRefreshHandler;
import com.adaptiweb.gwt.framework.table.model.ListModel;
import com.google.gwt.event.shared.HandlerRegistration;

public class ListValidationModel extends AbstractValidationModel {
	private String errorMessage;
	private final HandlerRegistration changeRegistration;
	private final HandlerRegistration refreshRegistration;

	protected <T> ListValidationModel(ListModel<T> lm, final ValidationSource source) {
		changeRegistration = lm.addHandler(new ListChangeHandler<T>() {
			@Override
			public void onListChange(ListChangeEvent<T> event) {
				setValid(source.isValid());
			}
		});
		refreshRegistration = lm.addHandler(new ListRefreshHandler<T>() {
			@Override
			public void onListRefresh(ListRefreshEvent<T> event) {
				setValid(source.isValid());
			}
		});
		setValid(source.isValid());
	}

	public void discard() {
		changeRegistration.removeHandler();
		refreshRegistration.removeHandler();
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static <T> ListValidationModel create(final ListModel<T> lm, ValidationSource source) {
		return new ListValidationModel(lm, source);
	}

}
