package com.adaptiweb.gwt.framework.changing;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface ChangingModel extends HasHandlers {

	boolean isChanged();

	HandlerRegistration addChangingHandler(ChangingHandler handler, boolean fireInitEvent);

}
