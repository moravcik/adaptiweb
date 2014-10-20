package com.adaptiweb.gwt.framework.table.model;

import java.util.List;

import com.adaptiweb.gwt.mvc.Model;
import com.google.gwt.event.shared.GwtEvent;

public interface ListModel<T> extends HasListChangeHandlers<T>, HasListRefreshHandlers<T>, Model {
	
	List<T> replace(int position, int count, List<T> iListModeltem);
	
	void refresh(int position, int count);
	
	int indexOf(T item);

	T get(int position);

	int size();
	
	boolean isEmpty();

	void fireEvent(GwtEvent<?> event);

	// helper methods
	public void remove(T item);
	public void removeAll();
	public void removeAll(List<T> items);
	public void add(T item);
	public void addAll(List<T> items);
	public void setAll(List<T> items);
	public void replace(T item);
	public void refresh(T item);

	public List<T> getItems();
}
