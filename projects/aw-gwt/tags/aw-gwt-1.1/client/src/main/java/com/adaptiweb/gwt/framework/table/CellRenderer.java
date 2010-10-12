package com.adaptiweb.gwt.framework.table;

import com.google.gwt.user.client.ui.Widget;

public interface CellRenderer<T> {
	
	public static final CellRenderer<Object> DEFAULT_CELL_RENDERER = new CellRenderer<Object>() {
		@Override
		public boolean isRowPositionSensitive() {
			return false;
		}
		@Override
	    public void renderCell(CellFormatter cell, Object value, Position position) {
	    	if(value instanceof Widget) cell.setWidget((Widget) value);
	    	else cell.setText(String.valueOf(value));
	    }
	};

	public static final CellRenderer<Object> ROW_NUMBER = new CellRenderer<Object>() {
		@Override
		public boolean isRowPositionSensitive() {
			return true;
		}
		@Override
		public void renderCell(CellFormatter cell, Object value, Position position) {
			cell.setText(position.getRow() + ".");
		}

	};

	boolean isRowPositionSensitive();
	
	void renderCell(CellFormatter cell, T value, Position position);
}
