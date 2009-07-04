package com.adaptiweb.gwt.framework.table.model;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public interface ListModel<T> extends HasListChangeHandlers<T>, HasListRefreshHandlers<T> {
	
	List<T> replace(int position, int count, List<T> iListModeltem);
	
	void refresh(int position, int count);
	
	int indexOf(T item);

	T get(int position);

	int size();

	void fireEvent(GwtEvent<?> event);

}
