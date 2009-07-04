package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.adaptiweb.gwt.mvc.model.DefaultNumberModel;

public class MinMaxValidationTest {

	@Test
	public void testMinMaxValidationNumberModelOfTDoubleDouble() {
		DefaultNumberModel<Double> model = new DefaultNumberModel<Double>();
		MinMaxValidation tested = new MinMaxValidation(model, 1.1, 3.0);
		assertTrue(tested.isValid());

		model.setNumber(1.0);
		assertFalse(tested.isValid());

		model.setNumber(3.0);
		assertTrue(tested.isValid());

		model.setNumber(3.1);
		assertFalse(tested.isValid());

		model.setNumber(null);
		assertTrue(tested.isValid());
	}

}
