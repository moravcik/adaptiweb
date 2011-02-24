package com.adaptiweb.utils.xstream;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamUtils {

	public static class XStreamIgnoringUnknownFields extends XStream {
		@Override
		protected MapperWrapper wrapMapper(MapperWrapper next) {
			return new MapperWrapper(next) {
				@SuppressWarnings("rawtypes")
				@Override
				public boolean shouldSerializeMember(Class definedIn, String fieldName) {
					if (definedIn == Object.class) return false;
					return super.shouldSerializeMember(definedIn, fieldName);
				}
			};
		}
	}
	
	private static XStream xstream;
	private static Set<Class<?>> processedClasses;
	
	public static synchronized XStream getXStream(Class<?> annotatedClass, Class<?>... otherClasses) {
		if (xstream == null) {
			xstream = new XStreamIgnoringUnknownFields();
			xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
			processedClasses = new HashSet<Class<?>>();
		}
		processClass(annotatedClass);
		if (otherClasses != null) 
			for (Class<?> otherClass : otherClasses) processClass(otherClass);
		return xstream;
	}
	
	private static void processClass(Class<?> annotatedClass) {
		if (!processedClasses.contains(annotatedClass)) {
			xstream.processAnnotations(annotatedClass);
			processedClasses.add(annotatedClass);
		}
	}
}
