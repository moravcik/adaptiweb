package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adaptiweb.gwt.mvc.model.DefaultStringModel;
import com.adaptiweb.gwt.mvc.model.ValueChangeModel;

public class NotNullValidationTest {

	@Test
	public void testValidateNotNull() {
		ValueChangeModel<String> model = new DefaultStringModel();
		NotNullValidationModel tested = NotNullValidationModel.create(model);
		
		assertFalse(tested.isValid());
		model.setValue("string");
		assertTrue(tested.isValid());
		model.setValue(null);
		assertFalse(tested.isValid());
		
		tested.discard();
	}
	
}
