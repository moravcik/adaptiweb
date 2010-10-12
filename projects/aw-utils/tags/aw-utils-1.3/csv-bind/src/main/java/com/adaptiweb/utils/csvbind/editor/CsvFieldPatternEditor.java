package com.adaptiweb.utils.csvbind.editor;

import java.beans.PropertyEditor;

public interface CsvFieldPatternEditor extends PropertyEditor {

	public void setPatterns(String[] patterns);
	
}
