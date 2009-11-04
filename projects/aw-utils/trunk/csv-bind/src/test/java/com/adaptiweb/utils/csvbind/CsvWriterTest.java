package com.adaptiweb.utils.csvbind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.adaptiweb.utils.csvbind.CsvReaderTest.CsvTestRecord3;
import com.adaptiweb.utils.csvbind.bean.Record2;


public class CsvWriterTest {
    /**
     * Test method for {@link com.adaptiweb.utils.csvbind.CsvWriter#writeNext(T)}.
     */
	@Test
    public final void testWriteNext() {
        try {
        	File outFile = new File("target/test-classes/testOutput.csv");
        	outFile.delete();
            CsvWriter<Record2> w = new CsvWriter<Record2>(new FileWriter(outFile), 
            		Record2.class, CsvConstants.EXCEL_SEPARATOR, CsvConstants.EXCEL_QUOTECHAR);
            assertNotNull(w);
            
            Record2 wcRecord = new Record2();
            wcRecord.setCustomer("9876543210");
            wcRecord.setResult("already");
            String out1 = w.writeNext(wcRecord);
            assertEquals("\"9876543210\";\"already\";\"\";\"\"\n", out1);

            w.closeResources();
            Assert.assertTrue("Output file must exist!", outFile.exists());
            
            outFile.delete();
            CsvWriter<CsvTestRecord3> w2 = new CsvWriter<CsvTestRecord3>(new FileWriter(outFile), 
            		CsvTestRecord3.class, CsvConstants.EXCEL_SEPARATOR, CsvConstants.EXCEL_QUOTECHAR);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(CsvReaderTest.datePattern);
            CsvTestRecord3 tr = new CsvTestRecord3("field content;; with \"semicolons\"\"; and quotes", 1234567890, "f3", date, Arrays.asList("i1", "i2", "i3", "i4"));
            String out2 = w2.writeNext(tr);
            assertEquals("\"field content;; with \"\"semicolons\"\"\"\"; and quotes\";\"1234567890\";\"f3\";\"" + dateFormat.format(date) + "\";\"i1\";\"i2\";\"i3\";\"i4\"\n", out2);
            Assert.assertTrue("Output file must exist!", outFile.exists());
            
            w2.closeResources();
        } catch (IOException e) {
            fail(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
}
