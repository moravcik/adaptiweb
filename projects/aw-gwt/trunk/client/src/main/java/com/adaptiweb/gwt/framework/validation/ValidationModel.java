package com.adaptiweb.gwt.framework.validation;

import java.util.LinkedList;
import java.util.List;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicModelSet;
import com.google.gwt.event.shared.HandlerRegistration;

public interface ValidationModel extends LogicModel {

	boolean isValid();
	
	String getErrorMessage();

	HandlerRegistration addValidationHandler(ValidationHandler handler, boolean fireInitEvent);
	
	class Util {
		List<String> collectErrorMessages(LogicModelSet models) {
			LinkedList<String> list = new LinkedList<String>();
			LinkedList<LogicModelSet> stack = new LinkedList<LogicModelSet>();
			
			stack.add(models);
			
			while (!stack.isEmpty()) {
				for (LogicModel model : stack.remove()) {
					if (model instanceof LogicModelSet) {
						stack.add((LogicModelSet) model);
					}
					else if (model instanceof ValidationModel) {
						ValidationModel validationModel = (ValidationModel) model;
						if (!validationModel.isValid())
							list.add(validationModel.getErrorMessage());
					}
				}
			}
			
			return list;
		}
	}
}
