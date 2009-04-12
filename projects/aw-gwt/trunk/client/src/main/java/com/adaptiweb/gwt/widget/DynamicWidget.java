package com.adaptiweb.gwt.widget;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

public class DynamicWidget extends FocusWidget {
	
	private Map<Element, Widget> enclosedWidgets;
	
	protected void registerChildWidget(Widget widget) {
		if(widget != this && DOM.isOrHasChild(getElement(), widget.getElement())) {
			if(enclosedWidgets == null) enclosedWidgets = new HashMap<Element, Widget>();
			enclosedWidgets.put(widget.getElement(), widget);
		}
		else throw new IllegalArgumentException();
	}

	protected void unregisterChildWidget(Widget widget) {
		if(enclosedWidgets != null) enclosedWidgets.remove(widget.getElement());
	}

	private boolean firedToEnclosedWidget(Event event) {
		
		if(enclosedWidgets != null && enclosedWidgets.size() > 0) {
			Element currentTarget = event.getEventTarget().cast();
			
			if(getElement() != currentTarget) {
				Widget enclosed = enclosedWidgets.get(currentTarget);
				
				if(enclosed != null) {
					enclosed.onBrowserEvent(event);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onBrowserEvent(Event event) {
		firedToEnclosedWidget(event);
		super.onBrowserEvent(event);
	}
}
