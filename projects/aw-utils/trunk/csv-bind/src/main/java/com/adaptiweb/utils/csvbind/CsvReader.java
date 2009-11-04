package com.adaptiweb.utils.csvbind;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;


import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * CSV files reader based on opencsv {@link CSVReader}
 * provides iterator over CSV records mapped to beans of type T.
 * CSV -> bean gsm is defined in T class by {@link CsvField} annotations.
 * 
 * Extends {@link CsvToBean} class in order to reuse protected {@link #processLine(MappingStrategy, String[])} method.
 * 
 * @param <T> target bean class
 */
public class CsvReader<T> extends CsvToBean<T> implements Iterable<T> {
    private CSVReader reader;
    private T nextRecord = null;
    private final T errorRecord;
    /**
     * Stores next line of parsing.
     */
    private String nextLine;
    /**
     * Bean strategy
     */
    protected CsvFieldMapping<T> mapping;
    
    /**
     * Aligning of parsed line.
     */
    protected LineAlign alignType;
    
    public enum LineAlign {
    	DEFAULT, // no change to line
    	LEFT;    // align left to first not-empty cell
    }
    
    /**
     * Constructor initializes {@link CSVReader} and gsm strategy.
     * If beanClass is not annotated with {@link CsvField} annotations for gsm CSV lines,
     * runtime exception IllegalArgumentException is thrown.
     * 
     * @param reader CSV content reader
     * @param beanClass target bean class annotated with {@link CsvField} annotations
     * @param align type of aligning parsed line.
     * @param separator CSV value separator
     * @param quotechar CSV quote character if separator is present in value
     *                  if quote character is present in value it is doubled.
     */
    public CsvReader(Reader reader, Class<T> beanClass, LineAlign align, char separator, char quotechar) {
        this.reader = new CSVReader(reader, separator, quotechar);
        try {
			errorRecord = beanClass.newInstance(); // empty bean instance to determine line parsing error
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Error initializing CSV bean: "+ e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Error initializing CSV bean: "+ e.getMessage(), e);
		}
        alignType = align;
        // define bean gsm
        mapping = new CsvFieldMapping<T>();
        mapping.setType(beanClass);
    }

    /**
     * Constructor initializes {@link CSVReader} and gsm strategy.
     * If beanClass is not annotated with {@link CsvField} annotations for gsm CSV lines,
     * runtime exception IllegalArgumentException is thrown.
     * 
     * @param reader CSV content reader
     * @param beanClass target bean class annotated with {@link CsvField} annotations
     * @param align type of aligning parsed line.
     */
    public CsvReader(Reader reader, Class<T> beanClass, LineAlign align) {
    	this(reader, beanClass, align, CsvConstants.EXCEL_SEPARATOR, CsvConstants.EXCEL_QUOTECHAR);
    }

    /**
     * Constructor initializes {@link CSVReader} and gsm strategy.
     * If beanClass is not annotated with {@link CsvField} annotations for gsm CSV lines,
     * runtime exception IllegalArgumentException is thrown.
     * Default Excel-like separator and quotechar is used.
     * 
     * @param reader CSV content reader
     * @param beanClass target bean class annotated with {@link CsvField} annotations
     */
    public CsvReader(Reader reader, Class<T> beanClass) {
    	this(reader, beanClass, LineAlign.DEFAULT, CsvConstants.EXCEL_SEPARATOR, CsvConstants.EXCEL_QUOTECHAR);
    }

    /**
     * Read next line from CSV, perform bean gsm and store as nextRecord.
     * When end of CSV is reached null is stored as nextRecord.
     */
    private void loadNextRecord() {
    	mapping.initMapping(); // clear list cache
    	
    	String[] line = null;
        try {
            line = reader.readNext();
            if (line != null) {                
                nextLine = "";
                for (int i = 0; i < line.length; i++) { // hack to get line of parsing (for callers of parsing process)
                    if (i > 0) nextLine += CsvConstants.EXCEL_SEPARATOR;
                    nextLine += line[i];
                }
            	if (removeComment(line) || isEmptyLine(line)) loadNextRecord(); // returns
            	else {
            		Integer alignIndex = 0;
            		if (alignType.equals(LineAlign.LEFT)) alignIndex = alignLineLeft(line);
            		
            		nextRecord = (T) processLine(mapping, line);
            		if (nextRecord instanceof CsvAlignIndex) {
            			((CsvAlignIndex) nextRecord).setAlignIndex(alignIndex);
            		}
            	}
            } else {
                nextRecord = null;
            }
        } catch (Exception e) {
            // LOG.error("Error parsing CSV: " + e.getMessage(), e);
            nextRecord = errorRecord;
            if (e instanceof CsvException) throw (CsvException) e;
            else throw new CsvException("Error parsing CSV: " + e.getMessage(), e, line);
        }
    }
    
    /**
     * Shift left all cell values to begin with not empty value
     * @param line
     */
    protected Integer alignLineLeft(String[] line) {
    	Integer alignIndex = 0;
    	// find shift level
		for (int i = 0; i < line.length; i++) {
			if (line[i] != null && line[i].trim().length() > 0) {
				alignIndex = i;
				break;
			}
		}
		if (alignIndex > 0) { // shift cells to align left
			for (int i = alignIndex; i < line.length; i++) {
				line[i - alignIndex] = line[i];
				line[i] = "";
			}
		}
		return alignIndex;
	}

	/**
     * Check if all values in line are empty
     * @param line
     * @return
     */
    protected boolean isEmptyLine(String[] line) {
    	for (int i = 0; i < line.length; i++)
    		if (line[i] != null && line[i].trim().length() > 0) return false;
		return true;
	}

	/**
     * Clear all values after COMMENT_CHAR.
     * Comment is valid if COMMENT_CHAR is present as first character in cell.
     * @param line
     * @return Returns if comment was present
     */
    protected boolean removeComment(String[] line) {
    	boolean commentLine = false;
    	for (int i = 0; i < line.length; i++) {
    		String value = line[i].trim();
    		if (value != null && value.trim().length() > 0
    				&& value.charAt(0) == CsvConstants.COMMENT_CHAR) commentLine = true;
			if (commentLine) line[i] = "";
    	}
		return commentLine;
	}

	/**
     * Close underlying resources.
     */
    public void closeResources() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
    }
    
    /**
     * Returns iterator of T beans over CSV. {@link Iterator#remove()} operation is not supported.
     */
    public Iterator<T> iterator() {
        return new Iterator<T>() {
        	private boolean initial = true;
            public boolean hasNext() {
            	if (initial || nextRecord == errorRecord) { // intentionally "==" comparing
            		initial = false;
            		loadNextRecord();
            	}
                return nextRecord != null;
            }
            public T next() {
                if (hasNext()) {
                    try {
                        return nextRecord;
                    } finally {
                        loadNextRecord();
                    }
                } else throw new NoSuchElementException();
            }
            public void remove() {
                throw new UnsupportedOperationException("remove() method is not supported");
            }
        };
    }

    @Override
    protected PropertyEditor getPropertyEditor(PropertyDescriptor propertydescriptor) throws InstantiationException, IllegalAccessException {
    	return mapping.getEditor(propertydescriptor.getName());
    }
    
}
