package com.adaptiweb.utils.ci;

import java.io.File;

import com.adaptiweb.utils.commons.VariableResolver;

class DefaultFilePropertyConverter implements PropertyConverter<File> {

	@Override
	public File convert(VariableResolver variables, String name, String defaultValue) {
		return new File(variables.resolveValue(name, defaultValue));
	}

	@Override
	public Class<File> getType() {
		return File.class;
	}

}
