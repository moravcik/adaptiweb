package com.adaptiweb.gwt.util.managed.handler;

import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class BlurManagedHandler extends AbstractManagedHandler implements BlurHandler {
	
	private final BlurHandler handler;
	
	private final HasBlurHandlers source;
	
	private final BlurHandler wrapper = new BlurHandler() {
		public void onBlur(BlurEvent event) {
			if (registration != null) handler.onBlur(event);
		}
	};
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onBlur(BlurEvent)} and {@link #registerHandler(BlurHandler)}.
	 */
	public BlurManagedHandler() {
		this((HasBlurHandlers) null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #registerHandler(BlurHandler)}.
	 * @param handler
	 */
	public BlurManagedHandler(BlurHandler handler) {
		this(handler, null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onBlur(BlurEvent)}.
	 * @param source
	 */
	public BlurManagedHandler(HasBlurHandlers source) {
		this.handler = this;
		this.source = source;
	}
	
	public BlurManagedHandler(BlurHandler handler, HasBlurHandlers source) {
		this.handler = handler;
		this.source = source;
	}

	@Override
	protected final HandlerRegistration registerHandler() {
		return registerHandler(wrapper, source);
	}
	
	protected final HandlerRegistration registerHandler(BlurHandler handler, HasBlurHandlers source) {
		return source == null ? registerHandler(handler) : source.addBlurHandler(handler);
	}

	protected HandlerRegistration registerHandler(BlurHandler handler) {
		throw new RuntimeException("If no source was passed to constructor, this method sould be overwriten!");
	}

	@Override
	public void onBlur(BlurEvent event) {
		throw new RuntimeException("If no handler was passed to constructor, this method should be overwriten!");
	}
}