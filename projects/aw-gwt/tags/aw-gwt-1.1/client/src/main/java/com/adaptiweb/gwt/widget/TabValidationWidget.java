package com.adaptiweb.gwt.widget;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.TabBar;

public class TabValidationWidget extends FocusWidget {
	
	private TabBar tabBar;
	private int tabIndex;
	
	public TabValidationWidget(TabBar tabBar, int tabIndex) {
		this.tabBar = tabBar;
		this.tabIndex = tabIndex;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		tabBar.setTabEnabled(tabIndex, enabled);
	}

}
