package com.adaptiweb.gwt.framework;

import static org.junit.Assert.*;

import org.junit.Test;

public class GwtGoodiesTest {

	@Test
	public void testFormat() {
		assertEquals("Ahoj Fero!", GwtGoodies.format("Ahoj $0!", "Fero"));
	}

	@Test
	public void testEscapeFormat() {
		assertEquals("Ahoj $0!", GwtGoodies.format("Ahoj $$0!", "Fero"));
	}

	@Test
	public void testEscapeFormatAtEnd() {
		assertEquals("Ahoj $", GwtGoodies.format("Ahoj $$", "Fero"));
	}

	@Test
	public void testEscapeAndFormat() {
		assertEquals("Ahoj $Fero!", GwtGoodies.format("Ahoj $$$0!", "Fero"));
	}

	@Test
	public void testFormatMoreParams() {
		assertEquals("Ahoj Fero!", GwtGoodies.format("$0 $1!", "Ahoj", "Fero"));
	}

	@Test
	public void testFormatDoubleIncluded() {
		assertEquals("Ahoj Fero Fero!", GwtGoodies.format("Ahoj $0 $0!", "Fero"));
	}

	@Test
	public void testFormatParametersJustBehind() {
		assertEquals("AhojFero!", GwtGoodies.format("$0$1!", "Ahoj", "Fero"));
	}

	@Test
	public void testFormatParameterAtEnd() {
		assertEquals("Ahoj Fero", GwtGoodies.format("Ahoj $0", "Fero"));
	}

	@Test
	public void testFormatNoise$() {
		assertEquals("Ahoj Fero!", GwtGoodies.format("Ah$oj $0!", "Fero"));
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testFormatWithInvalidIndex() {
		assertEquals("Ahoj Fero!", GwtGoodies.format("Ahoj $1!", "Fero"));
	}
}
