package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicModelFactory;


public class LogicModelFactoryTest {

	@Test
	public void testAnd() {
		DummyValidation a = new DummyValidation(true);
		DummyValidation b = new DummyValidation(true);
		DummyValidation c = new DummyValidation(true);
		LogicModel and = LogicModelFactory.and(a, b, c);
		assertTrue(and.getLogicValue());
		c.setValid(false);
		assertFalse(and.getLogicValue());
	}

	@Test
	public void testAdvancedAnd() {
		DummyValidation a = new DummyValidation(true);
		DummyValidation b = new DummyValidation(true);
		final DummyValidation c = new DummyValidation(true);
		final LogicModel and = LogicModelFactory.and(a, b, c);
		
		c.addValidationHandler(new ValidationHandler() {
			@Override
			public void onValidationChange(ValidationEvent event) {
				c.setValid(true);
			}
		}, false);
		
		assertTrue(and.getLogicValue());
		c.setValid(false);
		assertTrue(and.getLogicValue());
	}
	
	@Test
	public void testOr() {
		DummyValidation a = new DummyValidation(false);
		DummyValidation b = new DummyValidation(false);
		DummyValidation c = new DummyValidation(false);
		LogicModel or = LogicModelFactory.or(a, b, c);
		assertFalse(or.getLogicValue());
		c.setValid(true);
		assertTrue(or.getLogicValue());
	}
}
