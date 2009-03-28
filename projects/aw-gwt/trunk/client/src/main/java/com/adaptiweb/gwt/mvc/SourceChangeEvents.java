package com.adaptiweb.gwt.mvc;

import com.google.gwt.user.client.ui.Widget;


public interface SourceChangeEvents<T extends Widget> {
	
	void addChangeListener(ChangeListener<T> l);
	
	void removeChangeListener(ChangeListener<T> l);

}
