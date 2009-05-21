package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.Type;
import java.util.LinkedList;

class CharacterTypeAnalysis implements TypeAnalysis {

	public Object parse(String data) {
		if(data == null) return null;
		if(data.length() == 0) return null;
		if(data.length() == 1) return data.charAt(0);
		if(data.trim().length() > 0) return data.trim().charAt(0);
		return data.charAt(0);
	}

	public Type getType() {
		return Character.class;
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
