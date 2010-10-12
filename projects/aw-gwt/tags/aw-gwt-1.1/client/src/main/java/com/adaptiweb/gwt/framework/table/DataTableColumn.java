package com.adaptiweb.gwt.framework.table;

public interface DataTableColumn {

	void renderHeader(CellFormatter cell, int columnPosition);
	
	void renderCell(CellFormatter cell, Object value, Position position);

	void disposeCell();
	
	boolean isRowPositionSensitive();

	DataTableColumn setSize(int pertentage);

	DataTableColumn setCellRenderer(CellRenderer<?> rowNumber);

	DataTableColumn setKey(String string);

	DataTableColumn setSortExpression(String string);
}
