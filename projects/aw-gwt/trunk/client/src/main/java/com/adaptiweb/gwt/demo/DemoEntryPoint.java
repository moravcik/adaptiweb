package com.adaptiweb.gwt.demo;

import com.adaptiweb.gwt.framework.table.ArrayTableRecord;
import com.adaptiweb.gwt.framework.table.DataTable;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public class DemoEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get().add(createTable());
	}

	private Widget createTable() {
		DataTable<ArrayTableRecord> table = new DataTable<ArrayTableRecord>();
		return table;
	}

}
