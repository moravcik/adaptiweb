package com.adaptiweb.utils.commons.i18n.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD) @Retention(RUNTIME)
public @interface Localized {

	/**
	 * Pattern for LocalizedLabel#key<br/>
	 * Use {fieldName} for referencing id field, which is evaluated as String.valueOf(), single id field is supported<br/>
	 * Use # to reference index of list
	 * <p>
	 * e.g offer.{id}.name,
	 *     offer.{id}.note.#
	 */
	String key();
	
	/**
	 * Statically defined locale to load
	 */
	String locale() default "";

	public static final String DEFAULT_LOCALE = "EN";
	
}    