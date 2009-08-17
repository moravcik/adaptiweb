package com.adaptiweb.gwt.framework.visibility;

import com.adaptiweb.gwt.framework.AbstractHasHandlers;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;
import com.google.gwt.event.shared.HandlerRegistration;

public class DefaultVisibilityModel extends AbstractHasHandlers implements VisibilityModel, HasDebugInfo {
	
	private boolean visible = true;
	
	public DefaultVisibilityModel() {}
	
	public DefaultVisibilityModel(boolean initialVisibleStatus) {
		visible = initialVisibleStatus;
	}

	public void setVisible(boolean value) {
		if (this.visible == value) return;
		this.visible = value;
		VisibitilyEvent.fire(this);
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public HandlerRegistration addVisibilityHandler(VisibilitityHandler handler) {
		return handlers.addHandler(VisibitilyEvent.getType(), handler);
	}
	
	@Override
	public HandlerRegistration initAndAddVisibilityHandler(VisibilitityHandler handler) {
		new VisibitilyEvent(this).dispatch(handler);
		return handlers.addHandler(VisibitilyEvent.getType(), handler);
	}
	
	@Override
	public String toDebugString() {
		return toDebugString(GwtGoodies.simpleClassName(getClass().getName()));
	}

	protected String toDebugString(String type) {
		return type + ":[" + isVisible() + "]";
	}
}
