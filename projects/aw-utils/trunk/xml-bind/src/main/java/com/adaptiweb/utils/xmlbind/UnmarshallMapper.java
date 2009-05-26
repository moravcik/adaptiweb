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
import com.adaptiweb.utils.xmlbind.annotation.BindAttribute;
import com.adaptiweb.utils.xmlbind.annotation.BindElement;

public class UnmarshallMapper {
	
	private static final Map<TypeAnalysis, UnmarshallMapper> cache = 
		Collections.synchronizedMap(new HashMap<TypeAnalysis, UnmarshallMapper>()); 
	
	public static final UnmarshallMapper getInstance(TypeAnalysis analysis) {
		if(!cache.containsKey(analysis)) {
			synchronized (cache) {
				if(!cache.containsKey(analysis)) {
					UnmarshallMapper newInstance = new UnmarshallMapper(analysis);
					cache.put(analysis, newInstance);
					newInstance.inicialize();
				}
			}
		}
		return cache.get(analysis); //TODO
	}
	
	private static class MappingPart {
		private final Method setter;
		private final String xmlName;
		private final UnmarshallMapper mapper;
		private final boolean isPrimitiveType;
		
		public MappingPart(final Method setter, final UnmarshallMapper mapper) {
			this.setter = setter;
			this.mapper = mapper;
			this.isPrimitiveType = setter.getParameterTypes()[0].isPrimitive();
			
			String result = "";
			
			if(setter.isAnnotationPresent(BindAttribute.class))
				result = setter.getAnnotation(BindAttribute.class).name();
			
			if(setter.isAnnotationPresent(BindElement.class))
				result = setter.getAnnotation(BindElement.class).tagName();
			
			xmlName = result.length() == 0 ? BindUtils.toXmlName(setter.getName().substring(3)) : result;
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

		public String getXmlName() {
			return xmlName;
		}
	}
	
	private final Map<String,MappingPart> parts = new HashMap<String,MappingPart>(); 
	private final TypeAnalysis analysis; 
	private UnmarshallMapper component = null;
	
	private UnmarshallMapper(TypeAnalysis analysis) {
		this.analysis = analysis;
	}
	
	/**
	 * Initializing is separated from constructor
	 * in order to prevent recursive cycle.
	 */
	private void inicialize() {
		if(analysis == null) return;
		
		switch(analysis.getNature()) {
		case MULTIPLE:
			component = UnmarshallMapper.getInstance(TypeAnalyzer.forGenericType(analysis.getComponent()));
			break;
		case STUCTURE:
			for(Method m : analysis.getType().getMethods()) {
				if(m.getName().startsWith("set") && m.getParameterTypes().length == 1) {
					
					TypeAnalysis analysis = TypeAnalyzer.forSetMethod(m);
					if(analysis != null) {
						if (m.isAnnotationPresent(BindElement.class)) {
							Class<?> type = m.getAnnotation(BindElement.class).targetClass();
							if(type != void.class)
								analysis = TypeAnalyzer.withCustomComponent(analysis, type);
						}
						
						MappingPart part = new MappingPart(m, UnmarshallMapper.getInstance(analysis));
						parts.put(part.getXmlName(), part);
					}
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
			Object result = getTargetIntance();
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

	private Object getTargetIntance() {
		return analysis.createInstance();
	}
}
