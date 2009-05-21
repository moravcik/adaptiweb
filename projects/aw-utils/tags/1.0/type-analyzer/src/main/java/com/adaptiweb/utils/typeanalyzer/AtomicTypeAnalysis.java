package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;


class AtomicTypeAnalysis implements TypeAnalysis {

	private static final Class<?>[] parameterMask = {String.class};
	private final Constructor<?> constructor;
	
	AtomicTypeAnalysis(Constructor<?> constructor) {
		if(!Arrays.equals(parameterMask, constructor.getParameterTypes()))
			throw new IllegalArgumentException("Constructor must have 1 parameter (String)");
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

	public Type getType() {
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
