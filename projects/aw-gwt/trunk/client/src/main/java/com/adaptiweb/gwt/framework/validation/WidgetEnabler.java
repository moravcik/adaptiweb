package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicModelFactory;
import com.adaptiweb.gwt.framework.logic.LogicModelSet;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.google.gwt.user.client.ui.FocusWidget;

public class WidgetEnabler implements LogicValueChangeHandler {

	private FocusWidget widget;
	
	LogicModelSet model = LogicModelFactory.and();

	public WidgetEnabler(FocusWidget widget) {
		this.widget = widget;
		model.addLogicValueChangeHandler(this, true);
	}
	
	public void apply(LogicModel validation) {
		model.add(validation);
	}

	protected void enable(boolean value) {
		widget.setEnabled(value);
	}
	
	@Override
	public void onLogicValueChange(LogicValueChangeEvent event) {
		enable(model.getLogicValue());
	}
}
