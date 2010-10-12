package com.adaptiweb.gwt.util.managed.handler;

import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class KeyDownManagedHandler extends AbstractManagedHandler implements KeyDownHandler {
	
	private final KeyDownHandler handler;
	
	private final HasKeyDownHandlers source;
	
	private final KeyDownHandler wrapper = new KeyDownHandler() {
		public void onKeyDown(KeyDownEvent event) {
			if (registration != null) handler.onKeyDown(event);
		}
	};
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onKeyDown(KeyDownEvent)} and {@link #registerHandler(KeyDownHandler)}.
	 */
	public KeyDownManagedHandler() {
		this((HasKeyDownHandlers) null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #registerHandler(KeyDownHandler)}.
	 * @param handler
	 */
	public KeyDownManagedHandler(KeyDownHandler handler) {
		this(handler, null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onKeyDown(KeyDownEvent)}.
	 * @param source
	 */
	public KeyDownManagedHandler(HasKeyDownHandlers source) {
		this.handler = this;
		this.source = source;
	}
	
	public KeyDownManagedHandler(KeyDownHandler handler, HasKeyDownHandlers source) {
		this.handler = handler;
		this.source = source;
	}

	@Override
	protected final HandlerRegistration registerHandler() {
		return registerHandler(wrapper, source);
	}
	
	protected final HandlerRegistration registerHandler(KeyDownHandler handler, HasKeyDownHandlers source) {
		return source == null ? registerHandler(handler) : source.addKeyDownHandler(handler);
	}

	protected HandlerRegistration registerHandler(KeyDownHandler handler) {
		throw new RuntimeException("If no source was passed to constructor, this method sould be overwriten!");
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		throw new RuntimeException("If no handler was passed to constructor, this method should be overwriten!");
	}
}