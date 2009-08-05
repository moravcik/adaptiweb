package com.adaptiweb.gwt.framework;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class GwtGoodies {
    
	/**
     * Returns HTML element specified by its id attribute.
     * 
     * @param id element identifier
     * @return HTML element or null if not exists
     */
    public static Element getElementById(ElementIdentifier id) {
        Element elementById = DOM.getElementById(id.getElementId());
        if (elementById == null)
            GWT.log("element with id:" + id + "("+id.getElementId()+") not found!", null);
        return elementById;
    }
    
    public static boolean areEquals(Object original, Object actual) {
    	return original == null ? actual == null : original.equals(actual);
    }
    
    public static boolean isSameIn(Object item, Object...set) {
    	for (Object x : set)
    		if (x == item)
    			return true;
    	return false;
    }

	public static String toDebugString(Object obj) {
		if (obj instanceof HasDebugInfo)
			return ((HasDebugInfo) obj).toDebugString();
		return obj.toString();
	}

	public static String simpleClassName(String name) {
		int index = name.lastIndexOf('.');
		return index == -1 ? name : name.substring(index + 1);
	}

	public static boolean isOver(Widget widget, int clientX, int clientY) {
		return widget.getAbsoluteLeft() <= clientX 
			&& widget.getAbsoluteLeft() + widget.getOffsetWidth() >= clientX
			&& widget.getAbsoluteTop() <= clientY 
			&& widget.getAbsoluteTop() + widget.getOffsetHeight() >= clientY;
	}
}
