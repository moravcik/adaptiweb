package com.adaptiweb.gwt.framework.modify;



public interface ModifiedModelSet extends ModifiedModel, Iterable<ModifiedModel> {

	boolean add(ModifiedModel...models);
	
	boolean remove(ModifiedModel...models);
	
	int size();
}
