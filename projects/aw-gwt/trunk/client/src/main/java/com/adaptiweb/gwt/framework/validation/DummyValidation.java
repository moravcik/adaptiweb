package com.adaptiweb.gwt.framework.validation;


public class DummyValidation extends AbstractValidationModel {

	private String errorMessage;

	public DummyValidation() {}

	public DummyValidation(boolean initialValidStatus) {
		super(initialValidStatus);
	}

	//changed visibility
	@Override
	public void setValid(boolean value) {
		super.setValid(value);
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * Default: {@code null}
	 * @param errorMessage has no parameters
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
