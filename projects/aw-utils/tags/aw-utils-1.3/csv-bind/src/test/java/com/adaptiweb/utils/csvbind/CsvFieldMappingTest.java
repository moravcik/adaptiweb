package com.adaptiweb.utils.csvbind;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.adaptiweb.utils.csvbind.CsvFieldMapping;
import com.adaptiweb.utils.csvbind.CsvReaderTest.CsvTestRecord;
import com.adaptiweb.utils.csvbind.CsvReaderTest.CsvTestRecord2;


public class CsvFieldMappingTest {

    /**
     * Test method for {@link com.adaptiweb.utils.csvbind.CsvFieldMapping#getAllDeclaredFields()}.
     */
	@Test
    public final void testGetAllDeclaredFields() {
    	CsvInputMappingPublic<CsvTestRecord2> m = new CsvInputMappingPublic<CsvTestRecord2>();
    	List<Field> fields = m.getAllDeclaredFields(CsvTestRecord2.class);
    	assertNotNull(fields);
    	assertFalse(fields.isEmpty());
    	assertTrue(fields.containsAll(Arrays.asList(CsvTestRecord.class.getDeclaredFields())));
    	assertTrue(fields.containsAll(Arrays.asList(CsvTestRecord2.class.getDeclaredFields())));
    }

    public static class CsvInputMappingPublic<T> extends CsvFieldMapping<T> {
		@Override
        public List<Field> getAllDeclaredFields(Class<?> recordClass) {
        	return super.getAllDeclaredFields(recordClass);
        }
    }

}
