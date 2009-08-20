package com.adaptiweb.gwt.framework.modify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adaptiweb.gwt.mvc.model.DefaultNumberModel;

public class ModifiedModelGroupTest {

	@Test
	public void testDefaultUsage() {
		ModifiedModelGroup tested = new ModifiedModelGroup();
		
		assertFalse(tested.isModified());
		
		DefaultNumberModel<Integer> numberModel = new DefaultNumberModel<Integer>();
		assertFalse(tested.add(numberModel).isModified());
		assertFalse(tested.isModified());
		
		numberModel.setNumber(0);
		
		assertTrue(tested.isModified());
		
		tested.burn();
		
		assertFalse(tested.isModified());
	}

}
