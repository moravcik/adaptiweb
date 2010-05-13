package com.adaptiweb.gwt.util.managed.handler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;

public abstract class AbstractManagedHandler {

	protected HandlerRegistration registration;
	private Timer timer;

	public void registerAfter() {
		assert !isRegistered();
		timer().schedule(1);
	}

	private Timer timer() {
		if (timer == null) timer = new Timer() { public void run() { register(); } };
		return timer;
	}

	public void register() {
		assert !isRegistered();
		registration = registerHandler();
	}

	protected abstract HandlerRegistration registerHandler();

	public void unregister() {
		assert isRegistered();
		registration.removeHandler();
		registration = null;
	}

	public void ensureRegister() {
		if (!isRegistered()) register();
	}

	public void ensureUnregister() {
		if (isRegistered()) unregister();
	}

	public boolean isRegistered() {
		return registration != null;
	}

}
