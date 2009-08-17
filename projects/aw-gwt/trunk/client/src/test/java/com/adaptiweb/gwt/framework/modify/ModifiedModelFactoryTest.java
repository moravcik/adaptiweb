package com.adaptiweb.gwt.framework.modify;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModifiedModelFactoryTest {

	@Test
	public void testOr() {
		ModifiedModelSet tested = ModifiedModelFactory.or();
		
		assertFalse(tested.isModified());
		
		BaseModifiedModel m1 = new BaseModifiedModel();
		assertFalse(m1.isModified());
		
		tested.add(m1);
		assertFalse(tested.isModified());
	}

}
