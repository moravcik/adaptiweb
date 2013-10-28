package com.adaptiweb.utils.xstream;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.DefaultConverterLookup;
import com.thoughtworks.xstream.core.util.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamUtils {

	public static class XStreamIgnoringUnknownFields extends XStream {
		private static final NameCoder nameCoder = new NoNameCoder();
		
		public XStreamIgnoringUnknownFields() {
			// copied from default XStream constructor and added nameCoder
            super(null, new DomDriver("UTF-8", nameCoder), new ClassLoaderReference(new CompositeClassLoader()), (Mapper) null, new DefaultConverterLookup(), null); 
		}
		
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
