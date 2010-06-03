package com.adaptiweb.utils.csvbind.editor;

import java.beans.PropertyEditorSupport;

public class CsvFieldEnumEditor<T extends Enum<T>> extends PropertyEditorSupport {
	
	private Class<T> enumType;
	
	protected CsvFieldEnumEditor(Class<T> enumType) {
		this.enumType = enumType;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		return getValue() != null ? ((T) getValue()).name() : null;
	}
	@Override
	public void setAsText(String arg) throws IllegalArgumentException {
		try {
			setValue(Enum.valueOf(enumType, arg));
		} catch (IllegalArgumentException e) {
			setValue(null);
		} catch (NullPointerException e) {
			setValue(null);
		}
	}
	
}