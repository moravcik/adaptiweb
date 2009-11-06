package com.adaptiweb.gwt.framework.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adaptiweb.gwt.mvc.model.DefaultValueChangeModel;
import com.adaptiweb.gwt.mvc.model.ValueChangeModel;
import com.adaptiweb.gwt.util.ConcatUtils;

public class OneOfArrayValidationTest {

	enum TestEnum {
		A, B, C, D
	}
	
	@Test
	public void testValidateNotNull() {
		ValueChangeModel<TestEnum> model = new DefaultValueChangeModel<TestEnum>();
		OneOfArrayValidationModel tested = OneOfArrayValidationModel.create(model);
		
		assertFalse(tested.isValid());
		model.setValue(TestEnum.A);
		assertFalse(tested.isValid());
		
		tested.discard();
		tested = OneOfArrayValidationModel.create(model, TestEnum.A, TestEnum.C, TestEnum.D);
		
		assertTrue(tested.isValid());
		model.setValue(TestEnum.B);
		assertFalse(tested.isValid());
		assertTrue(tested.getErrorMessage()
				.indexOf(ConcatUtils.concat(", ", TestEnum.A, TestEnum.C, TestEnum.D)) >= 0);
		model.setValue(TestEnum.C);
		assertTrue(tested.isValid());
		model.setValue(TestEnum.D);
		assertTrue(tested.isValid());
		model.setValue(null);
		assertFalse(tested.isValid());
		
		tested.discard();
		tested = OneOfArrayValidationModel.create(model, TestEnum.values());

		model.setValue(TestEnum.B);
		assertTrue(tested.isValid());

		tested.discard();
	}

	
}
