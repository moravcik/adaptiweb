package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.mvc.model.ValueChangeModel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class NotNullValidationModel extends AbstractValidationModel {

	public static final String DEFAULT_ERROR_MESSAGE = "Required value is null!"; 
	
	private interface ObjectSource {
		Object getObject();
	}

	private static class ValueChangeModelSource<T> implements ObjectSource {

		private ValueChangeModel<T> vcm;

		public ValueChangeModelSource(ValueChangeModel<T> vcm) {
			this.vcm = vcm;
		}
		
		@Override
		public T getObject() {
			return vcm.getValue();
		}
	}

	private final ObjectSource s;
	private String errorMessage = DEFAULT_ERROR_MESSAGE;
	
	private NotNullValidationModel(ObjectSource s) {
		this.s = s;
		validate();
	}

	protected void validate() {
		setValid(s.getObject() != null);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static <T> ValidationModel create(final ValueChangeModel<T> vcm) {
		final NotNullValidationModel validator = new NotNullValidationModel(
				new ValueChangeModelSource<T>(vcm));

		ValueChangeHandler<T> handler = new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				validator.validate();
			}
		};
		vcm.addValueChangeHandler(handler);		
		return validator;
	}

	/**
	 * Default: {@value #DEFAULT_ERROR_MESSAGE}
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
