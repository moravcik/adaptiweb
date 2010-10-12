package com.adaptiweb.gwt.mvc.model;


public class DefaultStringModel extends DefaultValueChangeModel<String> implements StringModel {
	
	@Override
	public String getText() {
		return getValue();
	}

	@Override
	public void setText(String value) {
		setValue(value);
	}

}
