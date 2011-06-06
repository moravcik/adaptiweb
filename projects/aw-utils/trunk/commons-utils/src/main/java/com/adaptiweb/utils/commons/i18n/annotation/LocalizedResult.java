package com.adaptiweb.utils.commons.i18n.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, TYPE}) @Retention(RUNTIME)
public @interface LocalizedResult {

}    