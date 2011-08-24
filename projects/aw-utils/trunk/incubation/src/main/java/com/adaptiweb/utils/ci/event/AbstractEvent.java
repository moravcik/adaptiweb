package com.adaptiweb.utils.ci.event;

public abstract class AbstractEvent<H extends EventHandler, S> {
	
	private final S source;
	private final long fireTime;
	private HandlerRegistration registration;
	private boolean cancelled;

	public AbstractEvent(S source) {
		this.source = source;
		this.fireTime = System.currentTimeMillis();
	}
	
	public S getSource() {
		return source;
	}
	
	public long getFireTime() {
		return fireTime;
	}
	
	public HandlerRegistration getRegistration() {
		return registration;
	}
	
	@SuppressWarnings("unchecked")
	final void dispatch(HandlerRegistration registration) {
		if (!registration.isActive()) return;
		this.registration = registration;
		dispatch((H) registration.getListener());
	}
	
	protected abstract void dispatch(H handler);

	public boolean isCancelled() {
		return cancelled;
	}
	
	public void chancel() {
		cancelled = true;
	}
}
