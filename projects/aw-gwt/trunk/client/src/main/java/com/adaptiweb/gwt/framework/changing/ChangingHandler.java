package com.adaptiweb.gwt.framework.changing;

import com.google.gwt.event.shared.EventHandler;

public interface ChangingHandler extends EventHandler {

	void onChanging(ChangingEvent event);

}
