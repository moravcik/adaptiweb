package com.adaptiweb.gwt.test;

import com.adaptiweb.gwt.framework.GridBuilder;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Grid;

public class GridBuilderTest extends GWTTestCase {

	public void testBuild() {
		Grid grid = new GridBuilder()
			.row().cell("A").cell("B")
			.row().cell("C")
			.row().cell("D", GridBuilder.colspan(2))
			.row().cell().cell("E")
			.build();
		assertEquals(4, grid.getRowCount());
		assertEquals(2, grid.getRowFormatter().getElement(0).getChildNodes().getLength());
		assertEquals(1, grid.getRowFormatter().getElement(1).getChildNodes().getLength());
		assertEquals(1, grid.getRowFormatter().getElement(2).getChildNodes().getLength());
		assertEquals(2, grid.getRowFormatter().getElement(3).getChildNodes().getLength());
	}

	@Override
	public String getModuleName() {
		return "com.adaptiweb.gwt.Test";
	}

}
