package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class CollectionTypeAnalysis implements TypeAnalysis {
	
	private final Class<?> raw;
	private final Type component;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (raw == null ? 0 : raw.hashCode());
		result = prime * result + (component == null ? 0 : component.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof CollectionTypeAnalysis == false) return false;
		CollectionTypeAnalysis other = (CollectionTypeAnalysis) obj;
		return this.raw.equals(other.raw)
			&& this.component.equals(other.component);
	}

	public CollectionTypeAnalysis(ParameterizedType type) {
		this(type, type.getActualTypeArguments()[0]);
	}
	
	public CollectionTypeAnalysis(ParameterizedType type, Type component) {
		Class<?> raw = (Class<?>) type.getRawType();  
		if(raw.isInterface()) {
			if(raw.isAssignableFrom(List.class)) raw = ArrayList.class;
			else if(raw.isAssignableFrom(Set.class)) raw = HashSet.class;
			else if(raw.isAssignableFrom(Queue.class)) raw = LinkedList.class;
			else throw new UnsupportedOperationException();
		}
		this.raw = raw;
		this.component = component;
	}
	
	public CollectionTypeAnalysis(Class<?> raw, Type component) {
		this.raw = raw;
		this.component = component;
	}

	@SuppressWarnings("unchecked")
	public Object composite(LinkedList<Object> list) {
		try {
			Collection<Object> instance = (Collection<Object>) raw.newInstance();
			instance.addAll(list);
			return instance;
		} catch (Exception e) {
			return null;
		}
	}

	public Object createInstance() {
		try {
			return raw.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Type getComponent() {
		return component;
	}

	public TypeNature getNature() {
		return TypeNature.MULTIPLE;
	}

	public Class<?> getType() {
		return raw;
	}

	public Object parse(String data) {
		throw new UnsupportedOperationException();
	}

}
