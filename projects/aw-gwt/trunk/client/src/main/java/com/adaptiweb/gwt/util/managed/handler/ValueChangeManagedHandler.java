package com.adaptiweb.gwt.util.managed.handler;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class ValueChangeManagedHandler<T> extends AbstractManagedHandler implements ValueChangeHandler<T> {
	
	private final ValueChangeHandler<T> handler;
	
	private final HasValueChangeHandlers<T> source;
	
	private final ValueChangeHandler<T> wrapper = new ValueChangeHandler<T>() {
		public void onValueChange(ValueChangeEvent<T> event) {
			if (registration != null) handler.onValueChange(event);
		}
	};
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onValueChange(ValueChangeEvent)} and {@link #registerHandler(ValueChangeHandler)}.
	 */
	public ValueChangeManagedHandler() {
		this((HasValueChangeHandlers<T>) null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #registerHandler(ValueChangeHandler)}.
	 * @param handler
	 */
	public ValueChangeManagedHandler(ValueChangeHandler<T> handler) {
		this(handler, null);
	}
	
	/**
	 * If you use this constructor, you must overwrite method {@link #onValueChange(ValueChangeEvent)}.
	 * @param source
	 */
	public ValueChangeManagedHandler(HasValueChangeHandlers<T> source) {
		this.handler = this;
		this.source = source;
	}
	
	public ValueChangeManagedHandler(ValueChangeHandler<T> handler, HasValueChangeHandlers<T> source) {
		this.handler = handler;
		this.source = source;
	}

	@Override
	protected final HandlerRegistration registerHandler() {
		return registerHandler(wrapper, source);
	}
	
	protected final HandlerRegistration registerHandler(ValueChangeHandler<T> handler, HasValueChangeHandlers<T> source) {
		return source == null ? registerHandler(handler) : source.addValueChangeHandler(handler);
	}

	protected HandlerRegistration registerHandler(ValueChangeHandler<T> handler) {
		throw new RuntimeException("If no source was passed to constructor, this method sould be overwriten!");
	}

	@Override
	public void onValueChange(ValueChangeEvent<T> event) {
		throw new RuntimeException("If no handler was passed to constructor, this method should be overwriten!");
	}
}