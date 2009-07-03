package com.adaptiweb.gwt.framework.changing;


public interface ChangingModelSet extends ChangingModel {

	boolean add(ChangingModelSet...models);
	
	boolean remove(ChangingModelSet...models);
	
	int size();
}
