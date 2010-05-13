package com.adaptiweb.gwt.framework.table.model;

import com.google.gwt.event.shared.GwtEvent;

public class ListRefreshEvent<R> extends GwtEvent<ListRefreshHandler<R>> {

	private static Type<ListRefreshHandler<?>> TYPE;
	private final int index;
	private final int count;
	private final ListModel<R> model;

	protected ListRefreshEvent(ListModel<R> model, int index, int count) {
		this.model = model;
		this.index = index;
		this.count = count;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getCount() {
		return count;
	}
	
	public ListModel<R> getModel() {
		return model;
	}
	
	public static <I> void fire(ListModel<I> model, int index, int count) {
		if (TYPE != null) model.fireEvent(new ListRefreshEvent<I>(model, index, count));
	}

	@Override
	protected void dispatch(ListRefreshHandler<R> handler) {
		handler.onListRefresh(this);
	}

	@SuppressWarnings("unchecked")
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
		return super.toDebugString() + " refreshed " + count + " from inedx=" + index;
	}

}
