package com.adaptiweb.gwt.conf;

import java.util.StringTokenizer;

import org.springframework.util.AntPathMatcher;

import com.adaptiweb.utils.commons.ArrayUtils;
import com.adaptiweb.utils.commons.VariableResolver;

public class UriRelatedConfiguration {

	private static final AntPathMatcher pathMatcher = new AntPathMatcher();

	private String[] includes;
	private String[] excludes;

	public UriRelatedConfiguration() {}

	public UriRelatedConfiguration(VariableResolver variables, String variableBaseName) {
		setIncludes(variables.resolveValue(variableBaseName + ".includes", ""));
		setExcludes(variables.resolveValue(variableBaseName + ".excludes", ""));
	}

	public void setIncludeList(String[] includes) {
		validatePathPatterns(includes);
		this.includes = this.includes == null ? includes : ArrayUtils.merge(this.includes, includes);
	}

	public void setIncludes(String includes) {
		setIncludeList(split(includes));
	}

	public void setExcludeList(String[] excludes) {
		validatePathPatterns(excludes);
		this.excludes = this.excludes == null ? excludes : ArrayUtils.merge(this.excludes, excludes);
	}

	public void setExcludes(String excludes) {
		setExcludeList(split(excludes));
	}

	private static String[] split(String patterns) {
		StringTokenizer tokenizer = new StringTokenizer(patterns);
		String[] result = new String[tokenizer.countTokens()];
		int counter = 0;

		while (tokenizer.hasMoreTokens())
			result[counter++] = tokenizer.nextToken();

		return result;
	}

	private static String[] validatePathPatterns(String[] paths) {
		for (int i = 0; i < paths.length; i++)
			if (!paths[i].startsWith("/"))
				paths[i] = "/" + paths[i];
		return paths;
	}

	public boolean matchesUri(String requestUri) {
		return includes(requestUri) && !excludes(requestUri);
	}

	private boolean includes(String requestUri) {
		if (includes == null) return true;
		for (String include : includes)
			if (pathMatcher.match(include, requestUri)) return true;
		return false;
	}

	private boolean excludes(String requestUri) {
		if (excludes != null)
			for (String exclude : excludes)
				if (pathMatcher.match(exclude, requestUri)) return true;
		return false;
	}
}
