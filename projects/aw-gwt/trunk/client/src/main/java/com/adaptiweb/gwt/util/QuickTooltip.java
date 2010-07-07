package com.adaptiweb.gwt.util;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;


public final class QuickTooltip {

	private static native void attach() /*-{
		$doc.onmousemove = function(event) {
    		@com.adaptiweb.gwt.util.QuickTooltip::performEvent(Lcom/google/gwt/user/client/Event;)(event || $wnd.event);
    	}
	}-*/;

	private static native void detach() /*-{
		$doc.onmousemove = null;
	}-*/;

	private static PopupPanel popup;
	private static HTML htmlContent;
	private static Element targetElement;
	private static String title;
	private static HandlerRegistration registration;

	private QuickTooltip() {}

	public static void activate() {
		if (isActive()) return;
		attach();
		registration = Window.addWindowClosingHandler(new ClosingHandler() {
			@Override
			public void onWindowClosing(ClosingEvent event) {
				deactivate();
			}
		});
	}

	public static boolean isActive() {
		return registration != null;
	}

	public static void deactivate() {
		if (!isActive()) return;
		detach();
		registration.removeHandler();
		registration = null;
	}

	public static void performEvent(Event event) {
		Element targetElement = getTargetElementWithTitle(event);

		if (popup != null && popup.isShowing()) {
			if (QuickTooltip.targetElement == targetElement) {
				updatePopupPosition(event);
				return;
			}
			else popup.hide();
		}

		if (targetElement == null) return;
		String title = targetElement.getAttribute("title");

		ensurePopupIsCreated();
		htmlContent.setHTML(title);
		updatePopupPosition(event);
		targetElement.removeAttribute("title");
		QuickTooltip.title = title;
		QuickTooltip.targetElement = targetElement;
		popup.show();

	}

	private static void updatePopupPosition(Event event) {
		popup.setPopupPosition(event.getClientX() + Window.getScrollLeft(), event.getClientY() + Window.getScrollTop() + 20);
	}

	private static void ensurePopupIsCreated() {
		if (popup != null) return;
		popup = new PopupPanel(true);
		popup.addStyleName("aw-QuickTooltip");
		popup.setWidget(htmlContent = new HTML());
		popup.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				targetElement.setAttribute("title", title);
				targetElement = null;
			}
		});
	}

	private static Element getTargetElementWithTitle(Event event) {
		if (event == null) return null;
		EventTarget target = event.getEventTarget();
		if (!Element.is(target)) return null;
		Element targetElement = Element.as(target);
		BodyElement body = Document.get().getBody();

		while (targetElement != null) {
			if (targetElement.hasAttribute("title")) return targetElement;
			if (QuickTooltip.targetElement == targetElement) return targetElement;
			if (targetElement == body) return null;
			targetElement = targetElement.getParentElement();
		}
		return null;
	}
}
