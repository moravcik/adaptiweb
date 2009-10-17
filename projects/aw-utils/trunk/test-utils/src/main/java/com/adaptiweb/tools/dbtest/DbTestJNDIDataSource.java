package com.adaptiweb.tools.dbtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTestJNDIDataSource {
	String jndiName();
	Class<?> driver();
	String url();
	String username() default "";
	String password() default "";
}
