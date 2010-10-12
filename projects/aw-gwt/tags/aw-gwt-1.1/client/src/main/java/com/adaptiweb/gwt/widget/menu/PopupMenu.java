package com.adaptiweb.gwt.widget.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupMenu extends VerticalPanel {

	public interface Style extends CssResource {
		String popupPanel();
		String highlight();
		String icon();
		String label();
		String disabled();
	}

	private PopupPanel panel;
	Style style;
	private Timer autoHide = new Timer() {
		@Override
		public void run() {
			panel.hide();
		}
	};
	private Timer lazyPerform = new Timer() {
		@Override
		public void run() {
			lastItem.perform();
		}
	};
	private PopupMenuItem lastItem;


	public PopupMenu(Style style) {
		this.style = style;
		sinkEvents(Event.ONKEYPRESS | Event.ONCLICK);
		getElement().setAttribute("cellpadding", "1");
		addHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				switch (event.getNativeEvent().getKeyCode()) {
				case KeyCodes.KEY_ENTER:
				case ' ':					perform(); break;
				case KeyCodes.KEY_ESCAPE:	panel.hide(); break;
				case KeyCodes.KEY_DOWN:		selectNextItem(); break;
				case KeyCodes.KEY_UP:		selectPreviousItem(); break;
				}
			}
		}, KeyPressEvent.getType());
		addHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				perform();
			}
		}, ClickEvent.getType());
	}

	private void perform() {
		if (lastItem.isVisible() && lastItem.isEnabled()) {
			lastItem.setSelected(false);
			lazyPerform.schedule(1);
		}
	}

	private void selectPreviousItem() {
		PopupMenuItem prev = null;
		for (Widget item : this) {
			PopupMenuItem menu = (PopupMenuItem) item;
			if (menu.isSelected()) {
				if (prev != null) prev.select();
				return;
			}
			if (menu.isVisible() && menu.isEnabled()) prev = menu;
		}
	}

	private void selectNextItem() {
		boolean searching = true;
		for (Widget item : this) {
			PopupMenuItem menu = (PopupMenuItem) item;
			if (searching && menu.isSelected()) searching = false;
			else if (!searching && menu.isVisible() && menu.isEnabled()) {
				menu.select();
				return;
			}
		}
	}

	private void selectLastItem() {
		if (lastItem != null && (!lastItem.isVisible() || !lastItem.isEnabled())) {
			lastItem = null;
		}

		for (Widget item : this) if (item.isVisible() && ((PopupMenuItem) item).isEnabled() && lastItem == null || lastItem == item) {
			((PopupMenuItem) item).select();
			break;
		}
	}

	public void showBottom(Element element) {
		show(element.getAbsoluteLeft(), element.getAbsoluteTop() + element.getOffsetHeight());
	}

	public void showRight(Element element) {
		show(element.getAbsoluteLeft() + element.getOffsetWidth(), element.getAbsoluteTop());
	}

	public void show(int left, int top) {
		if (panel == null) {
			panel = new PopupPanel();
			applyPanelStyle(panel);
			panel.setWidget(this);
		}
		panel.setPopupPosition(left, top);
		show(null);
	}

	public MenuRegistration addMenuItem(ImageResource icon, String label) {
		PopupMenuItem item = new PopupMenuItem(this);
		item.setIcon(icon);
		item.setLabel(label);
		add(item);
		return item;
	}

	protected void applyPanelStyle(PopupPanel panel) {
		style.ensureInjected();
		panel.addStyleName(style.popupPanel());
	}

	void show(PopupMenuItem item) {
		autoHide.cancel();
		if (item != null) lastItem = item;
		if (!panel.isShowing()) {
			panel.show();
			selectLastItem();
		}
	}

	void hide() {
		autoHide.schedule(1);
	}
}
