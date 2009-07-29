package com.adaptiweb.gwt.mvc;



public interface SourceChangeEvents<T /*extends Widget*/> {
	
	void addChangeListener(ChangeListener<T> l);
	
	void removeChangeListener(ChangeListener<T> l);

}
