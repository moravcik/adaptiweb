package com.adaptiweb.gwt.framework.table.model;

import com.google.gwt.event.shared.HandlerRegistration;

public interface HasListRefreshHandlers<T> {
	
	HandlerRegistration addHandler(ListRefreshHandler<T> handler);
}
