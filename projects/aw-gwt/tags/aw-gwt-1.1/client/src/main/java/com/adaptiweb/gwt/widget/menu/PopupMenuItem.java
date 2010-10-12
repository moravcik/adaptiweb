package com.adaptiweb.gwt.widget.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;

class PopupMenuItem extends Composite implements MenuRegistration {

	interface PopupMenuItemUiBinder extends UiBinder<FocusPanel, PopupMenuItem> {}
	static final PopupMenuItemUiBinder uiBinder = GWT.create(PopupMenuItemUiBinder.class);

	@UiField TableCellElement iconCell;
	@UiField TableCellElement labelCell;
	@UiField Image icon;
	PopupMenu.Style style;
	private boolean selected = false;
	private boolean enabled = true;
	private final PopupMenu menu;
	private MenuPerformHandler handler;
	private int tabIndex = 0;

	public PopupMenuItem(PopupMenu menu) {
		this.menu = menu;
		initWidget(uiBinder.createAndBindUi(this));

		iconCell.addClassName(menu.style.icon());
		labelCell.addClassName(menu.style.label());

		getPanel().addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				setSelected(true);
			}
		});
		getPanel().addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				setSelected(false);
			}
		});
		getPanel().addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (enabled) select();
			}
		});
	}

	public MenuRegistration setPerformHandler(MenuPerformHandler handler) {
		this.handler = handler;
		return this;
	}

	void setSelected(boolean on) {
		if (selected = on) {
			addStyleName(menu.style.highlight());
			menu.show(this);
		}
		else {
			removeStyleName(menu.style.highlight());
			menu.hide();
		}
	}

	protected FocusPanel getPanel() {
		return (FocusPanel) getWidget();
	}

	public void select() {
		getPanel().setFocus(true);
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
	}

	@Override
	public void setLabel(String text) {
		labelCell.setInnerText(text);
	}

	@Override
	public String getLabel() {
		return labelCell.getInnerText();
	}

	@Override
	public void setIcon(ImageResource resource) {
		icon.setResource(resource);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean on) {
		if (enabled = on) {
			removeStyleName(menu.style.disabled());
			getPanel().setTabIndex(tabIndex);
		}
		else {
			addStyleName(menu.style.disabled());
			getPanel().setTabIndex(-1);
		}
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
		getPanel().setTabIndex(tabIndex);
	}

	public int getTabIndex() {
		return getPanel().getTabIndex();
	}

	void perform() {
		if (handler != null) handler.onMenuPerform(this);
	}

	@Override
	public void setVisible(boolean visible) {
		setVisible(getElement(), visible);

		Element tdElement = getElement().getParentElement();
		if (tdElement == null || !"td".equalsIgnoreCase(tdElement.getTagName())) return;

		Element trElement = tdElement.getParentElement();
		if (trElement == null || !"tr".equalsIgnoreCase(trElement.getTagName())) return;

		setVisible(trElement, visible);
	}
}
