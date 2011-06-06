package com.adaptiweb.utils.commons.i18n.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Flag for determining multiple localized labels in String field.<br/>
 * Following string value:
 * <pre>
 * [EN] Product name
 * [SK] Meno produktu
 * </pre>
 * <p>
 * will be determined with pattern '[{locale}] {label}' and value separator '\n'<br/>
 * <p>
 * For simplicity {locale} length is assumed as fixed (2 characters) and separator is concatenating {label}, there are no characters between {label} and separator.
 * In other words, values are resolved using fixed {locale} index and {label} index.  
 */
@Target(FIELD) @Retention(RUNTIME)
public @interface LocalizedValues {

	/**
	 * Locale-value pattern inside single line
	 */
	String pattern() default "[" + PATTERN_LOCALE + "] " + PATTERN_VALUE;
	
	/**
	 * Value separator
	 */
	String separator() default "\n";
		
	public static final String PATTERN_LOCALE = "{locale}";
	public static final String PATTERN_VALUE = "{label}";
	public static final int LOCALE_LENGTH = 2;
	
}    