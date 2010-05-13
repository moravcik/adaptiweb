package com.adaptiweb.gwt.framework.table;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

public interface RowFormatter {

	public abstract void addStyleName(String styleName);

	public abstract Element getElement();

	public abstract String getStyleName();

	public abstract String getStylePrimaryName();

	public abstract boolean isVisible();

	public abstract void removeStyleName(String styleName);

	public abstract void setStyleName(String styleName);

	public abstract void setStylePrimaryName(String styleName);

	public abstract void setVerticalAlign(VerticalAlignmentConstant align);

	public abstract void setVisible(boolean visible);

}