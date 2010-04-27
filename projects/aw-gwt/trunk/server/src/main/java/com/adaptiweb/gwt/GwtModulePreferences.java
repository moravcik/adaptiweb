package com.adaptiweb.gwt;

import java.util.LinkedList;
import java.util.StringTokenizer;

import org.springframework.util.AntPathMatcher;

import com.adaptiweb.utils.commons.ArrayUtils;

public class GwtModulePreferences {
	
	private static final AntPathMatcher pathMatcher = new AntPathMatcher();

	String[] includes;
	String[] excludes;
	
	public void setIncludeList(String[] includes) {
		validatePathPatterns(includes);
		this.includes = this.includes == null ? includes : ArrayUtils.merge(this.includes, includes);
	}
	
	public void setIncludes(String includes) {
		setIncludeList(toArray(includes));
	}
	
	public void setExcludeList(String[] excludes) {
		validatePathPatterns(excludes);
		this.excludes = this.excludes == null ? excludes : ArrayUtils.merge(this.excludes, excludes);
	}

	public void setExcludes(String excludes) {
		setExcludeList(toArray(excludes));
	}
	
	private String[] toArray(String includes) {
		LinkedList<String> collect = new LinkedList<String>();
		StringTokenizer tokenizer = new StringTokenizer(includes);
		while (tokenizer.hasMoreTokens()) collect.add(tokenizer.nextToken());
		String[] array = collect.toArray(new String[collect.size()]);
		return array;
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
		if (includes != null)
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
