package com.adaptiweb.utils.xmlbind;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.adaptiweb.utils.xmlbind.annotation.BindAttribute;
import com.adaptiweb.utils.xmlbind.annotation.BindClass;
import com.adaptiweb.utils.xmlbind.annotation.BindElement;
import com.adaptiweb.utils.xmlbind.annotation.DefaultBindElement;
import com.adaptiweb.utils.xmlbind.annotation.NoBind;

public class Marshaller {

	public void marshal(Object src, OutputStream outputStream) throws ParserException {
		marshal(src, new OutputStremConstructor(outputStream));
	}
	
	private void marshal(Object src, MarshallerOutput output) throws ParserException {
		try {
			Class<? extends Object> reflect = src.getClass();
			output.startElement(extractTagName(reflect));
			examineSource(src, reflect, output);
			output.stopElement();
			output.flush();
		} catch (IOException e) {
			throw new ParserException(e);
		} catch (IllegalArgumentException e) {
			throw new ParserException(e);
		} catch (IllegalAccessException e) {
			throw new ParserException(e);
		} catch (InvocationTargetException e) {
			throw new ParserException(e);
		} catch (SecurityException e) {
			throw new ParserException(e);
		} catch (NoSuchMethodException e) {
			throw new ParserException(e);
		}
	}

	private static String extractTagName(Class<?> reflect) {
		return BindUtils.toXmlName(reflect.isArray() ? 
				(reflect = reflect.getComponentType()).isArray() ? "arrays" : reflect.getSimpleName() + "s" : 
				reflect.getSimpleName());
	}
	
	private void examineSource(Object obj, Class<?> reflect, MarshallerOutput output) throws IOException, ParserException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		if(obj == null) return;
		
		if(obj instanceof Map) {
			for(Entry<?,?> entry : ((Map<?,?>) obj).entrySet()) {
				output.startElement(BindUtils.toXmlName(entry.getKey().toString()));
				if(entry.getValue() != null)
					examineSource(entry.getValue(), entry.getValue().getClass(), output);
				output.stopElement();
			}
			return;
		}
		if(isBasicType(obj)) {
			output.addText(toString(obj, reflect, output.getCurentTagName()));
			return;
		}
		if(reflect.isArray()) {
			int length = Array.getLength(obj);
			for(int i = 0; i < length; i++) {
				Object item = Array.get(obj, i);
				if(item == null) continue;
				Class<? extends Object> type = item.getClass();
				output.startElement(extractTagName(type));
				examineSource(item, type, output);
				output.stopElement();
			}
			return;
		}

		//---roztriedenie metod
		List<Method> attributes = new LinkedList<Method>();
		List<Method> subelemetns = new LinkedList<Method>();
		
		if(reflect.isAnnotationPresent(BindClass.class)) {
			BindClass annotation = reflect.getAnnotation(BindClass.class);
			(annotation.asElement() ? subelemetns : attributes)
				.add(reflect.getMethod("getClass"));
		}
		
		for(Method method : reflect.getMethods()) {
			if(!method.getName().startsWith("get") && !method.getName().startsWith("is")
				|| method.getParameterTypes().length != 0
				|| method.isAnnotationPresent(NoBind.class)
				|| Object.class.equals(method.getDeclaringClass())) continue;
			
			(method.isAnnotationPresent(BindAttribute.class) ?
				attributes : subelemetns).add(method); 
		}

		appendAttributes(obj, attributes, output);
		appendElements(obj, subelemetns, output);

