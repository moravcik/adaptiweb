package com.adaptiweb.gwt.framework.table;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public final class CellRendererFactory {
	
	private CellRendererFactory() {}

	public static CellRenderer<Date> date(String pattern) {
		final DateTimeFormat format = DateTimeFormat.getFormat(pattern);

		return new CellRenderer<Date>() {
			@Override
			public boolean isRowPositionSensitive() {
				return false;
			}

			@Override
			public void renderCell(CellFormatter cell, Date value, Position position) {
				cell.setText(format.format(value));
			}
		};
	}

}
