package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.LinkedList;


class AtomicTypeAnalysis implements TypeAnalysis {

	private final Constructor<?> constructor;
	
	@Override
	public int hashCode() {
		return constructor.getDeclaringClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof AtomicTypeAnalysis == false) return false;
		AtomicTypeAnalysis other = (AtomicTypeAnalysis) obj;
		return constructor.getDeclaringClass().equals(other.constructor.getDeclaringClass());
	}
	
	AtomicTypeAnalysis(Constructor<?> constructor) {
		assert constructor.getParameterTypes().length == 1 : "Constructor must have 1 parameter (String)";
		assert String.class.equals(constructor.getParameterTypes()[0]): "Constructor must have 1 parameter (String)";
		this.constructor = constructor;
	}

	public Object parse(String data) {
		if(data == null) return null;
		try {
			return constructor.newInstance(data);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if(e.getCause() instanceof RuntimeException)
				try {
					throw (RuntimeException) e.getCause();
				} catch(NumberFormatException en) {
					if(data.contains(",")) return parse(data.replace(',', '.'));
					throw new NumberFormatException("Nespr�vny form�t ��sla '" + data + "'");
				}
			else 
				throw new RuntimeException(e);
		}
		return null;
	}

	public Class<?> getType() {
		return constructor.getDeclaringClass();
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