		if(Iterable.class.isAssignableFrom(reflect)) {
			for(Object item : (Iterable<?>) obj) {
				if(item == null) continue;
				Class<? extends Object> type = item.getClass();
				output.startElement(extractTagName(type));
				examineSource(item, type, output);
				output.stopElement();
			}
		}
	}

	private boolean isBasicType(Object obj) {
		return obj instanceof Enum || obj instanceof BigDecimal
				|| obj instanceof String || obj instanceof Integer
				|| obj instanceof Long || obj instanceof Character
				|| obj instanceof Boolean || obj instanceof Date
				|| obj instanceof Calendar;
	}
	
	public static String toString(Object obj, Class<?> type, String currentTag) {
		if(obj instanceof Enum) {
			if(currentTag.endsWith("_text"))
				return ((Enum<?>) obj).toString();
			else if(currentTag.endsWith("_num"))
				return Integer.toString(((Enum<?>) obj).ordinal());
			else
				return ((Enum<?>) obj).name();
		}
		if(obj instanceof BigDecimal) {
			return ((BigDecimal) obj).toPlainString();
		}
		if(obj instanceof String || obj instanceof Integer || obj instanceof Long || obj instanceof Character || obj instanceof Boolean) {
			return obj.toString();
		}
		if(obj instanceof Date || obj instanceof Calendar) {
			Date dateTime = obj instanceof Calendar?
					((Calendar) obj).getTime() : (Date) obj;
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(dateTime);
//			if(currentTag.endsWith("_time"))
//				return Dates.formatTime(dateTime);
//			else if(currentTag.endsWith("_date"))
//				return Dates.formatDate(dateTime);
//			else if(currentTag.endsWith("_sdate"))
//				return Dates.formatDate(dateTime, Dates.SYSTEM_DATE_FORMAT);
//			else
//				return Dates.formatDateTime(dateTime);
		}
		throw new IllegalArgumentException("Unknown type " + type.getName());
	}
	
	private void appendElements(Object obj, List<Method> subelemetns, MarshallerOutput output) throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, ParserException, NoSuchMethodException, ArrayIndexOutOfBoundsException, InvocationTargetException {
		for(Method method : subelemetns) {
			BindElement annotation = method.getAnnotation(BindElement.class);
			if(annotation == null) annotation = DefaultBindElement.defaultInstance;

			Class<?> type = method.getReturnType();
			Object result = null;
			try {
				result = method.invoke(obj);
			} catch (InvocationTargetException ignore) {}

			String tag = annotation.tagName().length() != 0 ? annotation.tagName() : 
				BindUtils.toXmlName(method.getName().substring(method.getName().startsWith("is") ? 2 : 3));
						
			if(Iterable.class.isAssignableFrom(type) || type.isArray()) {
				if(result == null) continue;
				
				if(annotation.itemTag().length() != 0) {
					output.startElement(tag);
					tag = annotation.itemTag();
				}
				
				if(type.isArray())
					for(int i = 0, length = Array.getLength(result); i < length; i++)
						determineElement(tag, Array.get(result, i), annotation, output);
				else
					for(Object o : (Iterable<?>) result)
						determineElement(tag, o, annotation, output);
				
				if(annotation.itemTag().length() != 0)
					output.stopElement();
			}
			else determineElement(tag, result, annotation, output);
		}
	}

	private void determineElement(String tag, Object object, BindElement annotation, MarshallerOutput output) throws IOException, IllegalArgumentException, SecurityException, ParserException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		output.startElement(tag);
		
		if(object != null) {
			
			if(annotation.recursive().is(object.getClass()))
				examineSource(object, object.getClass(), output);
			else
				output.addText(extractValue(object, annotation.evalProvider(), ""));
		}
		
		output.stopElement();
	}

	private void appendAttributes(Object obj, List<Method> attributes, MarshallerOutput output) throws ParserException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		for(Method method : attributes) {
			Class<?> type = method.getReturnType();
			BindAttribute annotation = method.getAnnotation(BindAttribute.class);

			String name = annotation.name().length() != 0 ? annotation.name() :
				BindUtils.toXmlName(method.getName().substring(method.getName().startsWith("is") ? 2 : 3));
				
			String value = null;
			
			if(Iterable.class.isAssignableFrom(type) || type.isArray()) {
				String separator = annotation.separator();
				StringBuffer result = new StringBuffer();
				
				if(type.isArray())
					for(Object o : (Object[]) method.invoke(obj))
						result.append(separator).append(extractValue(o, annotation.evalProvider(), name));
				else
					for(Object o : (Iterable<?>) method.invoke(obj))
						result.append(separator).append(extractValue(o, annotation.evalProvider(), name));
				
				value = result.substring(separator.length());
			}
			else value = extractValue(method.invoke(obj), annotation.evalProvider(), name);
			
			output.addAttribute(name, value);
		}
	}
	
	private String extractValue(Object o, Class<? extends BindValueProvider> evalProvider, String attributeName) {
		if(o == null) return "";
		if(o instanceof BindValue) return ((BindValue) o).getBindValue();
		if(!BindValueProvider.class.equals(evalProvider)) {
			try {
				return evalProvider.newInstance().getBindValue(o);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(o instanceof Class) return ((Class<?>) o).getName();
		if(o instanceof BigDecimal) return ((BigDecimal) o).toPlainString();
		if(o instanceof Date || o instanceof Calendar) {
			Date dateTime = o instanceof Calendar?
					((Calendar) o).getTime() : (Date) o;
			
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(dateTime);
//			if(attributeName.endsWith("_time"))
//				return Dates.formatTime(dateTime);
//			else if(attributeName.endsWith("_date"))
//				return Dates.formatDate(dateTime);
//			else if(attributeName.endsWith("_sdate"))
//				return Dates.formatDate(dateTime, Dates.SYSTEM_DATE_FORMAT);
//			else
//				return Dates.formatDateTime(dateTime);
		}
		return o.toString();
	}
}
