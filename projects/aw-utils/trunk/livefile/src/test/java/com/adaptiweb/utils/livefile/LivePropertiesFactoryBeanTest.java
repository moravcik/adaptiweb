package com.adaptiweb.utils.livefile;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LivePropertiesFactoryBeanTest {

	@Value("#{testProperties.d}")
	String d;

	@Value("#{testProperties.c}")
	String c;

	@Value("#{testProperties.a}")
	String a;
	
	@Value("#{testProperties.b}")
	String b;
	
	@Test
	public void test() {
		assertEquals("a", a);
		assertEquals("ba", b);
		assertEquals("cba", c);
		assertEquals("da|ba|cba", d);
	}
	
}
