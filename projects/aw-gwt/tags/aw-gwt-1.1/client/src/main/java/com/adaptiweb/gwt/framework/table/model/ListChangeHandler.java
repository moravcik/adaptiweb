package com.adaptiweb.gwt.framework.table.model;

import com.google.gwt.event.shared.EventHandler;

public interface ListChangeHandler<T> extends EventHandler {

	void onListChange(ListChangeEvent<T> event);
}
