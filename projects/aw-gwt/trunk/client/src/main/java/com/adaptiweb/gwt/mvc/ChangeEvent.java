package com.adaptiweb.gwt.mvc;

import com.adaptiweb.gwt.mvc.FireableEvent;
import com.google.gwt.user.client.ui.Widget;


public class ChangeEvent<T extends Widget> implements FireableEvent<ChangeListener<T>> {
	
	private final T widget;
	
	public ChangeEvent(T widget) {
		this.widget = widget;
	}

	public void fireOnListener(ChangeListener<T> listener) {
		listener.onChange(widget);
	}
	
}
