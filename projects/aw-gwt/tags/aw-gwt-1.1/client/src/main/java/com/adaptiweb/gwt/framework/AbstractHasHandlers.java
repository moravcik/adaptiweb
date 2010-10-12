package com.adaptiweb.gwt.framework;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;


public class AbstractHasHandlers implements HasHandlers {
	
	protected final HandlerManager handlers;
	
	protected AbstractHasHandlers() {
		this(null);
	}
	
	protected AbstractHasHandlers(Object source) {
		handlers = new HandlerManager(source == null ? this : source);
	}

	public final void fireEvent(GwtEvent<?> event) {
		handlers.fireEvent(event);
	}

}
