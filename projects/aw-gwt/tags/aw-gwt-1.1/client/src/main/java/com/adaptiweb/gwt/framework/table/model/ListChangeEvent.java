package com.adaptiweb.gwt.framework.table.model;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class ListChangeEvent<T> extends GwtEvent<ListChangeHandler<T>> {

	private static Type<ListChangeHandler<?>> TYPE;
	private final int index;
	private final List<T> removed;
	private final List<T> inserted;

	protected ListChangeEvent(int index, List<T> removed, List<T> inserted) {
		this.index = index;
		this.removed = removed;
		this.inserted = inserted;
	}
	
	public int getIndex() {
		return index;
	}
	
	public List<T> getRemovedRecords() {
		return removed;
	}
	
	public List<T> getInsertedRecords() {
		return inserted;
	}
	
	public static <I> 
	void fire(HasListChangeHandlers<I> source, int index, List<I> removed, List<I> inserted) {
		if (TYPE != null) source.fireEvent(new ListChangeEvent<I>(index, removed, inserted));
	}

	@Override
	protected void dispatch(ListChangeHandler<T> handler) {
		handler.onListChange(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<ListChangeHandler<T>> getAssociatedType() {
		return (Type) TYPE;
	}

	public static Type<ListChangeHandler<?>> getType() {
		if (TYPE == null) TYPE = new Type<ListChangeHandler<?>>();
		return TYPE;
	}

	@Override
	public String toDebugString() {
		return super.toDebugString()
			+ " removed=" + removed.size()
			+ " inserted=" + inserted.size()
			+ " at index=" + index;
	}

}
