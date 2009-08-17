package com.adaptiweb.gwt.framework.modify;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;


public class BaseModifiedModel extends AbstractHasHandlers implements ModifiedModel {

	private boolean modified;

	protected BaseModifiedModel() {}

	protected BaseModifiedModel(boolean initChangedStatus) {
		modified = initChangedStatus;
	}

	protected void setModified(boolean modified) {
		if (this.modified == modified) return;
		this.modified = modified;
		ModifiedEvent.fire(this);
	}
	
	public void reset() {
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
		return modified;
	}

}
