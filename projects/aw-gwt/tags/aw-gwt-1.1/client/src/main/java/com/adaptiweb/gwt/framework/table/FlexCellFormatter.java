package com.adaptiweb.gwt.framework.table;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

class FlexCellFormatter implements CellFormatter {
	
	private final FlexTable table;
	private int row;
	private int column;
	
	FlexCellFormatter(FlexTable table) {
		this.table = table;
	}
	
	FlexCellFormatter setCurentCell(int row, int column) {
		this.row = row;
		this.column = column;
		setText(""); //create empty cell
		return this;
	}

	public void addStyleName(String styleName) {
		table.getFlexCellFormatter().addStyleName(row, column, styleName);
	}

	public int getColSpan() {
		return table.getFlexCellFormatter().getColSpan(row, column);
	}

	public Element getElement() {
		return table.getFlexCellFormatter().getElement(row, column);
	}

	public int getRowSpan() {
		return table.getFlexCellFormatter().getRowSpan(row, column);
	}

	public String getStyleName() {
		return table.getFlexCellFormatter().getStyleName(row, column);
	}

	public String getStylePrimaryName() {
		return table.getFlexCellFormatter().getStylePrimaryName(row, column);
	}

	public boolean isVisible() {
		return table.getFlexCellFormatter().isVisible(row, column);
	}

	public void removeStyleName(String styleName) {
		table.getFlexCellFormatter().removeStyleName(row, column, styleName);
	}

	public void setAlignment(HorizontalAlignmentConstant align, VerticalAlignmentConstant align2) {
		table.getFlexCellFormatter().setAlignment(row, column, align, align2);
	}

	public void setColSpan(int colSpan) {
		table.getFlexCellFormatter().setColSpan(row, column, colSpan);
	}

	public void setHeight(String height) {
		table.getFlexCellFormatter().setHeight(row, column, height);
	}

	public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
		table.getFlexCellFormatter().setHorizontalAlignment(row, column, align);
	}

	public void setRowSpan(int rowSpan) {
		table.getFlexCellFormatter().setRowSpan(row, column, rowSpan);
	}

	public void setStyleName(String styleName) {
		table.getFlexCellFormatter().setStyleName(row, column, styleName);
	}

	public void setStylePrimaryName(String styleName) {
		table.getFlexCellFormatter().setStylePrimaryName(row, column, styleName);
	}

	public void setVerticalAlignment(VerticalAlignmentConstant align) {
		table.getFlexCellFormatter().setVerticalAlignment(row, column, align);
	}

	public void setVisible(boolean visible) {
		table.getFlexCellFormatter().setVisible(row, column, visible);
	}

	public void setWidth(String width) {
		table.getFlexCellFormatter().setWidth(row, column, width);
	}

	public void setWordWrap(boolean wrap) {
		table.getFlexCellFormatter().setWordWrap(row, column, wrap);
	}

	public String getText() {
		return table.getText(row, column);
	}

	public Widget getWidget() {
		return table.getWidget(row, column);
	}

	public void setText(String cellText) {
		table.setText(row, column, cellText);
	}

	public void setWidget(Widget cellWidget) {
		table.setWidget(row, column, cellWidget);
	}

	public void setClickHandler(final ClickHandler clickHandler) {
		new FocusWidget(getElement()) {
			{
				sinkEvents(Event.ONCLICK);
				onAttach();
			}
		}.addClickHandler(clickHandler);
	}
}
