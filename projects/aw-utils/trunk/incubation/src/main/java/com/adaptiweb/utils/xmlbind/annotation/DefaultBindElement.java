package com.adaptiweb.utils.xmlbind.annotation;


public class DefaultBindElement {
	public static final BindElement defaultInstance = defaultAnnotation();
	
	@BindElement
	private static BindElement defaultAnnotation() {
		try {
			return DefaultBindElement.class
				.getDeclaredMethod("defaultAnnotation")
				.getAnnotation(BindElement.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
