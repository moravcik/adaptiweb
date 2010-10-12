package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adaptiweb.gwt.mvc.model.DefaultNumberModel;

public class PrecisionValidationTest {

	@Test
	public void testValidateNumberInt() {
		DefaultNumberModel<Number> model = new DefaultNumberModel<Number>();
		PrecisionValidation tested = new PrecisionValidation(model, 2);
		
		assertTrue(tested.isValid());
		model.setNumber(20.568);
		assertFalse(tested.isValid());
		model.setNumber(20.56);
		assertTrue(tested.isValid());
		model.setNumber(20);
		assertTrue(tested.isValid());
		
		tested.discard();
	}

}
