package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

class DefaultTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> type;

	public DefaultTypeAnalysis(final Class<?> type) {
		this.type = type;
	}

	public Object createInstance() {
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Type getType() {
		return type;
	}

	public TypeNature getNature() {
		return TypeNature.STUCTURE;
	}

	public Object parse(String data) {
		return null;
	}

	public Object composite(LinkedList<Object> list) {
		throw new UnsupportedOperationException();
	}

	public Type getComponent() {
		return null;
	}

}
