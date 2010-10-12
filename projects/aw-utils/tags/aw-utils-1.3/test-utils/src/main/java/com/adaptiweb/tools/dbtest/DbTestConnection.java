package com.adaptiweb.tools.dbtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks class with database execution methods
 * (methods marked by {@link DbTestExecution}) and gives
 * information which persistence unit will be used.
 * 
 *  @see DbTestConfig
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTestConnection {

	/**
	 * @return name of persistence unit used for {@link DbTestExecution}.
	 */
	String persistenceUnit() default "";

}
