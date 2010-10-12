package com.adaptiweb.gwt.util.managed.handler;

import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class KeyUpManagedHandler extends AbstractManagedHandler implements KeyUpHandler {
	
	private final KeyUpHandler handler;
	
	private final HasKeyUpHandlers source;
	
	private final KeyUpHandler wrapper = new KeyUpHandler() {
		public void onKeyUp(KeyUpEvent event) {
			if (registration != null) handler.onKeyUp(event);
		}
	};
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onKeyUp(KeyUpEvent)} and {@link #registerHandler(KeyUpHandler)}.
	 */
	public KeyUpManagedHandler() {
		this((HasKeyUpHandlers) null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #registerHandler(KeyUpHandler)}.
	 * @param handler
	 */
	public KeyUpManagedHandler(KeyUpHandler handler) {
		this(handler, null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onKeyUp(KeyUpEvent)}.
	 * @param source
	 */
	public KeyUpManagedHandler(HasKeyUpHandlers source) {
		this.handler = this;
		this.source = source;
	}
	
	public KeyUpManagedHandler(KeyUpHandler handler, HasKeyUpHandlers source) {
		this.handler = handler;
		this.source = source;
	}

	@Override
	protected final HandlerRegistration registerHandler() {
		return registerHandler(wrapper, source);
	}
	
	protected final HandlerRegistration registerHandler(KeyUpHandler handler, HasKeyUpHandlers source) {
		return source == null ? registerHandler(handler) : source.addKeyUpHandler(handler);
	}

	protected HandlerRegistration registerHandler(KeyUpHandler handler) {
		throw new RuntimeException("If no source was passed to constructor, this method sould be overwriten!");
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		throw new RuntimeException("If no handler was passed to constructor, this method should be overwriten!");
	}
}