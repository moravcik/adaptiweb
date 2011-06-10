package com.adaptiweb.gwt.common.api;


public interface HasTable {

	interface Header {
		int getIndex();
		String getLabel();
		String getTitle();
		String getId();
		String getStyleName();
	}

	String getCellValue(int rowIndex, Header columnId);
	
}
