package com.adaptiweb.gwt.mvc;

import com.google.gwt.user.client.ui.Widget;


public interface ChangeListener<T extends Widget> {
	
	void onChange(T widget);
}
