package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.google.gwt.user.client.ui.FocusWidget;

public class WidgetEnabler implements LogicValueChangeHandler {

	private FocusWidget widget;

	public WidgetEnabler(FocusWidget widget) {
		this.widget = widget;
	}
	
	public void apply(LogicModel validation) {
		validation.addLogicValueChangeHandler(this, true);
	}

	private void enable(boolean on) {
		widget.setEnabled(on);
	}
	
	@Override
	public void onLogicValueChange(LogicValueChangeEvent event) {
		enable(event.getModel().getLogicValue());
	}
	
}
