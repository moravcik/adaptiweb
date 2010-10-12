package com.adaptiweb.gwt.framework.table;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

public interface CellFormatter {
	
	HorizontalAlignmentConstant ALIGN_CENTER = HasHorizontalAlignment.ALIGN_CENTER;
	HorizontalAlignmentConstant ALIGN_LEFT = HasHorizontalAlignment.ALIGN_LEFT;
	HorizontalAlignmentConstant ALIGN_RIGHT = HasHorizontalAlignment.ALIGN_RIGHT;
	HorizontalAlignmentConstant ALIGN_DEFAULT = HasHorizontalAlignment.ALIGN_DEFAULT;
	
	VerticalAlignmentConstant ALIGN_TOP = HasVerticalAlignment.ALIGN_TOP;
	VerticalAlignmentConstant ALIGN_MIDDLE = HasVerticalAlignment.ALIGN_MIDDLE;
	VerticalAlignmentConstant ALIGN_BOTTOM = HasVerticalAlignment.ALIGN_BOTTOM;

	void addStyleName(String styleName);

	int getColSpan();

	Element getElement();

	int getRowSpan();

	String getStyleName();

	String getStylePrimaryName();

	boolean isVisible();

	void removeStyleName(String styleName);

	void setAlignment(HorizontalAlignmentConstant horizontal, VerticalAlignmentConstant vertical);

	void setColSpan(int colSpan);

	void setHeight(String height);

	void setHorizontalAlignment(HorizontalAlignmentConstant align);

	void setRowSpan(int rowSpan);

	void setStyleName(String styleName);

	void setStylePrimaryName(String styleName);

	void setVerticalAlignment(VerticalAlignmentConstant align);

	void setVisible(boolean visible);

	void setWidth(String width);

	void setWordWrap(boolean wrap);
	
	String getText();
	
	Widget getWidget();
	
	void setText(String cellText);
	
	void setWidget(Widget cellWidget);

	void setClickHandler(ClickHandler clickHandler);
}