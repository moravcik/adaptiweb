package com.adaptiweb.gwt.framework.modify;

import com.google.gwt.event.shared.EventHandler;

public interface ModifiedHandler extends EventHandler {

	void onModify(ModifiedEvent event);

}
