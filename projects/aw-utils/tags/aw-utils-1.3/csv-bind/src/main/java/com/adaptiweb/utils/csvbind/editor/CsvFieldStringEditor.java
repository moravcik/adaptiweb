package com.adaptiweb.utils.csvbind.editor;

import java.beans.PropertyEditorSupport;

/**
 * Custom implementation of StringEditor because sun.beans.editors.StringEditor is restricted
 * NOTE: Needed only for list csv fields
 */
public class CsvFieldStringEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text);
	}
	
}
