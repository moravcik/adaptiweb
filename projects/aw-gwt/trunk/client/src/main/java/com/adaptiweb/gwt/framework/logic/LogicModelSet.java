package com.adaptiweb.gwt.framework.logic;

import java.util.List;



public interface LogicModelSet extends LogicModel, Iterable<LogicModel> {

	LogicModelSet add(LogicModel model);
	
	LogicModelSet remove(LogicModel model);
	
	boolean contains(LogicModel model);
	
	List<LogicModel> collectLeafs();
	
	int size();
}
