package com.adaptiweb.gwt.framework.validation;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasValidationHandlers extends HasHandlers {

	HandlerRegistration addValidationHandler(ValidationHandler handler);

}
