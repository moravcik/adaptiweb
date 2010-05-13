package com.adaptiweb.utils.typeanalyzer;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class provide static methods for 
 */
public class TypeAnalyzer {
	
	public static TypeAnalysis forSetMethod(Method m) {
		Type type = m.getGenericParameterTypes()[0];
		if(type instanceof TypeVariable<?> || type instanceof WildcardType) {
			try {
				type = m.getDeclaringClass().getMethod("get" + m.getName().substring(3)).getReturnType();
			} catch (SecurityException e) {
				return null;
			} catch (NoSuchMethodException e) {
				return null;
			}
			if(type instanceof TypeVariable<?> || type instanceof WildcardType)
				return null;
		}
		

		return forGenericType(type);
	}
	
	public static TypeAnalysis forGenericType(Type type) {
		if(type instanceof GenericArrayType)
			return examineGenericArrayType((GenericArrayType) type);
		if(type instanceof ParameterizedType)
			return examineParameterizedType((ParameterizedType) type);
		if(type instanceof Class<?>)
			return examineType((Class<?>) type);
		return null;
	}
	
	public static TypeAnalysis examineType(Class<?> type) {
		if(type.isEnum()) {
			return new EnumTypeAnalysis(type);
		}
		if(type.isArray()) {
			return new ArrayTypeAnalysis(type);
		}
		if(type.isPrimitive()) {
			if(type == boolean.class) type = Boolean.class;
			else if(type == byte.class) type = Byte.class;
			else if(type == char.class) type = Character.class;
			else if(type == short.class) type = Short.class;
			else if(type == int.class) type = Integer.class;
			else if(type == long.class) type = Long.class;
			else if(type == float.class) type = Float.class;
			else if(type == double.class) type = Double.class;
			
			if(type.isPrimitive()) return null;
		}
		if(type == Boolean.class) return BooleanTypeAnalysis.INSTANCE;
		if(type == Character.class) return CharacterTypeAnalysis.INSTANCE;
		try {
			return new AtomicTypeAnalysis(type.getConstructor(String.class));
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
			
		return new DefaultTypeAnalysis(type);
	}
	
	public static TypeAnalysis forObject(final Object obj) {
		final TypeAnalysis analysis = new DefaultTypeAnalysis(obj.getClass());
		
		return new TypeAnalysis() {
			private boolean used;
			
			@Override
			public Object parse(String data) {
				return analysis.parse(data);
			}
			
			@Override
			public Class<?> getType() {
				return analysis.getType();
			}
			
			@Override
			public TypeNature getNature() {
				return analysis.getNature();
			}
			
			@Override
			public Type getComponent() {
				return analysis.getComponent();
			}
			
			@Override
			public Object createInstance() {
				assert !used && (used = true);
				return obj;
			}
			
			@Override
			public Object composite(LinkedList<Object> list) {
				return analysis.composite(list);
			}
		};
	}
	
	private static TypeAnalysis examineParameterizedType(ParameterizedType type) {
		Type[] parameters = type.getActualTypeArguments();
		if(parameters.length > 1) return null;
		Class<?> raw = (Class<?>) type.getRawType();
		if(Collection.class.isAssignableFrom(raw)) {
			return new CollectionTypeAnalysis(type);
		}
		return null;
	}

	private static TypeAnalysis examineGenericArrayType(GenericArrayType type) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	public static TypeAnalysis withCustomComponent(TypeAnalysis analysis, Class<?> type) {
		if (analysis instanceof ArrayTypeAnalysis)
			return new ArrayTypeAnalysis(analysis.getType(), type);
		if (analysis instanceof CollectionTypeAnalysis)
			return new CollectionTypeAnalysis(analysis.getType(), type);
		if (analysis instanceof DefaultTypeAnalysis)
			return examineType(type);
		throw new UnsupportedOperationException();
	}
}
