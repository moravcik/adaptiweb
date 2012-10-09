package com.adaptiweb.blog.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpelFreemarkerTest {

	@Autowired GreetingBean greetingBean;
	
	@Autowired Configuration freemarkerConfig;
	
	@Test
	public void testFreemarkerSpel() throws IOException, TemplateException {
		Map<String, String> model = new HashMap<String, String>();
		model.put("name", "Felix");
		Template template = freemarkerConfig.getTemplate("greeting-template.ftl");
		String output = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		System.out.println(output);
		String expected = "We say '" + greetingBean.printGreeting(model.get("name")) + "' from Freemarker";
		assertEquals(expected, output);
	}
	
	public static class GreetingBean {
		private String greeting;
		
		@Required
		public void setGreeting(String greeting) {
			this.greeting = greeting;
		}
		
		public String printGreeting(String name) {
			return greeting + " " + name;
		}
	}
	

}
