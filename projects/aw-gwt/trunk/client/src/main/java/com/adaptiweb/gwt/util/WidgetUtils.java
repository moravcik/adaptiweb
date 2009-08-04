package com.adaptiweb.gwt.util;

import com.google.gwt.user.client.ui.Widget;

public class WidgetUtils {

	public static boolean isOver(Widget widget, int clientX, int clientY) {
		return widget.getAbsoluteLeft() <= clientX 
			&& widget.getAbsoluteLeft() + widget.getOffsetWidth() >= clientX
			&& widget.getAbsoluteTop() <= clientY 
			&& widget.getAbsoluteTop() + widget.getOffsetHeight() >= clientY;
	}
	
}
