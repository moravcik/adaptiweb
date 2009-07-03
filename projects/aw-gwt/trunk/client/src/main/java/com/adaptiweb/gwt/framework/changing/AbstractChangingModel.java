package com.adaptiweb.gwt.framework.changing;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;


public class AbstractChangingModel extends AbstractHasHandlers implements ChangingModel {

	private boolean changed;

	protected AbstractChangingModel() {}

	protected AbstractChangingModel(boolean initChangedStatus) {
		changed = initChangedStatus;
	}

	protected void setChanged(boolean changed) {
		if (this.changed == changed) return;
		this.changed = changed;
		ChangingEvent.fire(this);
	}

	@Override
	public HandlerRegistration addChangingHandler(ChangingHandler handler, boolean fireInitEvent) {
		HandlerRegistration registrantion = handlers.addHandler(ChangingEvent.getType(), handler);
		if (fireInitEvent) ChangingEvent.init(this, handler);
		return registrantion;
	}

	@Override
	public boolean isChanged() {
		return changed;
	}

}
