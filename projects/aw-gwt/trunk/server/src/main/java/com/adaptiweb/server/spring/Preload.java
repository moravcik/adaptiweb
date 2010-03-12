package com.adaptiweb.server.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.AbstractRemoteServiceServlet;


/**
 * This annotation is for method implementing some GWT {@link RemoteService}'s method.
 * 
 * <p>
 * <b>Preconditions:</b><ul>
 * <li>Annotated method can't use {@link AbstractRemoteServiceServlet#getThreadLocalRequest()}
 * or {@link AbstractRemoteServiceServlet#getThreadLocalResponse()} because invoking
 * is realized outside RPC call.</li>
 * <li>Method can't have parameters.</li>
 * </ul>
 * </p> 
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Preload {

	/**
	 * Determine names of GWT modules for which is this service applicable.
	 * <b>Default</b> is applicable for all modules.
	 */
	String[] modules() default {};
	
	/**
	 * Determine JavaScript variable name for serialized value.
	 * <b>Default</b> is derived from full qualified response class name where '.' are replaced by '_'
	 */
	String name() default "";
	
}
