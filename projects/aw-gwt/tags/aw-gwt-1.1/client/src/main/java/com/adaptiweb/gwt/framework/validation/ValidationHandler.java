package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.shared.EventHandler;

public interface ValidationHandler extends EventHandler {

	void onValidationChange(ValidationEvent event);

}
