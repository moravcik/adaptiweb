package com.adaptiweb.utils.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

	public static final Map<String, String> select(Properties props, String prefix) {
		if (!prefix.endsWith(".")) prefix += ".";
		Map<String, String> selected = new HashMap<String, String>();
		for (String key : props.stringPropertyNames()) {
			if (key.startsWith(prefix)) {
				selected.put(key.substring(prefix.length()), props.getProperty(key));
			}
		}
		return selected;
	}

}
