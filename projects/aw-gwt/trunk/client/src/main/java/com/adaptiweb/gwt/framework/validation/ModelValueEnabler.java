package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.adaptiweb.gwt.mvc.model.ValueChangeModel;

/**
 * If validation is false, default value is set into value change model
 */
public class ModelValueEnabler<T> implements LogicValueChangeHandler {

	private ValueChangeModel<T> model;
	private T defaultValue;
	
	public ModelValueEnabler(ValueChangeModel<T> model, T defaultValue) {
		this.model = model;
		this.defaultValue = defaultValue;
	}
	
	public void apply(LogicModel validation) {
		validation.addLogicValueChangeHandler(this, true);
	}

	private void enable(boolean on) {
		if (!on) model.setValueForce(defaultValue);
	}
	
	@Override
	public void onLogicValueChange(LogicValueChangeEvent event) {
		enable(event.getModel().getLogicValue());
	}
	
}
