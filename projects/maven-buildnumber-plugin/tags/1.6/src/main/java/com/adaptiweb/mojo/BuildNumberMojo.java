package com.adaptiweb.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.SelectorUtils;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;

/**
 * Goal which provide some dynamic information in build time thru project properties.
 * <br>Right now are supported these four properties:<ol>
 * <li><b>{@link #buildNumberProperty}</b></li>
 * <li><b>{@link #timestampProperty}</b></li>
 * <li><b>{@link #revisionNumberProperty}</b></li>
 * <li><b>{@link #lastCommittedRevisionProperty}</b></li>
 * </ol>
 * 
 * @goal create
 * @phase validate
 * @requiresProject
 */
public class BuildNumberMojo extends AbstractMojo {
	private static final String[] DEFAULT_EXCLUDES = { "**/.svn", "**/.svn/**", "**/*~" };

	/**
	 * Working copy of project resources managed by Subverion. Default ${basedir}. 
	 * 
	 * @parameter expression="${basedir}"
	 */
	private File basedir;
	
	/**
	 * Name of property for build number. Default 'buildNumber'.
	 * 
	 * @parameter default-value=""
	 */
	private String buildNumberProperty;

	/**
	 * Name of property for timestamp.
	 * No property name is set by default.
	 * Value of property is formated by {@link #timestampFormat} parameter.
	 * @parameter default-value=""
	 */
	private String timestampProperty;

	/**
	 * Name of property for revision number.
	 * No property name is set by default.
	 * This parameter is ignored if project isn't under subversion control.
	 * 
	 * @parameter default-value=""
	 */
	private String revisionNumberProperty;
	
	/**
	 * Name of property for last commited revision number.
	 * No property name is set by default.
	 * This parameter is ignored if project isn't under subversion control.
	 * 
	 * @parameter default-value=""
	 */
	private String lastCommittedRevisionProperty;
	
	/**
	 * Properties are sets even they already exists. Default {@code false}.
	 * 
	 * @parameter default-value="false"
	 */
	private boolean overwriteProperties;
	
	/**
	 * Format for timestamp property. Default 'Y-m-d H:M:S'.
	 * 
	 * @parameter default-value="Y-m-d H:M:S"
	 */
	private String timestampFormat;
		
	/**
	 * The maven project properties. For exporting.
	 * 
	 * @parameter expression="${project.properties}"
	 * @required
	 * @readonly
	 */
	private Properties projectProperties;
	
	/**
	 * The maven project version. Used for build number property.
	 * 
	 * @parameter expression="${project.version}"
	 * @required
	 * @readonly
	 */
	private String projectVersion;
	
	/**
	 * If {@code true} (default) unversioned resources are ignored
	 * while checking SVN status. 
	 * 
	 * @parameter default-value="true"
	 */
	private boolean ignoreUnversioned;

	/**
	 * List of patterns to match resources includes for SVN checking.
	 * 
	 * @parameter
	 */
	private String[] includes;

	/**
	 * List of patterns to match resources excludes from SVN checking.
	 * 
	 * @parameter
	 */
	private String[] excludes;

	/**
	 * Check if files in working copy are up to date and commited.
	 * @parameter expression="${maven.buildNumber.doCheck}" default-value="true"
	 */
	private boolean doCheck;
	
	/**
	 * Project build never fails, even if SVN check find some problems.
	 * @parameter expression="${neverFail}" default-value="true"
	 */
	private boolean neverFail;

	/**
	 * All exported properties for information log message.
	 */
	private Map<String,String> exportedProperties = new HashMap<String, String>();
	
	/**
	 * Value for timestamp initialized by {@link System#currentTimeMillis()}.
	 */
	private long now;

	/**
	 * API for working with subversion data.
	 */
	private SVNClientManager svnManager;

	/**
	 * Subversion info data for {@link #basedir}.
	 */
	private SVNInfo svnInfo;

