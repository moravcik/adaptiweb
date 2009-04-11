package com.adaptiweb.gwt.widget;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.Widget;

public class DynamicWidget extends Widget {
	
	private Map<Element, Widget> enclosedWidgets;

	/**
	 * Same as {@link KeyboardListenerCollection#getKeyboardModifiers(Event)}
	 * @param event
	 * @return
	 */
	protected static int getKeyboardModifiers(Event event) {
		return (DOM.eventGetShiftKey(event) ? KeyboardListener.MODIFIER_SHIFT : 0)
			| (DOM.eventGetMetaKey(event) ? KeyboardListener.MODIFIER_META : 0)
			| (DOM.eventGetCtrlKey(event) ? KeyboardListener.MODIFIER_CTRL : 0)
			| (DOM.eventGetAltKey(event) ? KeyboardListener.MODIFIER_ALT : 0);
	}

	protected static char getKeyCode(Event event) {
		return (char) DOM.eventGetKeyCode(event);
	}

	protected static int getMouseCoordinateX(Element element, Event event) {
		return DOM.eventGetClientX(event)
			- DOM.getAbsoluteLeft(element)
			+ DOM.getElementPropertyInt(element, "scrollLeft")
			+ Window.getScrollLeft();
	}

	protected static int getMouseCoordinateY(Element element, Event event) {
		return DOM.eventGetClientY(event)
		    - DOM.getAbsoluteTop(element)
		    + DOM.getElementPropertyInt(element, "scrollTop")
		    + Window.getScrollTop();
	}
	
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
		if(firedToEnclosedWidget(event)) return;
		
		switch (event.getTypeInt()) {
		case Event.ONBLUR:
			onBlur(event);
			break;
		case Event.ONCHANGE:
			onChange(event);
			break;
		case Event.ONCLICK:
			onClick(event);
			break;
		case Event.ONCONTEXTMENU:
			onContextMenu(event);
			break;
		case Event.ONDBLCLICK:
			onDblClick(event);
			break;
		case Event.ONERROR:
			onError(event);
			break;
		case Event.ONFOCUS:
			onFocus(event);
			break;
		case Event.ONKEYDOWN:
			onKeyDown(event);
			break;
		case Event.ONKEYUP:
			onKeyUp(event);
			break;
		case Event.ONKEYPRESS:
			onKeyPress(event);
			break;
		case Event.ONLOSECAPTURE:
			onLoseCapture(event);
			break;
		case Event.ONMOUSEDOWN:
			onMouseDown(event);
			break;
		case Event.ONMOUSEMOVE:
			onMouseMove(event);
			break;
		case Event.ONMOUSEOUT:
	        // Only fire the mouseLeave event if it's actually leaving this
	        // widget.
	        Element to = DOM.eventGetToElement(event);
	        if (to == null || !DOM.isOrHasChild(getElement(), to))
				onMouseOut(event);
			break;
		case Event.ONMOUSEOVER:
	        // Only fire the mouseEnter event if it's coming from outside this
	        // widget.
	        Element from = DOM.eventGetFromElement(event);
	        if (from == null || !DOM.isOrHasChild(getElement(), from))
				onMouseOver(event);
			break;
		case Event.ONMOUSEUP:
			onMouseUp(event);
			break;
		case Event.ONMOUSEWHEEL:
			onMouseWheel(event);
			break;
		case Event.ONSCROLL:
			onMouseScroll(event);
			break;
		}
	    DomEvent.fireNativeEvent(event, this, this.getElement());
	}

	protected void onMouseScroll(Event event) {}

	protected void onMouseWheel(Event event) {}

	protected void onMouseUp(Event event) {}

	protected void onMouseOver(Event event) {}

	protected void onMouseOut(Event event) {}

	protected void onMouseMove(Event event) {}

	protected void onMouseDown(Event event) {}

	protected void onLoseCapture(Event event) {}

	/**
	 * @see KeyboardListener
	 * @param event
	 */
	protected void onKeyUp(Event event) {}

	/**
	 * @see KeyboardListener
	 * @param event
	 */
	protected void onKeyPress(Event event) {}

	/**
	 * @see KeyboardListener
	 * @param event
	 */
	protected void onKeyDown(Event event) {}

	protected void onFocus(Event event) {}

	protected void onError(Event event) {}

	protected void onDblClick(Event event) {}

	protected void onContextMenu(Event event) {}

	protected void onClick(Event event) {}

	protected void onChange(Event event) {}

	protected void onBlur(Event event) {}
}
