package com.adaptiweb.gwt.common;

public class DefaultListBoxItem implements ListBoxItem {

	private String label;
	private String value;

	public DefaultListBoxItem(String label, String value) {
		this.label = label;
		this.value = value;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public String value() {
		return value;
	}
}
