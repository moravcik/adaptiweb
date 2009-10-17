package com.adaptiweb.tools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;


public final class JndiUtils {
	
	private JndiUtils() {}

	public static void unbind(String jndiName) {
		try {
			InitialContext initialContext = new InitialContext();
			initialContext.unbind(jndiName);
		} catch (NoInitialContextException ignore) {
			// TODO: handle exception
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void bind(String jndiName, Object value) {
		try {
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

			InitialContext initialContext = new InitialContext();

			for (int start = 0, index; (index = jndiName.indexOf('/', start)) != -1; start = index + 1) {
				try {
					initialContext.createSubcontext(jndiName.substring(0, index));
				} catch (NameAlreadyBoundException ignore) {}
			}

			initialContext.bind(jndiName, value);
		} catch (NameAlreadyBoundException ignore) {
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
