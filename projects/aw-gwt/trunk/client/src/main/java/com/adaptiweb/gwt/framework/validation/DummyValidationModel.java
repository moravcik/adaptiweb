package com.adaptiweb.gwt.framework.validation;


public class DummyValidationModel extends AbstractValidationModel {

	public DummyValidationModel() {}

	public DummyValidationModel(boolean initialValidStatus) {
		super(initialValidStatus);
	}

	//changed visibility
	@Override
	public void setValid(boolean value) {
		super.setValid(value);
	}
	
}
