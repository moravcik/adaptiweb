package com.adaptiweb.utils.xmlbind;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.adaptiweb.utils.typeanalyzer.TypeAnalysis;
import com.adaptiweb.utils.typeanalyzer.TypeAnalyzer;

public class UnmarshallMapper {
	
	private static final Map<Type, UnmarshallMapper> cache = 
		Collections.synchronizedMap(new HashMap<Type, UnmarshallMapper>()); 
	
	public static final UnmarshallMapper getInstance(Type type) {
		if(!cache.containsKey(type)) {
			synchronized (cache) {
				if(!cache.containsKey(type)) {
					UnmarshallMapper newInstance = new UnmarshallMapper(type);
					cache.put(type, newInstance);
					newInstance.inicialize();
				}
			}
		}
		return cache.get(type); //TODO
	}
	
	private static class MappingPart {
		private final Method setter;
		private final UnmarshallMapper mapper;
		private final boolean isPrimitiveType;
		
		public MappingPart(final Method setter, final UnmarshallMapper mapper) {
			this.setter = setter;
			this.mapper = mapper;
			this.isPrimitiveType = setter.getParameterTypes()[0].isPrimitive();
		}

		public void apply(Object object, Object value) {
			if(value == null && isPrimitiveType)
				throw new IllegalArgumentException("Unable set null value with: " + setter);
			try {
				setter.invoke(object, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				if(e.getCause() instanceof RuntimeException)
					throw (RuntimeException) e.getCause();
				e.printStackTrace();
			} 
		}
	}
	
	private final Map<String,MappingPart> parts = new HashMap<String,MappingPart>(); 
	private final TypeAnalysis analysis; 
	private final Class<?> type;
	private UnmarshallMapper component = null;
	
	private UnmarshallMapper(Type type) {
		this.type = (Class<?>) (type instanceof ParameterizedType ? 
				((ParameterizedType) type).getRawType() : type);
		this.analysis = TypeAnalyzer.forGenericType(type);
	}
	
	/**
	 * Initializing is separated from constructor
	 * in order to prevent recursive cycle.
	 */
	private void inicialize() {
		if(analysis == null) return;
		
		switch(analysis.getNature()) {
		case MULTIPLE:
			component = UnmarshallMapper.getInstance(analysis.getComponent());
			break;
		case STUCTURE:
			for(Method m : type.getMethods()) {
				if(m.getName().startsWith("set") && m.getParameterTypes().length == 1) {
					TypeAnalysis analysis = TypeAnalyzer.forSetMethod(m);
					if(analysis != null)
						parts.put(
								BindUtils.toXmlName(m.getName().substring(3)), 
								new MappingPart(m, UnmarshallMapper.getInstance(analysis.getType())));
				}
			}
			break;
		}
	}

	public Object map(XmlSource source) {
		if(analysis == null) return null;
		
		switch(analysis.getNature()) {
		case MULTIPLE:
			LinkedList<Object> list = new LinkedList<Object>();
			do {
				list.add(component.map(source));
				source = source.getNext();
			} while(source != null);
			return analysis.composite(list);
		case STUCTURE:
			Object result = analysis.createInstance();
			if(result == null) return null;
			for(XmlSource child : source.getChildren()) {
				MappingPart part = parts.get(child.getName());
				if(part != null)
					part.apply(result, part.mapper.map(child));
			}
			//unmarshal attributes
			if(source instanceof Element)
				for(Entry<String,String> child : ((Element) source).attrs.entrySet()) {
					MappingPart part = parts.get(child.getKey());
					if(part != null) part.apply(result, part.mapper.analysis.parse(child.getValue()));
				}
			return result;
		case ATOMIC:
			if(!source.isComposite())
				return analysis.parse(source.getValue());
		default:
			return null;
		}
	}
}
