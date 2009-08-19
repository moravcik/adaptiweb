package com.adaptiweb.gwt.framework.validation;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.google.gwt.event.shared.HandlerRegistration;

public interface ValidationModel extends LogicModel {

	boolean isValid();
	
//	List<String> getValidationReport();

	HandlerRegistration addValidationHandler(ValidationHandler handler, boolean fireInitEvent);
}
