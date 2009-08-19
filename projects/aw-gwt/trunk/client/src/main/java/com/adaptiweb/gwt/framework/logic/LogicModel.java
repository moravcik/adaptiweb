package com.adaptiweb.gwt.framework.logic;

import com.adaptiweb.gwt.mvc.Model;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface LogicModel extends HasHandlers, Model {

	boolean getLogicValue();
	
	HandlerRegistration addLogicValueChangeHandler(LogicValueChangeHandler handler, boolean fireInitEvent);
}
