package com.adaptiweb.utils.ci;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class LiveJavaProperties extends VariableProperties implements LiveFile.FileLoader {
	
	private LiveFile liveFile;
	
	public void setLiveFile(LiveFile liveFile) {
		this.liveFile = liveFile;
	}
	
	@Override
	public String getProperty(String key) {
		liveFile.checkChanges(this);
		return super.getProperty(key);
	}

	@Override
	public void loadFile(File file) throws IOException {
		loadFromFile(file);
	}
	
}
