package com.adaptiweb.gwt.framework.table;

import org.junit.Ignore;

import com.adaptiweb.gwt.framework.table.ArrayTableRecord;
import com.adaptiweb.gwt.framework.table.CellRenderer;
import com.adaptiweb.gwt.framework.table.CellRendererFactory;


@Ignore
public class DataTableTest {

	public void testDefineTable() {
		DataTable<ArrayTableRecord> table = new DataTable<ArrayTableRecord>();
		table.addColumn("#")
			.setSize(10)
			.setCellRenderer(CellRenderer.ROW_NUMBER);
		table.addColumn("Date")
			.setKey("date")
			.setSize(20)
			.setCellRenderer(CellRendererFactory.date("d.M.yyyy"));
		table.addColumn("Name")
			.setKey("name")
			.setSortExpression("t.name");
		
	}
}
