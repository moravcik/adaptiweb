package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

class EnumTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> type;
	
	EnumTypeAnalysis(final Class<?> type) {
		this.type = type;
	}
	
	public Object composite(LinkedList<Object> list) {
		throw new UnsupportedOperationException();
	}

	public Object createInstance() {
		return null;
	}

	public Type getComponent() {
		return null;
	}

	public TypeNature getNature() {
		return TypeNature.ATOMIC;
	}

	public Type getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public Object parse(String data) {
		Enum<?> result = Enum.valueOf(type.asSubclass(Enum.class), data);
		return result;
	}

}
