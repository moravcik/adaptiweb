package com.adaptiweb.utils.spel;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpelEvaluatorFactoryBeanTest {

	@Value("#{myProperties.aaa}")
	String aaa;
	
	@Value("#{testProperties.bbb}")
	String bbb;

	@Value("#{testProperties.ccc}")
	String ccc;
	
	@Value("#{testProperties.ddd}")
	String ddd;

	@Value("#{testProperties}")
	Properties testProperties;
	
	@Autowired @Qualifier("testSpel") SpelEvaluator spel;
	@Autowired @Qualifier("testSpel") SpelEvaluatorFactoryBean spelFactory;
	
	@Test
	public void testProperties() {
		assertEquals("V@Lu3", aaa);
		assertEquals("V@Lu3", bbb);
		assertEquals("V@Lu3", spelFactory.getObject().setExpression(ccc).evaluate());
		assertEquals("V@Lu3/V@Lu3", spel.setExpression("${myProperties.aaa}/${testProperties.bbb}").evaluate());
		assertEquals("V@Lu3/V@Lu3", spel.setRootObject(testProperties).setExpression(ddd).evaluate());
	}
	
	@Test
	public void testNestedMap() {
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("d", "d${a + '|' + b + '|' + c}");
		testMap.put("c", "c${b}");
		testMap.put("a", "a");
		testMap.put("b", "b${a}");
		SpelEvaluator eval = spelFactory.getObject().setRootObject(testMap);
		assertEquals("cba", eval.setExpression("${c}").evaluate());
		assertEquals("a", eval.setExpression("${a}").evaluate());
		assertEquals("ba", eval.setExpression("${b}").evaluate());
		assertEquals("da|ba|cba", eval.setExpression("${d}").evaluate(String.class));
	}
	
	@Test
	public void testVariables() {
		spel.setVariable("user", "Marissa");
		assertEquals("Hello Marissa", spel.setExpression("Hello ${#user}").evaluate());
		Map<String, String> months = new HashMap<String, String>();
		months.put("first", "January");
		months.put("second", "February");
		months.put("third", "March");
		assertEquals("Marissa signed in February", spel.setRootObject(months)
				.setExpression("${#user} signed in ${second}")
				.evaluate());
	}
	
	private static final SimpleDateFormat testFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Test
	public void testFunctions() throws SecurityException, NoSuchMethodException {
		spel.registerFunction("printDate", SpelEvaluatorFactoryBeanTest.class.getDeclaredMethod("testPrintDate", Date.class));
		Date today = Calendar.getInstance().getTime();
		assertEquals(testFormat.format(today), spel.setExpression("${#printDate(#root)}").setRootObject(today).evaluate());
	}

	public static String testPrintDate(Date date) {
		return testFormat.format(date);
	}
	
}
