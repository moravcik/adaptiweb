package com.adaptiweb.gwt.framework.table;

public class DummyTableRecord implements DataTableRecord {
	
	private Object record;
	
	public DummyTableRecord(Object record) {
		this.record = record;
	}

	@Override
	public Object value(int columnIndex, String key) {
		return record;
	}

}
