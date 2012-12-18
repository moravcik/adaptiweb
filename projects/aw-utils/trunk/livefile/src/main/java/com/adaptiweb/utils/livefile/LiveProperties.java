package com.adaptiweb.utils.livefile;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Set;

import com.adaptiweb.utils.spel.SpelEvaluator;
import com.adaptiweb.utils.spel.SpelProperties;

public class LiveProperties extends SpelProperties implements LiveFile, LiveFile.FileLoader {
	private static final long serialVersionUID = 6648035335266504847L;

	private LiveFile liveFile;

	protected LiveProperties(LiveFile liveFile, SpelEvaluator spelEvaluator, boolean spelRootContext) {
		super(spelEvaluator, spelRootContext);
		this.liveFile = liveFile;
	}
	
	@Override
	public synchronized String getProperty(String key) {
		liveFile.refresh();
		return super.getProperty(key);
	}

	@Override
	public synchronized Object get(Object key) {
		liveFile.refresh();
		return super.get(key);
	}
	
	@Override
	public synchronized boolean containsKey(Object key) {
		liveFile.refresh();
		return super.containsKey(key);
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
	public Set<Entry<Object, Object>> entrySet() {
		liveFile.refresh();
		return super.entrySet();
	}

	@Override
	public void loadFile(File file) throws IOException {
		loadProperties(file);
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
