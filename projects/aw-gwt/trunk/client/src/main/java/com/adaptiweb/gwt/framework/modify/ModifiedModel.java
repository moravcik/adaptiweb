package com.adaptiweb.gwt.framework.modify;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface ModifiedModel extends HasHandlers {

	boolean isModified();

	HandlerRegistration addModifiedHandler(ModifiedHandler handler, boolean fireInitEvent);

}
