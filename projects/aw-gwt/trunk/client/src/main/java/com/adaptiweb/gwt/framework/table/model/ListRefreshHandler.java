package com.adaptiweb.gwt.framework.table.model;

import com.google.gwt.event.shared.EventHandler;

public interface ListRefreshHandler<T> extends EventHandler {
	
	void onListRefresh(ListRefreshEvent<T> event);
}
