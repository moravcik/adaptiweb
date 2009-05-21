package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.LinkedList;


class ArrayTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> type;

	public ArrayTypeAnalysis(final Class<?> type) {
		if(!type.isArray()) throw new IllegalArgumentException();
		this.type = type;
	}

	public Object createInstance() {
		return null;
	}

	public Type getType() {
		return type;
	}

	public TypeNature getNature() {
		return TypeNature.MULTIPLE;
	}

	public Object parse(String data) {
		return null;
	}

	public Object composite(LinkedList<Object> list) {
		Object result = Array.newInstance(type.getComponentType(), list.size());
		System.arraycopy(list.toArray(), 0, result, 0, list.size());
		return result;
	}

	public Type getComponent() {
		return type.getComponentType();
	}

	
}
