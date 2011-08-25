package com.adaptiweb.utils.livefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.adaptiweb.utils.spel.SpelEvaluator;

public class LiveProperties extends Properties implements LiveFile, LiveFile.FileLoader {
	private static final long serialVersionUID = 6648035335266504847L;

	private LiveFile liveFile;
	private SpelEvaluator spelEvaluator;
	private boolean spelRootContext;

	protected LiveProperties(LiveFile liveFile, SpelEvaluator spelEvaluator, boolean spelRootContext) {
		this.liveFile = liveFile;
		this.spelEvaluator = spelEvaluator;
		this.spelRootContext = spelRootContext;
	}
	
	@Override
	public synchronized String getProperty(String key) {
		liveFile.refresh();
		return evaluateResult(super.getProperty(key));
	}

	@Override
	public synchronized Object get(Object key) {
		liveFile.refresh();
		return evaluateResult((String) super.get(key));
	}
	
	@Override
	public synchronized boolean containsKey(Object key) {
		liveFile.refresh();
		return super.containsKey(key);
	}
	
	private String evaluateResult(String originalResult) {
		if (spelEvaluator != null && originalResult != null) {
			if (spelRootContext) spelEvaluator.setRootObject(this);
			return spelEvaluator.setExpression(originalResult).evaluate(String.class);
		} else return originalResult;
	}
	
	@Override
	public Enumeration<?> propertyNames() {
		liveFile.refresh();
		return super.propertyNames();
	}
	
	@Override
	public Set<String> stringPropertyNames() {
		liveFile.refresh();
		return super.stringPropertyNames();
	}
	
	@Override
	public void loadFile(File file) throws IOException {
		Properties loadProperties = new Properties();
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			loadProperties.load(fis);
			for (String name : loadProperties.stringPropertyNames())
				this.put(name, loadProperties.getProperty(name));
			IOUtils.closeQuietly(fis);
		};
	}

	// livefile wrappers
	
	@Override
	public void checkChanges(FileLoader loader) {
		liveFile.checkChanges(loader);
	}

	@Override
	public void addFileLoader(FileLoader loader) {
		liveFile.addFileLoader(loader);
	}
	
	@Override
	public void refresh() {
		liveFile.refresh();
	}

	@Override
	public File getFile() {
		return liveFile.getFile();
	}
	
	
}
