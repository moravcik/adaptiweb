package com.adaptiweb.utils.ci;

import java.util.HashMap;
import java.util.Map;

public class MapVariableSource implements VariableSource {
	
	private final Map<String, String> map;

	public MapVariableSource(Map<String,String> map) {
		this.map = map;
	}
	
	public MapVariableSource() {
		this(new HashMap<String, String>());
	}

	@Override
	public String getRawValue(String variableName) throws NullPointerException {
		return map.get(variableName);
	}

}
