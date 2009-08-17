package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.*;

import org.junit.Test;


public class ValidationModelFactoryTest {

	@Test
	public void testAnd() {
		DummyValidation a = new DummyValidation(true);
		DummyValidation b = new DummyValidation(true);
		DummyValidation c = new DummyValidation(true);
		ValidationModel and = ValidationModelFactory.and(a, b, c);
		assertTrue(and.isValid());
		c.setValid(false);
		assertFalse(and.isValid());
	}

	@Test
	public void testAdvancedAnd() {
		DummyValidation a = new DummyValidation(true);
		DummyValidation b = new DummyValidation(true);
		final DummyValidation c = new DummyValidation(true);
		
		c.addValidationHandler(new ValidationHandler() {
			@Override
			public void onValidationChange(ValidationEvent event) {
				c.setValid(true);
			}
		}, false);
		
		ValidationModel and = ValidationModelFactory.and(a, b, c);
		assertTrue(and.isValid());
		c.setValid(false);
		assertTrue(and.isValid());
	}
	
	@Test
	public void testOr() {
		DummyValidation a = new DummyValidation(false);
		DummyValidation b = new DummyValidation(false);
		DummyValidation c = new DummyValidation(false);
		ValidationModel or = ValidationModelFactory.or(a, b, c);
		assertFalse(or.isValid());
		c.setValid(true);
		assertTrue(or.isValid());
	}
}
