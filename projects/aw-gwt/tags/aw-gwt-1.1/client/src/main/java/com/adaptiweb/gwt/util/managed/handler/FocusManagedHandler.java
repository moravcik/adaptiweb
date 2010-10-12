package com.adaptiweb.gwt.util.managed.handler;

import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class FocusManagedHandler extends AbstractManagedHandler implements FocusHandler {
	
	private final FocusHandler handler;
	
	private final HasFocusHandlers source;
	
	private final FocusHandler wrapper = new FocusHandler() {
		public void onFocus(FocusEvent event) {
			if (registration != null) handler.onFocus(event);
		}
	};
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onFocus(FocusEvent)} and {@link #registerHandler(FocusHandler)}.
	 */
	public FocusManagedHandler() {
		this((HasFocusHandlers) null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #registerHandler(FocusHandler)}.
	 * @param handler
	 */
	public FocusManagedHandler(FocusHandler handler) {
		this(handler, null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onFocus(FocusEvent)}.
	 * @param source
	 */
	public FocusManagedHandler(HasFocusHandlers source) {
		this.handler = this;
		this.source = source;
	}
	
	public FocusManagedHandler(FocusHandler handler, HasFocusHandlers source) {
		this.handler = handler;
		this.source = source;
	}

	@Override
	protected final HandlerRegistration registerHandler() {
		return registerHandler(wrapper, source);
	}
	
	protected final HandlerRegistration registerHandler(FocusHandler handler, HasFocusHandlers source) {
		return source == null ? registerHandler(handler) : source.addFocusHandler(handler);
	}

	protected HandlerRegistration registerHandler(FocusHandler handler) {
		throw new RuntimeException("If no source was passed to constructor, this method sould be overwriten!");
	}

	@Override
	public void onFocus(FocusEvent event) {
		throw new RuntimeException("If no handler was passed to constructor, this method should be overwriten!");
	}
}