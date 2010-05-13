package com.adaptiweb.gwt.mvc;


public interface FireableEvent<L> {
	
	void fireOnListener(L listener);
}
