package com.adaptiweb.gwt.common.tracker;

public enum TrackerScope {
	Visitor(1),
	Session(2),
	Page(3); // default
	
	int value;

	private TrackerScope(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
