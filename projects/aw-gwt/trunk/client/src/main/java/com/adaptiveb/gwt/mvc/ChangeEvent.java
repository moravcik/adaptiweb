package com.adaptiveb.gwt.mvc;

import com.adaptiveb.gwt.mvc.FireableEvent;
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
