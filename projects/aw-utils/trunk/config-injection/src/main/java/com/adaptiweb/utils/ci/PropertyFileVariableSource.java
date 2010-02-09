package com.adaptiweb.utils.ci;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.adaptiweb.utils.commons.InicializableVariableSource;
import com.adaptiweb.utils.commons.Properties;
import com.adaptiweb.utils.commons.VariableResolver;

public class PropertyFileVariableSource implements InicializableVariableSource {
	
	private Properties properties;
	private String expression;
	private File file;
	private long lastModified;
	private String template;
	
	public void setPropertyFileName(String expression) {
		this.expression = expression;
	}
	
	public void setPropertyFile(File file) {
		this.file = file;
	}
	
	public void setSystemResourceAsTemplate(String template) {
		this.template = template;
	}
	
	private void createDefaultPropertiesFile() throws IOException {
		InputStreamReader is = new InputStreamReader(ClassLoader.getSystemResourceAsStream(template), "UTF-8");
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			IOUtils.copy(is, os);
		} finally {
			IOUtils.closeQuietly(is);
			if (os != null) os.close();
		}
	}

	@Override
	public String getRawValue(String variableName) throws NullPointerException {
		return properties.getProperty(variableName);
	}

	public boolean checkModifications() throws IOException {
		if (file.lastModified() > lastModified) {
			loadProperties();
			return true;
		}
		return false;
	}

	@Override
	public void initSource(VariableResolver variables) throws IOException {
		assert (file == null) != (expression == null) : "Only one of properties {'propertyFile','propertyFileName'} must be used!";
		if (file == null) file = new File(variables.replaceVariables(expression));
		loadProperties();
	}
	
	private void loadProperties() throws IOException {
		checkFile();
		lastModified = file.lastModified();
		System.out.println("Loading configuration from " + file);
		properties = new Properties(file);
	}
	
	private void checkFile() throws IOException {
		if (!file.exists() && template != null) createDefaultPropertiesFile();
		if (!file.exists()) throw new IllegalStateException("File " + file + " not found!"); 
		if (!file.isFile()) throw new IllegalStateException("File " + file + " isn't regular file!"); 
		if (!file.canRead()) throw new IllegalStateException("File " + file + " can't read!"); 
	}

	public Map<String,String> extractDirectChildrenProperties(String prefix) {
		Map<String,String> result = new HashMap<String, String>();
		
		Properties subProperties = properties.select(prefix);
		for (String child : subProperties) {
			if (child.indexOf('.') == -1) {
				result.put(child, subProperties.getProperty(child));
			}
		}
		return result;
	}

}
