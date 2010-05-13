package com.adaptiweb.gwt.framework.visibility;

import com.google.gwt.event.shared.EventHandler;

public interface VisibilitityHandler extends EventHandler {

	void onVisibilityChange(VisibitilyEvent event);
}
