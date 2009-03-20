package com.adaptiveb.gwt.mvc;


public interface FireableEvent<L> {
	
	void fireOnListener(L listener);
}
