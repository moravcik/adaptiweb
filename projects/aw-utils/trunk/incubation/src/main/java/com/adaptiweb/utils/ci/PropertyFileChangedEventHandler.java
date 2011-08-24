package com.adaptiweb.utils.ci;

import com.adaptiweb.utils.ci.event.EventHandler;

public interface PropertyFileChangedEventHandler  extends EventHandler {

	void onPropertyFileChanged(PropertyFileChangedEvent event);

}
