package com.adaptiweb.utils.csvbind.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvFieldDateEditor extends PropertyEditorSupport implements CsvFieldPatternEditor {
	private List<SimpleDateFormat> formatList = new ArrayList<SimpleDateFormat>();
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null && text.trim().length() > 0 && false == formatList.isEmpty()) {
			Date parsedDate = null;
			for (SimpleDateFormat format : formatList) {
				try {
					parsedDate = format.parse(text);
					break;
				} catch (ParseException e) {
					// do nothing, date as null
				}
			}
			setValue(parsedDate);
		} else {
			setValue(null);
		}
	}
	
	@Override
	public String getAsText() {
		if (getValue() != null && getValue() instanceof Date && false == formatList.isEmpty()) {
			return formatList.get(0).format((Date) getValue());
		} else if (getValue() != null) {
			return getValue().toString();
		} else {
			return null;
		}
	}

	/**
	 * TODO comment - pattern = SimpleDateFormat 
	 */
	public void setPatterns(String[] patterns) {
		formatList.clear();
		for (String pattern : patterns) {
			formatList.add(new SimpleDateFormat(pattern));
		}
		formatList.add(new SimpleDateFormat()); // add format for default locale
	}
}
