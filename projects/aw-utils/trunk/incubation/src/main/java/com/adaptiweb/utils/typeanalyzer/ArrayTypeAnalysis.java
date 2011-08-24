package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.LinkedList;


class ArrayTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> type;
	private final Class<?> componentType;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (type == null ? 0 : type.hashCode());
		result = prime * result + (componentType == null ? 0 : componentType.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof ArrayTypeAnalysis == false) return false;
		ArrayTypeAnalysis other = (ArrayTypeAnalysis) obj;
		return this.type.equals(other.type)
			&& this.componentType.equals(other.componentType);
	}

	public ArrayTypeAnalysis(Class<?> type, Class<?> componentType) {
		assert type.isArray();
		assert type.getComponentType().isAssignableFrom(componentType);
		this.type = type; 
		this.componentType = componentType;
	}

	public ArrayTypeAnalysis(Class<?> type) {
		this(type, type.getComponentType());
	}

	public Object createInstance() {
		return null;
	}

	public Class<?> getType() {
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
		return componentType;
	}
}
