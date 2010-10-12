package com.adaptiweb.tools.dbtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.EntityManager;

/**
 * This annotation marks methods for database execution.
 * Annotated method must have exactly one parameter of type {@link EntityManager}.
 * 
 * @see DbTestConfig
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTestExecution {

	/**
	 * This required parameter defines execution order.
	 * There cannot be more than one annotations with same order value in one class. 
	 * @return order
	 */
	int order();
	
	/**
	 * @return <tt>true</tt> (default) if execution required transaction,
	 * otherwise <tt>false</tt>.
	 */
	boolean useTransaction() default true;

}
