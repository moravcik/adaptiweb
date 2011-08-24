package com.adaptiweb.utils.ci;

import java.io.File;


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
