package com.adaptiweb.gwt.framework.logic;

public class DefautLogicModel extends AbstractLogicModel {

	public DefautLogicModel() {
		super();
	}

	public DefautLogicModel(boolean initialLogicValue) {
		super(initialLogicValue);
	}

	public DefautLogicModel(Object source, boolean initialLogicValue) {
		super(source, initialLogicValue);
	}

	public DefautLogicModel(Object source) {
		super(source);
	}

	//changed visibility
	@Override
	public void setLogicValue(boolean value) {
		super.setLogicValue(value);
	}
	
}
