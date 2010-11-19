package com.adaptiweb.utils.commons.param;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ParameterMap extends LinkedHashMap<String, String> {
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : entrySet())
			result.append('&').append(entry.getKey()).append('=').append(entry.getValue());
		return result.length() > 0 ? result.substring(1) : "";
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void bindParameters(Object target, Parameter<?>... parameters) {
		for (Parameter param : parameters) {
			param.bindValue(target, containsKey(param.getParameterName()) 
					? get(param.getParameterName()) : null, this);
		}
	}

}
