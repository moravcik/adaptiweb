package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.user.client.ui.FocusWidget;

public class FocusWidgetEnabler implements ValidationHandler {

	private FocusWidget widget;

	public FocusWidgetEnabler(FocusWidget widget) {
		this.widget = widget;
	}
	
	public void apply(ValidationModel validation) {
		validation.addValidationHandler(this);
		enable(validation.isValid());
	}

	private void enable(boolean on) {
		widget.setEnabled(on);
	}

	@Override
	public void onValidationChange(ValidationEvent event) {
		enable(event.isValid());
	}
	
}
