package com.adaptiweb.utils.livefile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class LiveFileHandler implements LiveFile {
	private File file;
	private long lastChecked;
	private long changesCheckPeriod;
	private long lastChanged;

	private List<FileLoader> fileLoaders = new ArrayList<FileLoader>();
	
	protected LiveFileHandler(File file, long checkPeriod) {
		this.file = file;
		this.changesCheckPeriod = checkPeriod;
	}
	
	@Override
	public void checkChanges(FileLoader loader) {
		if (fileLoaders.isEmpty()) refresh(Arrays.asList(loader));
		else {
			List<FileLoader> checkLoaders = new ArrayList<FileLoader>(fileLoaders);
			checkLoaders.add(loader);
			refresh(checkLoaders);
		}
	}

	@Override
	public void refresh() {
		refresh(fileLoaders);
	}	

	private void refresh(Collection<FileLoader> loaders) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChecked < changesCheckPeriod) return;
		lastChecked = currentTime;

		File file = getFile();
		long changedTime = file.lastModified();
		if (changedTime == lastChanged) return;
		lastChanged = changedTime;

		if (loaders != null) {
			for (FileLoader loader : loaders) { 
				try {
					loader.loadFile(file);
				} catch (IOException e) { throw new RuntimeException(e); }
			}
		}
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public void addFileLoader(FileLoader loader) {
		this.fileLoaders.add(loader);
	}
	
}
