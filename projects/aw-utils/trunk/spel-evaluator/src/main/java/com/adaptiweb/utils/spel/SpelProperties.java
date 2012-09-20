package com.adaptiweb.utils.spel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.adaptiweb.utils.spel.SpelEvaluator;

public class SpelProperties extends Properties {
	private static final long serialVersionUID = 961252785514837257L;
	
	private SpelEvaluator spelEvaluator;
	private boolean spelRootContext;

	protected SpelProperties(SpelEvaluator spelEvaluator, boolean spelRootContext) {
		this.spelEvaluator = spelEvaluator;
		this.spelRootContext = spelRootContext;
	}
	
	protected void loadProperties(File file) throws IOException {
		Properties loadProperties = new Properties();
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			loadProperties.load(fis);
			loadProperties(loadProperties);
			IOUtils.closeQuietly(fis);
		};
	}
	
	protected void loadProperties(Properties props) {
		for (String name : props.stringPropertyNames())
			this.put(name, props.getProperty(name));
	}

	@Override
	public String getProperty(String key) {
		return evaluateResult(super.getProperty(key));
	}

	@Override
	public Object get(Object key) {
		return evaluateResult((String) super.get(key));
	}
	
	private String evaluateResult(String originalResult) {
		if (spelEvaluator != null && originalResult != null) {
			if (spelRootContext) spelEvaluator.setRootObject(this);
			return spelEvaluator.setExpression(originalResult).evaluate(String.class);
		} else return originalResult;
	}
	
}
