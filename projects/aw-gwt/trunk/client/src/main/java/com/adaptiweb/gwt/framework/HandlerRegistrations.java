package com.adaptiweb.gwt.framework;

import java.util.LinkedList;

import com.google.gwt.event.shared.HandlerRegistration;

public class HandlerRegistrations {
	
	private final LinkedList<HandlerRegistration> registrations = new LinkedList<HandlerRegistration>();
	
	public HandlerRegistration add(HandlerRegistration registration) {
		registrations.add(registration);
		return registration;
	}
	
	public void discard() {
		for (HandlerRegistration registration : registrations)
			registration.removeHandler();
	}
}
