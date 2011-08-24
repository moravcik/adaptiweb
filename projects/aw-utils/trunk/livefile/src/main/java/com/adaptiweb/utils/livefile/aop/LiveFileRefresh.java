package com.adaptiweb.utils.livefile.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME)
public @interface LiveFileRefresh {

	/**
	 * LiveFile handler bean Id
	 */
	String value();
	
}
