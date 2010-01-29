package com.adaptiweb.gwt.mvc;

/**
 * Use google SourceChangeEvents instead
 */
@Deprecated
public interface SourceChangeEvents<T /*extends Widget*/> {
	
	void addChangeListener(ChangeListener<T> l);
	
	void removeChangeListener(ChangeListener<T> l);

}
