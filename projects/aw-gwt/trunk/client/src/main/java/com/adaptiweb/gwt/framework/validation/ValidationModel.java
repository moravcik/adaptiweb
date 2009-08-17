package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.mvc.Model;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface ValidationModel extends HasHandlers, Model {

	boolean isValid();
	
//	List<String> getValidationReport();

	HandlerRegistration addValidationHandler(ValidationHandler handler, boolean fireInitEvent);
}
