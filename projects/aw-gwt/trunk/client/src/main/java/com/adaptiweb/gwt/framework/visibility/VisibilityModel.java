package com.adaptiweb.gwt.framework.visibility;

import com.adaptiweb.gwt.mvc.Model;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface VisibilityModel extends HasHandlers, Model {

	boolean isVisible();
	
	void setVisible(boolean visible);

	HandlerRegistration addVisibilityHandler(VisibilitityHandler handler);
	
	HandlerRegistration initAndAddVisibilityHandler(VisibilitityHandler handler);

}
