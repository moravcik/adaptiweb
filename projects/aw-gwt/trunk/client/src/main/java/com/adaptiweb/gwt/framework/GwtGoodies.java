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
		return String.valueOf(obj);
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

	public static boolean isVisible(Widget widget) {
		if (!widget.isVisible()) return false;
		for(
			Element element = widget.getElement();
			element != null;
			element = element.getParentElement()
		) if ("none".equals(element.getStyle().getProperty("display"))) return false;
		
		return true;
	}
	
	/**
	 * Pattern can contains index reference to params array.
	 * Format is <b>$<i>n</i></b>, where <b><i>n</i></b> is 0-based index to parameters.
	 * If character '$' need to be included in string, use double "$$".
	 * @param pattern
	 * @param params
	 * @return formated string
	 */
	public static String format(String pattern, Object...params) {
		StringBuilder sb = new StringBuilder();
		boolean parsingIndex = false;
		int indexValue = -1;
		
		for (char c : pattern.toCharArray()) {
			if (parsingIndex && Character.isDigit(c)) {
				indexValue = indexValue == -1 ? c - '0' : (indexValue * 10 + c - '0');
			}
			else if (parsingIndex && indexValue != -1) {
				sb.append(String.valueOf(params[indexValue]));
				indexValue = -1;
				parsingIndex = c == '$';
			}
			else {
				parsingIndex = !parsingIndex && c == '$';
			}
			if (!parsingIndex) sb.append(c);
		}
		if (indexValue != -1) sb.append(params[indexValue]);
		return sb.toString();
	}
}
