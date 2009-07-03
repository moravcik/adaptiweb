package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.*;

import org.junit.Test;


public class ValidationModelFactoryTest {

	@Test
	public void testAnd() {
		DummyValidationModel a = new DummyValidationModel(true);
		DummyValidationModel b = new DummyValidationModel(true);
		DummyValidationModel c = new DummyValidationModel(true);
		ValidationModel and = ValidationModelFactory.and(a, b, c);
		assertTrue(and.isValid());
		c.setValid(false);
		assertFalse(and.isValid());
	}

	@Test
	public void testAdvancedAnd() {
		DummyValidationModel a = new DummyValidationModel(true);
		DummyValidationModel b = new DummyValidationModel(true);
		final DummyValidationModel c = new DummyValidationModel(true);
		c.addValidationHandler(new ValidationHandler() {
			@Override
			public void onValidationChange(ValidationEvent event) {
				c.setValid(true);
			}
		});
		ValidationModel and = ValidationModelFactory.and(a, b, c);
		assertTrue(and.isValid());
		c.setValid(false);
		assertTrue(and.isValid());
	}
}
