package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

class DefaultTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> type;

	public DefaultTypeAnalysis(Class<?> type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof DefaultTypeAnalysis == false) return false;
		DefaultTypeAnalysis other = (DefaultTypeAnalysis) obj;
		return this.type.equals(other.type);
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

	public Class<?> getType() {
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
