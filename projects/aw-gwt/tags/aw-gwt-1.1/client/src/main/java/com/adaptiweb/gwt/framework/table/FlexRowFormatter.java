package com.adaptiweb.gwt.framework.table;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

public class FlexRowFormatter implements RowFormatter {
	
	private final FlexTable table;
	private int row;

	public FlexRowFormatter(FlexTable table) {
		this.table = table;
	}
	
	RowFormatter setCurrentRow(int row) {
		this.row = row;
		return this;
	}

	public void addStyleName(String styleName) {
		table.getRowFormatter().addStyleName(row, styleName);
	}

	public Element getElement() {
		return table.getRowFormatter().getElement(row);
	}

	public String getStyleName() {
		return table.getRowFormatter().getStyleName(row);
	}

	public String getStylePrimaryName() {
		return table.getRowFormatter().getStylePrimaryName(row);
	}

	public boolean isVisible() {
		return table.getRowFormatter().isVisible(row);
	}

	public void removeStyleName(String styleName) {
		table.getRowFormatter().removeStyleName(row, styleName);
	}

	public void setStyleName(String styleName) {
		table.getRowFormatter().setStyleName(row, styleName);
	}

	public void setStylePrimaryName(String styleName) {
		table.getRowFormatter().setStylePrimaryName(row, styleName);
	}

	public void setVerticalAlign(VerticalAlignmentConstant align) {
		table.getRowFormatter().setVerticalAlign(row, align);
	}

	public void setVisible(boolean visible) {
		table.getRowFormatter().setVisible(row, visible);
	}
	
}
