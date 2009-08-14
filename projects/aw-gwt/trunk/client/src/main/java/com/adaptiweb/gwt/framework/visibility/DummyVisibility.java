package com.adaptiweb.gwt.framework.visibility;


public class DummyVisibility extends AbstractVisibilityModel {

	public DummyVisibility() {}

	public DummyVisibility(boolean initialValidStatus) {
		super(initialValidStatus);
	}

	//changed visibility
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}
	
}
