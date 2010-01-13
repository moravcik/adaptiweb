package com.adaptiweb.utils.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class VariableResolverTest {

	@Test
	public void testResolveVariable() {
		VariableSource sourceA = variableSource("a=x");
		VariableResolver tested = new VariableResolver(sourceA);
		
		assertEquals("x", tested.resolveValue("a"));
		assertNull(tested.resolveValue("b"));
	}
	
	@Test
	public void testSimpleSubstitution() {
		VariableSource sourceA = variableSource("a=${x}","y=2");
		VariableSource sourceB = variableSource("x=1", "b=$y");
		VariableResolver tested = new VariableResolver(sourceA, sourceB);
		
		assertEquals("1", tested.resolveValue("x"));
		assertEquals("1", tested.resolveValue("a"));
		assertEquals("2", tested.resolveValue("b"));
	}
	
	@Test
	public void testMultiLevelSubstitution() {
		VariableSource sourceA = variableSource("a=${x}","b=${y}");
		VariableSource sourceB = variableSource("x=$b","y=?");
		VariableResolver tested = new VariableResolver(sourceA, sourceB);
		
		assertEquals("?", tested.resolveValue("a"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testCircularSubstitution() {
		VariableSource sourceA = variableSource("a=${b}","b=${x}","x=$y","y=$z","z=$y");
		VariableResolver tested = new VariableResolver(sourceA);
		tested.resolveValue("a");
	}
	
	@Test
	public void testMultipleSubstitution() {
		VariableSource sourceA = variableSource("a=${x}+${y}=$z","x=(${k}-$l)","k=8","l=12","y=5","z=1");
		VariableResolver tested = new VariableResolver(sourceA);
		
		assertEquals("(8-12)+5=1", tested.resolveValue("a"));
	}

	@Test
	public void testEscapeCharaktersSubstitution() {
		VariableSource sourceA = variableSource("a=$${x}+$y$","y=\\a\\$$z");
		VariableResolver tested = new VariableResolver(sourceA);
		
		assertEquals("${x}+\\a\\$z$", tested.resolveValue("a"));
	}
	
	@Test
	public void testAvoidTrimSubstitution() {
		VariableSource sourceA = variableSource("a= ${b} ","b= . ");
		VariableResolver tested = new VariableResolver(sourceA);
		
		assertEquals("  .  ", tested.resolveValue("a"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMissingSubstitution() {
		VariableSource sourceA = variableSource("a=$b","b=${x}");
		VariableResolver tested = new VariableResolver(sourceA);
		
		assertEquals("  .  ", tested.resolveValue("a"));
	}
	
	private static VariableSource variableSource(String...definitions) {
		Map<String, String> result = new HashMap<String, String>(definitions.length);
		
		for (String definition : definitions) {
			int index = definition.indexOf('=');
			result.put(definition.substring(0, index), definition.substring(index + 1));
		}
		return VariableResolver.asSource(result);
	}
}
