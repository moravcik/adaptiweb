package com.adaptiweb.utils.ci.event;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public final class HandlerRegistration {
	
	private AtomicBoolean active = new AtomicBoolean(true);
	private EventHandler listener;
	private Collection<HandlerRegistration> register;
	
	HandlerRegistration(EventHandler listener, Collection<HandlerRegistration> register) {
		this.listener = listener;
		this.register = register;
	}
	
	EventHandler getListener() {
		return listener;
	}

	public void unregister() {
		assert register.remove(this);
		register = null;
		listener = null;
		active = null;
	}
	
	public void suspend() {
		active.set(false);
	}
	
	public void resume() {
		active.set(true);
	}
	
	public boolean isActive() {
		return active.get();
	}
}
