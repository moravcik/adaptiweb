package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.ui.Widget;

public class StyleFactory {

	/**
	 * Method will assign a custom {@arg value} to style {@arg property}.
	 * @param w Widget to apply a style
	 * @param property style
	 * @param value to set
	 */
	public static void applyStyle(String property, String value, Widget...widgets) {
		for(Widget w : widgets)
			w.getElement().getStyle().setProperty(property, value);
	}
}
