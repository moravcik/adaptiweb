package com.adaptiweb.gwt.framework.table.model;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasListChangeHandlers<T> extends HasHandlers {
	
	HandlerRegistration addHandler(ListChangeHandler<T> handler);
}
