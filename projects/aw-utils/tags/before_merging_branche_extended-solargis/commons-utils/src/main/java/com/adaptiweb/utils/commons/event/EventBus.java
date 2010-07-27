package com.adaptiweb.utils.commons.event;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

public class EventBus implements Runnable {
	
	private final AtomicReference<Thread> thread = new AtomicReference<Thread>();
	
	private final ArrayBlockingQueue<AbstractEvent<?,?>> queue = new ArrayBlockingQueue<AbstractEvent<?,?>>(50);
	
	private final ConcurrentMap<Class<?>, Collection<HandlerRegistration>> listeners = new ConcurrentHashMap<Class<?>, Collection<HandlerRegistration>>();
	
	public boolean fireEvent(AbstractEvent<? extends EventHandler, ?> event) {
		if (isRunning()) return queue.offer(event);
		processEvent(event);
		return true;
	}

	public <T extends EventHandler> HandlerRegistration addHandler(Class<? extends AbstractEvent<T,?>> eventType, T handler) {
		Collection<HandlerRegistration> l = listeners.get(eventType);
		if (l == null) {
			l = new ConcurrentLinkedQueue<HandlerRegistration>();
			Collection<HandlerRegistration> a = listeners.putIfAbsent(eventType, l);
			if (a != null) l = a;
		}
		HandlerRegistration result = new HandlerRegistration(handler, l);
		l.add(result);
		return result;
	}
	
	@Override
	public void run() {
		try {
			Thread currentThread = Thread.currentThread();
			while (thread.get() == currentThread) {
				processEvent(queue.take());
			}
		} catch (InterruptedException ignore) {}
	}

	private void processEvent(AbstractEvent<? extends EventHandler, ?> event) {
		Collection<HandlerRegistration> registrations;
		for (Class<?> type = event.getClass(); !AbstractEvent.class.equals(type); type = type.getSuperclass()) {
			if ((registrations = listeners.get(type)) != null) {
				for (HandlerRegistration r : registrations) {
					event.dispatch(r);
					if (event.isCancelled()) return;
				}
			}
		}
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.setName("event-bus");
		if (this.thread.compareAndSet(null, thread)) {
			thread.start();
		}
	}
	
	public boolean isRunning() {
		return thread.get() != null;
	}
	
	public void stop() {
		Thread thread = this.thread.getAndSet(null);
		if (thread != null) thread.interrupt();
	}
}
