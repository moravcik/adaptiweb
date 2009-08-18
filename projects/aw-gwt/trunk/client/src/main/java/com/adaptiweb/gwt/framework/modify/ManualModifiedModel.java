package com.adaptiweb.gwt.framework.modify;

public class ManualModifiedModel extends BaseModifiedModel {

	public ManualModifiedModel() {
		super();
	}

	public ManualModifiedModel(boolean initChangedStatus) {
		super(initChangedStatus);
	}

	//changed visibility
	@Override
	public void setModified(boolean modified) {
		super.setModified(modified);
	}
}
