package com.adaptiweb.gwt.framework.logic;



public interface LogicModelSet extends LogicModel, Iterable<LogicModel> {

	LogicModelSet add(LogicModel model);
	
	LogicModelSet remove(LogicModel model);
	
	boolean contains(LogicModel model);
	
	int size();
}
