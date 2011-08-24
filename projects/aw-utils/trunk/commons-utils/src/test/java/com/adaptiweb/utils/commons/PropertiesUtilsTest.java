package com.adaptiweb.utils.commons;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;

public class PropertiesUtilsTest {

	@Test
	public void selectProperties() {
		Properties p = new Properties();
		p.setProperty("a", "a");
		p.setProperty("a.b", "a.b");
		p.setProperty("a.b.c", "a.b.c");
		p.setProperty("d.b", "d.b");
		p.setProperty("d.b.a", "d.b.a");
		p.setProperty("d.c", "d.c");
		p.setProperty("e", "e");
		
		Map<String, String> pa = PropertiesUtils.select(p, "a");
		assertTrue(pa.containsKey("b"));
		assertEquals("a.b", pa.get("b"));
		assertTrue(pa.containsKey("b.c"));
		assertEquals("a.b.c", pa.get("b.c"));
		
		Map<String, String> pab = PropertiesUtils.select(p, "a.b");
		assertTrue(pab.containsKey("c"));
		assertEquals("a.b.c", pab.get("c"));
		
		Map<String, String> pd = PropertiesUtils.select(p, "d");
		assertTrue(pd.containsKey("b"));
		assertEquals("d.b", pd.get("b"));
		assertTrue(pd.containsKey("c"));
		assertEquals("d.c", pd.get("c"));
		assertTrue(pd.containsKey("b.a"));
		assertEquals("d.b.a", pd.get("b.a"));

		Map<String, String> pdb = PropertiesUtils.select(p, "d.b");
		assertTrue(pdb.containsKey("a"));
		assertEquals("d.b.a", pdb.get("a"));
	}
	
}
