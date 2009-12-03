package com.adaptiweb.utils.csvbind;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import au.com.bytecode.opencsv.CSVWriter;

import com.adaptiweb.utils.csvbind.CsvFieldMapping.CsvFieldDescriptor;
import com.adaptiweb.utils.csvbind.CsvFieldMapping.CollectionPropertyEditor;

/**
 * Wrapped opencsv CSVWriter.
 * Provides {@link #writeNext(T)} method returning last line as it was written.
 */
public class CsvWriter<T> {
	
	private class WriterWrapper extends Writer {
		Writer outputWriter;
		
		WriterWrapper(Writer writer) {
			this.outputWriter = writer;
		}
        @Override
        public void write(String s, int i, int j) throws IOException {
            outputWriter.write(s, i, j);
            lastLine.append(s.substring(i, i + j)); // needed to capture last line
        }
		@Override
		public void close() throws IOException {
			outputWriter.close();
		}
		@Override
		public void flush() throws IOException {
			outputWriter.flush();
		}
		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			outputWriter.write(cbuf, off, len);
		}
	}
	
    /**
     * Bean strategy
     */
    protected CsvFieldMapping<T> mapping;

    private CSVWriter csvWriter = null;
    private StringBuffer lastLine = null;
    
    public CsvWriter(Writer writer, Class<T> beanClass, char separator, char quotechar) throws IOException {
        csvWriter = new CSVWriter(new WriterWrapper(writer), separator, quotechar);
        mapping = new CsvFieldMapping<T>();
        mapping.setType(beanClass);
    }

    public CsvWriter(Writer writer, Class<T> beanClass) throws IOException {
    	this(writer, beanClass, CsvConstants.EXCEL_SEPARATOR, CsvConstants.EXCEL_QUOTECHAR);
    }
    
    /**
     * 
     * @param output T to write.
     * @return Returns written line.
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public String writeNext(T bean) throws CsvException {
    	mapping.initMapping(); // clear list cache
    	
        lastLine = new StringBuffer();
        try {
        	List<String> stringCols = new ArrayList<String>();
	        for (CsvFieldDescriptor desc : mapping.getFieldDescriptors()) {
	        	Object value = PropertyUtils.getProperty(bean, desc.getField().getName());
	        	if (value == null) stringCols.add("");
	        	else {
		        	PropertyEditor editor = desc.getFieldEditor();
		        	if (value instanceof Collection<?>  // handle collection field
		        			&& editor instanceof CsvFieldMapping.CollectionPropertyEditor) {
		        		
		        		PropertyEditor itemEditor = ((CollectionPropertyEditor) editor).getItemEditor();
		        		for (Object valueItem : (Collection<?>) value) {
		        			if (valueItem == null) stringCols.add("");
		        			else {
		            			itemEditor.setValue(valueItem);
		            			stringCols.add(itemEditor.getAsText());
		        			}
		        		}		        		
		        	} else { // handle standard fields
			        	editor.setValue(value);
			        	stringCols.add(editor.getAsText());
		        	}
	        	}
	        }
	        csvWriter.writeNext(stringCols.toArray(new String[stringCols.size()]));
	        
        } catch (Exception e) {
        	throw new CsvException("Error writing CSV line", e);
        }
        return lastLine.toString();
    }
    
    public String writeComment(String comment) {
    	lastLine = new StringBuffer();
    	csvWriter.writeNext(new String[] { CsvConstants.COMMENT_CHAR + comment});
    	return lastLine.toString();
    }
    
    /**
     * Close underlying resources.
     */
    public void closeResources() {
        if (csvWriter != null) {
            try {
                csvWriter.close();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
    }
}
