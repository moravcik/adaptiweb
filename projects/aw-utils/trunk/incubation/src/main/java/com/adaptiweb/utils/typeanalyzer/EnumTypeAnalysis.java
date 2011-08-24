package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

class EnumTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> type;
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof EnumTypeAnalysis == false) return false;
		EnumTypeAnalysis other = (EnumTypeAnalysis) obj;
		return this.type.equals(other.type);
	}

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

	public Class<?> getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public Object parse(String data) {
		Enum<?> result = Enum.valueOf(type.asSubclass(Enum.class), data);
		return result;
	}

}
