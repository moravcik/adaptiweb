package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

class BooleanTypeAnalysis implements TypeAnalysis {
	
	public Object parse(String data) {
		return "1".equals(data)
			|| "true".equalsIgnoreCase(data)
			|| "yes".equalsIgnoreCase(data)
			|| "Y".equalsIgnoreCase(data)
			|| "A".equalsIgnoreCase(data);
	}

	public Type getType() {
		return Boolean.class;
	}

	public TypeNature getNature() {
		return TypeNature.ATOMIC;
	}

	public Object createInstance() {
		return null;
	}

	public Object composite(LinkedList<Object> list) {
		throw new UnsupportedOperationException();
	}

	public Type getComponent() {
		return null;
	}

}
