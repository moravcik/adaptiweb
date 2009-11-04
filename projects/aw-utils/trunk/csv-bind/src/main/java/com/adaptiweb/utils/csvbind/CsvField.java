package com.adaptiweb.utils.csvbind;

import java.beans.PropertyEditor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for {@link CsvReader} target bean fields.
 * Field annotation with its index defines field position in CSV line.
 * Field CsvField index values should be continuous, skipped indexes are ignored.
 * 
 * @see CsvReader
 */

@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)

public @interface CsvField {
	/**
	 * column index in CSV
	 * @return
	 */
    int index();

    /**
     * property editor for column
     * @return
     */
    Class<? extends PropertyEditor> editor() default PropertyEditor.class;
    
    /**
     * patterns for parsing string values
     * @return
     */
    String[] patterns() default {};
    
    /**
     * true if cointains array of CSV values from given index to end, variable length.
     * Field is of type List. editor has to be specified.
     * @return
     */
    boolean list() default false;
    
}    