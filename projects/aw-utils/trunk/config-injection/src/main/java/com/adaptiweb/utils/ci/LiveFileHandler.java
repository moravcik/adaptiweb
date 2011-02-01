package com.adaptiweb.utils.ci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.adaptiweb.utils.commons.VariableResolver;

public class LiveFileHandler implements LiveFile {

	private static final String DEFAULT_CHECK_PERIOD_SYSTEM_PROPERTY = "liveFile_defaultCheckPeriod";
	private static final String DEVELOPMENT_MODE_SYSTEM_PROPERTY = "liveFile_developmentMode";
	private static final String CLASSPATH_URL = "classpath:";
	private VariableResolver variables;
	private File file;
	private String fileName;
	private long lastChecked;
	private long changesCheckPeriod = 2 * 60 * 1000; //default 2 minutes
	private long lastChanged;
	private String templateResource;
	private boolean developmentMode = false;

	public void setPropertyFile(String fileName) {
		this.fileName = fileName;
		this.file = null;

		String changesCheckPeriod = System.getProperty(DEFAULT_CHECK_PERIOD_SYSTEM_PROPERTY);
		if (changesCheckPeriod != null) setChangesCheckPeriod(Integer.parseInt(changesCheckPeriod));

		String developmentMode = System.getProperty(DEVELOPMENT_MODE_SYSTEM_PROPERTY);
		if (developmentMode != null) this.developmentMode = Boolean.parseBoolean(developmentMode);
	}

	/**
	 * Using standard Spring PropertyEditor to resolve Resource from string
	 * with possible use of prefixes: classpath: file: etc.
	 */
	public void setResource(Resource resource) throws IOException {
		setPropertyFile(resource.getFile().getPath());
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

	/**
	 * Using standard Spring PropertyEditor to resolve Resource from string
	 * with possible use of prefixes: classpath: file: etc.
	 */
	public void setTemplate(Resource templateResource) throws IOException {
		this.templateResource = templateResource.getFile().getPath();
	}
	
	public void checkChanges(FileLoader loader) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChecked < changesCheckPeriod) return;
		lastChecked = currentTime;

		File file = getFile();

		long changedTime = file.lastModified();
		if (changedTime == lastChanged) return;
		if (changedTime == 0 && templateResource != null && !file.exists())
			createDefaultFile(substituteVariables(templateResource), file);
		lastChanged = changedTime;

		try {
			loader.loadFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String substituteVariables(String string) {
		return variables == null ? string : variables.replaceVariables(string);
	}

	@PostConstruct
	public File getFile() {
		if (file == null) {
			if (fileName == null)
				throw new IllegalStateException("Attribute 'propertyFile' wasn't initialized!");

			file = new File(substituteVariables(fileName));

			if (developmentMode) prepareDevelopmentMode();

			if (!file.exists() && templateResource != null)
				createDefaultFile(substituteVariables(templateResource), file);
		}
		return file;
	}

	private void prepareDevelopmentMode() {
		if (templateResource == null)  return;
		File templateFile = resourceAsFile();
		if (templateFile != null && templateFile.exists()) {
			file = templateFile;
			templateFile = null;
		}
	}

	private File resourceAsFile() {
		try {
			return ResourceUtils.getFile(substituteVariables(templateResource));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static void createDefaultFile(String templateResource, File file) {
		InputStream is = null;
		OutputStream os = null;
		try {
			FileUtils.forceMkdir(file.getParentFile());

			if (templateResource.startsWith(CLASSPATH_URL)) {
				templateResource = templateResource.substring(CLASSPATH_URL.length());
				is = new ClassPathResource(templateResource).getInputStream();
				os = new FileOutputStream(file);
				IOUtils.copy(is, os);
			} else {
				FileUtils.copyFile(ResourceUtils.getFile(templateResource), file, false);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}
}
