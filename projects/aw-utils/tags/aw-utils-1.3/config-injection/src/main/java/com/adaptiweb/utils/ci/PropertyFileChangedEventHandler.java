package com.adaptiweb.utils.ci;

import com.adaptiweb.utils.commons.event.EventHandler;

public interface PropertyFileChangedEventHandler  extends EventHandler {

	void onPropertyFileChanged(PropertyFileChangedEvent event);

}
