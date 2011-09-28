package com.adaptiweb.gwt.framework;

import java.util.IdentityHashMap;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

public abstract class GwtEventHelper {

	private static EventBus eventBus;

	private static Map<Class<? extends EventHandler>, Type<? extends EventHandler>> types; 
	
	private static EventBus getEventBus() {
		return eventBus == null ? (eventBus = new SimpleEventBus()) : eventBus;
	}

	@SuppressWarnings("unchecked")
	public static <H extends EventHandler> Type<H> getType(Class<H> handlerClass) {
		if (types == null) { 
			types = new IdentityHashMap<Class<? extends EventHandler>, Type<? extends EventHandler>>();
		}
		if (!types.containsKey(handlerClass)) {
			types.put(handlerClass, new Type<H>());
		}
		return (Type<H>) types.get(handlerClass);
	}
	
	public static <H extends EventHandler> HandlerRegistration addHandler(Class<H> handlerClass, H handler) {
		Type<H> type = GwtEventHelper.getType(handlerClass);
		return getEventBus().addHandler(type, handler);
	}
	
	public static <E extends GwtEvent<?>> void fireEvent(E event) {
		getEventBus().fireEvent(event);
	}
	
}
