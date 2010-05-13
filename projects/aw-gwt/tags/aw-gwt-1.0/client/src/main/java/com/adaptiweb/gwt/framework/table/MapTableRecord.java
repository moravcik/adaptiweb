package com.adaptiweb.gwt.framework.table;

import java.util.Map;

public class MapTableRecord implements DataTableRecord {

	private Map<String, Object> map;

	public MapTableRecord(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public Object value(int columnIndex, String key) {
		return map.get(key);
	}

}
