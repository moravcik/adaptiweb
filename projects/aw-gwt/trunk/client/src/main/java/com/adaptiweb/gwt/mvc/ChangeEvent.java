package com.adaptiweb.gwt.mvc;



public class ChangeEvent<T> implements FireableEvent<ChangeListener<T>> {
	
	private final T source;
	
	public ChangeEvent(T source) {
		this.source = source;
	}

	public void fireOnListener(ChangeListener<T> listener) {
		listener.onChange(source);
	}
	
}
