package com.adaptiweb.gwt.framework.modify;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.google.gwt.event.shared.HandlerRegistration;

public interface ModifiedModel extends LogicModel {

	boolean isModified();
	
	void burn();
	
	void revert();

	HandlerRegistration addModifiedHandler(ModifiedHandler handler, boolean fireInitEvent);

}
