package com.adaptiweb.gwt.framework;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

class Glass extends PopupPanel implements ResizeHandler {
	
	private int showCounter = 0;

	public Glass() {
		add(new Label(""));
		setPixelSize(
				Window.getScrollLeft() + Window.getClientWidth(),
                Window.getScrollTop() + Window.getClientHeight());
		
		Window.addResizeHandler(this);
        setPopupPosition(0, 0);
        setStyleName("glass");
	}

	@Override
	public void onResize(ResizeEvent event) {
		setPixelSize(event.getWidth(), event.getHeight());
	}
	
	@Override
	public void show() {
		if (showCounter++ == 0) super.show();
	}
	
	@Override
	public void hide() {
		if (--showCounter == 0) super.hide();
	}
}
