package com.adaptiweb.utils.ci;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import com.adaptiweb.utils.commons.Properties;
import com.adaptiweb.utils.commons.VariableResolver;

public class LiveJavaProperties extends java.util.Properties {
	
	private VariableResolver variables;
	private String fileName;
	private File propertyFile;
	private long lastChanged;
	private long lastChecked;
	private long changesCheckPeriod = 2 * 60 * 1000; //default 2 minutes
	private String templateResource;
	
	public void setPropertyFile(String fileName) {
		this.fileName = fileName;
		this.propertyFile = null;
	}
	
	/**
	 * Enables substituting variables in values. Optional.
	 */
	public void setVariables(VariableResolver variables) {
		this.variables = variables;
	}
	
	/**
	 * Minimal change checking period in seconds.
	 * Optional, default is 2 minutes.
	 */
	public void setChangesCheckPeriod(int changesCheckPeriod) {
		this.changesCheckPeriod = changesCheckPeriod * 1000L;
	}
	
	/**
	 * Enable auto creating missing file during initialization. Optional.
	 */
	public void setTemplateResource(String templateResource) {
		this.templateResource = templateResource;
	}
	
	@Override
	public String getProperty(String key) {
		checkChanges();
		return substituteVariables(super.getProperty(key));
	}
	
	@PostConstruct
	private void checkChanges() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChecked < changesCheckPeriod) return;
		lastChecked = currentTime;

		long changedTime = getPropertyFile().lastModified();
		if (changedTime == lastChanged) return;
		lastChanged = changedTime;
		
		replaceContent(new Properties(getPropertyFile()).toMap());
	}

	private File getPropertyFile() {
		if (propertyFile == null) {
			if (fileName == null)
				throw new IllegalStateException("Attribute 'propertyFile' wasn't initialized!");
			
			propertyFile = new File(substituteVariables(fileName));
			
			if (!propertyFile.exists() && templateResource != null)
				createDefaultFile(substituteVariables(templateResource), propertyFile);
		}
		return propertyFile;
	}

	private String substituteVariables(String string) {
		return variables == null ? string : variables.replaceVariables(string);
	}

	private static void createDefaultFile(String templateResource, File file) {
		try {
			File template = ResourceUtils.getFile(templateResource);
			FileUtils.forceMkdir(file.getParentFile());
			FileUtils.copyFile(template, file, false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized void replaceContent(Map<String,String> values) {
		this.clear();
		this.putAll(values);
	}
}
