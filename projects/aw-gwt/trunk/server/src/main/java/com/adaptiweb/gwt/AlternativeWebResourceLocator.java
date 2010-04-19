package com.adaptiweb.gwt;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Deprecated
public class AlternativeWebResourceLocator implements MethodInterceptor {

	private String pahtPrefix;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result = invocation.proceed();
		if (result == null && pahtPrefix != null) {
			alterPath(invocation.getArguments());
			result = invocation.proceed();
		}
		return result;
	}

	private void alterPath(Object[] arguments) {
		arguments[0] = pahtPrefix + arguments[0];
	}

	public void setPahtPrefix(String pahtPrefix) {
		this.pahtPrefix = pahtPrefix;
	}

	public String getPahtPrefix() {
		return pahtPrefix;
	}
}
