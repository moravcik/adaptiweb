package com.adaptiweb.gwt.framework;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

class Glass extends PopupPanel implements ResizeHandler, ScrollHandler {

	private int showCounter = 0;

	public Glass() {
		add(new Label(""));
		resetSize();

		Window.addResizeHandler(this);
		Window.addWindowScrollHandler(this);
		setPopupPosition(0, 0);
		setStyleName("glass");
	}

	private void resetSize() {
		setPixelSize(
				Window.getScrollLeft() + Window.getClientWidth(),
				Window.getScrollTop() + Window.getClientHeight());
	}

	@Override
	public void onResize(ResizeEvent event) { resetSize(); }

	@Override
	public void onWindowScroll(ScrollEvent event) { resetSize(); }

	@Override
	public void show() {
		if (showCounter++ == 0) super.show();
	}

	@Override
	public void hide() {
		if (--showCounter == 0) super.hide();
	}
}
