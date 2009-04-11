package com.adaptiweb.gwt.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusListenerCollection;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.impl.FocusImpl;

public class FireWidget extends DynamicWidget {

	private static final FocusImpl impl = FocusImpl.getFocusImplForWidget();
	private FocusListenerCollection focusListeners;
	private KeyboardListenerCollection keyboardListeners;
	private MouseListenerCollection mouseListeners;

	public FireWidget(Element element, boolean focusable) {
		super.setElement(element);
		if (focusable) setTabIndex(0);
	}

	@Override
	protected void setElement(Element elem) {
		throw new IllegalStateException("Use constructor for set element!");
	}

	public int getTabIndex() {
		return impl.getTabIndex(getElement());
	}

	public void setAccessKey(char key) {
		impl.setAccessKey(getElement(), key);
	}

	public void setFocus(boolean focused) {
		if (focused) impl.focus(getElement());
		else impl.blur(getElement());
	}

	public void setTabIndex(int index) {
		impl.setTabIndex(getElement(), index);
	}

	public void addFocusListener(FocusListener listener) {
		if (focusListeners == null) {
			focusListeners = new FocusListenerCollection();
			sinkEvents(Event.FOCUSEVENTS);
		}
		focusListeners.add(listener);
	}

	public void removeFocusListener(FocusListener listener) {
		if (focusListeners != null) focusListeners.remove(listener);
	}

	public void addKeyboardListener(KeyboardListener listener) {
		if (keyboardListeners == null) {
			keyboardListeners = new KeyboardListenerCollection();
			sinkEvents(Event.KEYEVENTS);
		}
		keyboardListeners.add(listener);
	}

	public void removeKeyboardListener(KeyboardListener listener) {
		if (keyboardListeners != null) keyboardListeners.remove(listener);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
	    return addDomHandler(handler, ClickEvent.getType());
	}
	
	public void addMouseListener(MouseListener listener) {
		if (mouseListeners == null) {
			mouseListeners = new MouseListenerCollection();
			sinkEvents(Event.MOUSEEVENTS);
		}
		mouseListeners.add(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		if (mouseListeners != null) mouseListeners.remove(listener);
	}

	@Override
	protected void onKeyDown(Event event) {
		if (keyboardListeners != null) 
			keyboardListeners.fireKeyDown(this, getKeyCode(event), getKeyboardModifiers(event));
	}

	@Override
	protected void onKeyPress(Event event) {
		if (keyboardListeners != null)
			keyboardListeners.fireKeyPress(this, getKeyCode(event), getKeyboardModifiers(event));
	}

	@Override
	protected void onKeyUp(Event event) {
		if (keyboardListeners != null)
			keyboardListeners.fireKeyUp(this, getKeyCode(event), getKeyboardModifiers(event));
	}

	@Override
	protected void onFocus(Event event) {
		if (focusListeners != null) focusListeners.fireFocus(this);
	}

	@Override
	protected void onBlur(Event event) {
		if (focusListeners != null) focusListeners.fireLostFocus(this);
	}
	
	@Override
	protected void onMouseDown(Event event) {
		if (mouseListeners != null) {
			int x = getMouseCoordinateX(getElement(), event);
			int y = getMouseCoordinateY(getElement(), event);
			mouseListeners.fireMouseDown(this, x, y);
		}
	}
	
	@Override
	protected void onMouseMove(Event event) {
		if (mouseListeners != null) {
			int x = getMouseCoordinateX(getElement(), event);
			int y = getMouseCoordinateY(getElement(), event);
			mouseListeners.fireMouseMove(this, x, y);
		}
	}
	
	@Override
	protected void onMouseOut(Event event) {
		if (mouseListeners != null) mouseListeners.fireMouseLeave(this);
	}
	
	@Override
	protected void onMouseOver(Event event) {
		if (mouseListeners != null) mouseListeners.fireMouseEnter(this);
	}
	
	@Override
	protected void onMouseUp(Event event) {
		if (mouseListeners != null) {
			int x = getMouseCoordinateX(getElement(), event);
			int y = getMouseCoordinateY(getElement(), event);
			mouseListeners.fireMouseUp(this, x, y);
		}
	}	
}
