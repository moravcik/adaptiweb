package com.adaptiweb.gwt.framework.validation;


public class DummyValidation extends AbstractValidationModel {

	public DummyValidation() {}

	public DummyValidation(boolean initialValidStatus) {
		super(initialValidStatus);
	}

	//changed visibility
	@Override
	public void setValid(boolean value) {
		super.setValid(value);
	}
	
}
