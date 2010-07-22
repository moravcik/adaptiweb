package com.adaptiweb.utils.csvbind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.adaptiweb.utils.csvbind.CsvReaderTest.CsvTestRecord3;
import com.adaptiweb.utils.csvbind.CsvReaderTest.CsvTestRecord3.TestEnum;
import com.adaptiweb.utils.csvbind.bean.Record2;


public class CsvWriterTest {
	Date testDate;
	DateFormat testDateFormat;
	
	@Before
	public void setup() {
        testDate = new Date();
        testDateFormat = new SimpleDateFormat(CsvReaderTest.datePattern); // no thread-safe, FastDateFormat should be used
	}
	
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
            CsvTestRecord3 tr = new CsvTestRecord3("field content;; with \"semicolons\"\"; and quotes", 1234567890, "f3", testDate, TestEnum.CCC, Arrays.asList("i1", "i2", "i3", "i4"));
            String out2 = w2.writeNext(tr);
            assertEquals("\"field content;; with \"\"semicolons\"\"\"\"; and quotes\";\"1234567890\";\"f3\";\"" + testDateFormat.format(testDate) + "\";\"" + TestEnum.CCC.name() + "\";\"i1\";\"i2\";\"i3\";\"i4\"\n", out2);
            Assert.assertTrue("Output file must exist!", outFile.exists());
            
            w2.closeResources();
        } catch (IOException e) {
            fail(e.getClass().getName() + ": " + e.getMessage());
        }
    }
	
	@Test
	public void testWriteToString() {
        CsvTestRecord3 tr1 = new CsvTestRecord3("field content;; with \"semicolons\"\"; and quotes1", 1234567891, "f4", testDate, TestEnum.AAA, Arrays.asList("i1", "i2", "i3", "i4"));
        CsvTestRecord3 tr2 = new CsvTestRecord3("field content;; with \"semicolons\"\"; and quotes2", 1234567892, "f5", testDate, TestEnum.BBB, Arrays.asList("j1", "j2", "j3"));
        String[] out = CsvWriter.writeToString(tr1, tr2).split("\n");
        assertEquals("\"field content;; with \"\"semicolons\"\"\"\"; and quotes1\";\"1234567891\";\"f4\";\"" + testDateFormat.format(testDate) + "\";\"" + TestEnum.AAA.name() + "\";\"i1\";\"i2\";\"i3\";\"i4\"", out[0]);
        assertEquals("\"field content;; with \"\"semicolons\"\"\"\"; and quotes2\";\"1234567892\";\"f5\";\"" + testDateFormat.format(testDate) + "\";\"" + TestEnum.BBB.name() + "\";\"j1\";\"j2\";\"j3\"", out[1]);
        assertEquals(2, out.length);
	}
    
}
