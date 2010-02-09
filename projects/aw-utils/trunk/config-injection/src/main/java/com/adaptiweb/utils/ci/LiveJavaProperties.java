package com.adaptiweb.utils.ci;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.adaptiweb.utils.commons.Properties;
import com.adaptiweb.utils.commons.VariableResolver;

public class LiveJavaProperties extends java.util.Properties implements LiveFile.FileLoader {
	
	private VariableResolver variables;
	private LiveFile liveFile;
	
	public void setVariableResolver(VariableResolver variables) {
		this.variables = variables;
	}
	
	public void setLiveFile(LiveFile liveFile) {
		this.liveFile = liveFile;
	}
	
	@Override
	public String getProperty(String key) {
		liveFile.checkChanges(this);
		String result = super.getProperty(key);
		return variables == null ? result : variables.replaceVariables(result);
	}
	
	@Override
	public void loadFile(File file) throws IOException {
		replaceContent(file.exists() ? new Properties(file).toMap() : Collections.<String,String>emptyMap());
	}
	
	private synchronized void replaceContent(Map<String,String> values) {
		this.clear();
		this.putAll(values);
	}

}
