package com.adaptiweb.gwt.common.tracker;



public class CustomVariable {
	protected Integer slot;
	protected String name;
	protected String value;
	protected TrackerScope scope;

	public CustomVariable(Integer slot, String name, String value) {
		this(slot, name, value, TrackerScope.Page);
	}

	public CustomVariable(Integer slot, String name, String value, TrackerScope scope) {
		this.slot = slot;
		this.name = name;
		this.value = value;
		this.scope = scope;
	}
	
	public Integer getSlot() {
		return slot;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public TrackerScope getScope() {
		return scope;
	}

}
