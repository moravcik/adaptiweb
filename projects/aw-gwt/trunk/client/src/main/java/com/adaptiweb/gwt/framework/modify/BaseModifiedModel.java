package com.adaptiweb.gwt.framework.modify;

import com.adaptiweb.gwt.framework.logic.AbstractLogicModel;
import com.google.gwt.event.shared.HandlerRegistration;


public class BaseModifiedModel extends AbstractLogicModel implements ModifiedModel {

	protected BaseModifiedModel() {
		super();
	}

	protected BaseModifiedModel(boolean initialLogicValue) {
		super(initialLogicValue);
	}

	protected BaseModifiedModel(Object source, boolean initialLogicValue) {
		super(source, initialLogicValue);
	}

	protected BaseModifiedModel(Object source) {
		super(source);
	}

	public void burn() {
		setModified(false);
	}
	
	@Override
	public HandlerRegistration addModifiedHandler(ModifiedHandler handler, boolean fireInitEvent) {
		HandlerRegistration registrantion = handlers.addHandler(ModifiedEvent.getType(), handler);
		if (fireInitEvent) ModifiedEvent.init(this, handler);
		return registrantion;
	}

	@Override
	public boolean isModified() {
		return getLogicValue();
	}
	
	protected void setModified(boolean modified) {
		setLogicValue(modified);
	}

}