	/**
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		now = System.currentTimeMillis();
		
		if(isPropertyWanted(buildNumberProperty))
			doExportProperty(buildNumberProperty, getVersion());
		if(isPropertyWanted(timestampProperty))
			doExportProperty(timestampProperty, getTimeStamp(timestampFormat));
		if(isWorkingCopy()) {
			if(doCheck && !doCheck() && !neverFail)
				throw new MojoExecutionException("Checking working copy fails!");
			if(isPropertyWanted(revisionNumberProperty))
				doExportProperty(revisionNumberProperty, String.valueOf(getSVNInfo().getRevision()));
			if(isPropertyWanted(lastCommittedRevisionProperty))
				doExportProperty(lastCommittedRevisionProperty, String.valueOf(getSVNInfo().getCommittedRevision()));
		}
				
		getLog().info("Exported properties: " + exportedProperties);
	}

	private boolean doCheck() throws MojoExecutionException {
		correctPathPatterns(DEFAULT_EXCLUDES);
		if(includes != null) correctPathPatterns(includes);
		if(excludes != null) correctPathPatterns(excludes);
		return checkFile(basedir, "");
	}
	
	private void correctPathPatterns(String[] patterns) {
		for(int i = 0; i < patterns.length; i++) {
			patterns[i] = patterns[i]
			                       .replace('/', File.separatorChar)
			                       .replace('\\', File.separatorChar);
			if(patterns[i].endsWith(File.separator))
				patterns[i] += "**";
		}
	}

	private boolean checkFile(File file, String matchPrefix) throws MojoExecutionException {
		SVNStatus status = getSVNStatus(file);
		SVNStatusType statusType = status.getContentsStatus();

		boolean checked = true;
		
		if(!isStatusOk(statusType)) {
			reportStatus(statusType, toProjectBasePath(file));
			checked = false;
		}

		if(file.isDirectory() && shouldSinkDeeper(statusType))
			for(String f : file.list())
				if(isIncluded(matchPrefix + f))
					checked &= checkFile(new File(file, f), matchPrefix + f + "/");

		return checked;
	}

	private boolean isIncluded(String path) {
		for(String exclude : DEFAULT_EXCLUDES)
			if(SelectorUtils.match(exclude, path))
				return false;
		
		if(includes != null) {
			boolean included = false;
			for(String include : includes) { 
				if(SelectorUtils.match(include, path)) {
					included = true;
					break;
				}
			}
			if(!included) return false;
		}
		
		if(excludes != null)
			for(String exclude : excludes)
				if(SelectorUtils.match(exclude, path))
					return false;
		
		return true;
	}

	private void reportStatus(SVNStatusType status, String path) {
		getLog().warn(String.format("%12s: %s", status, path));
	}

	private String toProjectBasePath(File file) {
		return file.getAbsolutePath().substring(basedir.getAbsolutePath().length());
	}

	private boolean shouldSinkDeeper(SVNStatusType status) {
		return status != SVNStatusType.STATUS_IGNORED
			&& status != SVNStatusType.STATUS_UNVERSIONED;
	}

	private boolean isStatusOk(SVNStatusType status) {
		return status == SVNStatusType.STATUS_NONE
			|| status == SVNStatusType.STATUS_NORMAL
			|| status == SVNStatusType.STATUS_IGNORED
			|| status == SVNStatusType.STATUS_UNVERSIONED && ignoreUnversioned;
	}

	private SVNInfo getSVNInfo() throws MojoExecutionException {
		if(svnInfo == null)
			try {
				svnInfo = getSVNManager().getWCClient().doInfo(basedir, SVNRevision.BASE);
			} catch (SVNException e) {
				throw new MojoExecutionException(e.getMessage(), e);
			}
		return svnInfo;
	}

	private SVNStatus getSVNStatus(File file) throws MojoExecutionException {
		try {
			return getSVNManager().getStatusClient().doStatus(file, false);
		} catch (SVNException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private SVNClientManager getSVNManager() {
		if(svnManager == null)
			svnManager = SVNClientManager.newInstance();
		return svnManager;
	}

	private boolean isPropertyWanted(String propertyName) {
		return propertyName != null
			&& propertyName.length() > 0
			&& (overwriteProperties || !existProperty(propertyName));
	}

	private boolean existProperty(String propertyName) {
		return projectProperties.containsKey(propertyName) || System.getProperty(propertyName) != null;
	}
	
	private void doExportProperty(String propertyName, String propertyValue) {
		if(propertyValue != null) {
			projectProperties.put(propertyName, propertyValue);
			exportedProperties.put(propertyName, propertyValue);
		}
	}

	private String getVersion() {
		String version = projectVersion;
	
		if(version.contains("SNAPSHOT"))
			version = version.replace("SNAPSHOT", getTimeStamp("YmdHMS"));
		
		return version;
	}

	private String getTimeStamp(String format) {
		StringBuffer result = new StringBuffer(format.length() * 5);
		
		for(char c : format.toCharArray()) {
			if(Character.isLetter(c))
				result.append("%1$t");
			result.append(c);
		}
		return String.format(result.toString(), now);
	}
	
	private boolean isWorkingCopy() {
		return new File(basedir, ".svn").exists();
	}
}
