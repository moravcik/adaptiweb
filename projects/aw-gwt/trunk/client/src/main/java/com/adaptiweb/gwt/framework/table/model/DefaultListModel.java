package com.adaptiweb.gwt.framework.table.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class DefaultListModel<T> implements ListModel<T> {
	
	private final ArrayList<T> records = new ArrayList<T>();
	private final HandlerManager handlers;
	
	public DefaultListModel() {
		this.handlers = new HandlerManager(this);
	}
	
	public DefaultListModel(HasHandlers target) {
		this();
		forwardEvents(target);
	}

	@Override
	public T get(int position) {
		return records.get(postitionToIndex(position));
	}

	@Override
	public int indexOf(T item) {
		return records.indexOf(item);
	}

	@Override
	public int size() {
		return records.size();
	}
	
	@Override
	public List<T> replace(int position, int count, List<T> item) {
		int index = postitionToIndex(position);
		List<T> removed = removeRecords(index, count, item);
		
		if (position == -1) item.addAll(item);
		else item.addAll(index, item);
		
		return removed;
	}

	private List<T> removeRecords(int index, int count, List<T> records) {
		assert count >= 0;
		if (count == 0) return Collections.emptyList();

		List<T> result = new ArrayList<T>(count);
		while (count-- > 0) result.add(records.remove(index));
		return result;
	}

	@Override
	public void refresh(int position, int count) {
		ListRefreshEvent.fire(this, postitionToIndex(position), count);
	}

	private int postitionToIndex(int position) {
		return position >= 0 ? position : records.size() + position;
	}

	@Override
	public HandlerRegistration addHandler(ListChangeHandler<T> handler) {
		return handlers.addHandler(ListChangeEvent.getType(), handler);
	}

	@Override
	public HandlerRegistration addHandler(ListRefreshHandler<T> handler) {
		return handlers.addHandler(ListRefreshEvent.getType(), handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlers.fireEvent(event);
	}

	public HandlerRegistration forwardEvents(final HasHandlers target) {
		return new HandlerRegistration() {
			
			private HandlerRegistration changeHandler = addHandler(new ListChangeHandler<T>() {
				
				@Override
				public void onListChange(ListChangeEvent<T> event) {
					target.fireEvent(event);
				}
			});
			
			private HandlerRegistration refreshHandler = addHandler(new ListRefreshHandler<T>() {

				@Override
				public void onListRefresh(ListRefreshEvent<T> event) {
					target.fireEvent(event);
				}
			});

			@Override
			public void removeHandler() {
				changeHandler.removeHandler();
				refreshHandler.removeHandler();
				changeHandler = refreshHandler = null;
			}
		};
	}
}
