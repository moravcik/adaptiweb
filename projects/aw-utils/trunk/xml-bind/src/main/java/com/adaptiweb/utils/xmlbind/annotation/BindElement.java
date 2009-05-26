package com.adaptiweb.utils.xmlbind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.adaptiweb.utils.xmlbind.BindValueProvider;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Inherited
public @interface BindElement {
	public RecusiveType recursive() default RecusiveType.AUTO;

	public Class<? extends BindValueProvider> evalProvider() default BindValueProvider.class;

	public String tagName() default "";

	public String itemTag() default "";

	public Class<?> targetClass() default void.class;
}
