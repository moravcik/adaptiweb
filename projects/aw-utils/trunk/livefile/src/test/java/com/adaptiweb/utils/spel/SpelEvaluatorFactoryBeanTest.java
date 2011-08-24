package com.adaptiweb.utils.spel;

import static org.junit.Assert.*;

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
	public void test() {
		assertEquals("V@Lu3", aaa);
		assertEquals("V@Lu3", bbb);
		assertEquals("V@Lu3", spelFactory.getObject().setExpression(ccc).evaluate());
		assertEquals("V@Lu3/V@Lu3", spel.setExpression("${myProperties.aaa}/${testProperties.bbb}").evaluate());
		assertEquals("V@Lu3/V@Lu3", spel.setRootObject(testProperties).setExpression(ddd).evaluate());
	}

}
