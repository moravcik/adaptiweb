package com.adaptiweb.gwt.framework.table.model;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class ListRefreshEvent<R> extends GwtEvent<ListRefreshHandler<R>> {

	private static Type<ListRefreshHandler<?>> TYPE;
	private final int index;
	private final List<R> refreshed;

	protected ListRefreshEvent(int index, List<R> refreshed) {
		this.index = index;
		this.refreshed = refreshed;
	}
	
	public int getIndex() {
		return index;
	}
	
	public List<R> getRefreshed() {
		return refreshed;
	}
	
	public static <I> 
	void fire(HasListRefreshHandlers<I> source, int index, List<I> refreshed) {
		if (TYPE != null) source.fireEvent(new ListRefreshEvent<I>(index, refreshed));
	}

	@Override
	protected void dispatch(ListRefreshHandler<R> handler) {
		handler.onListRefresh(this);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Type<ListRefreshHandler<R>> getAssociatedType() {
		return (Type) TYPE;
	}

	public static Type<ListRefreshHandler<?>> getType() {
		if (TYPE == null) TYPE = new Type<ListRefreshHandler<?>>();
		return TYPE;
	}

	@Override
	public String toDebugString() {
		return super.toDebugString() + " refreshed " + refreshed.size() + " from index=" + index;
	}

}
