package com.adaptiweb.gwt.framework.table;

import java.util.List;

public class ArrayTableRecord implements DataTableRecord {
	
	private Object[] values;

	public ArrayTableRecord(List<?> list) {
		this(list.toArray());
	}

	public ArrayTableRecord(Object...values) {
		assert values != null;
		this.values = values;
	}

	@Override
	public Object value(int columnIndex, String key) {
		return values[columnIndex];
	}

}
