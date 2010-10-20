package com.adaptiweb.server.servlet;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DefaultRequestDispatcherFilterTest {

	static class Tester {
		@Mock FilterConfig config;
		@Mock ServletContext context;
		@Mock RequestDispatcher dispatcher;
		int forwardCounter = 0;
		final DefaultRequestDispatcherFilter testedObject = new DefaultRequestDispatcherFilter();

		Tester(String includes, String excludes) throws ServletException {
			MockitoAnnotations.initMocks(this);
			stub(config.getInitParameter("includes")).toReturn(includes);
			stub(config.getInitParameter("excludes")).toReturn(excludes);
			stub(config.getServletContext()).toReturn(context);
			stub(context.getNamedDispatcher("default")).toReturn(dispatcher);
			testedObject.init(config);
		}

		public void assertFowradToDefault(String uri, String contextPath) throws IOException, ServletException {
			assertActive(true, uri, contextPath);
		}

		public void assertAppliedToChain(String uri, String contextPath) throws IOException, ServletException {
			assertActive(false, uri, contextPath);
		}

		private void assertActive(boolean active, String uri, String contextPath) throws IOException, ServletException {
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpServletResponse response = mock(HttpServletResponse.class);
			FilterChain chain = mock(FilterChain.class);

			stub(request.getRequestURI()).toReturn(uri);
			stub(request.getContextPath()).toReturn(contextPath);

			testedObject.doFilter(request, response, chain);

			verify(request).getRequestURI();
			verify(request).getContextPath();
			verify(chain, active ? never() : times(1)).doFilter(request, response);
			verify(dispatcher, active ? times(1) : never()).forward(request, response);
			if (active) forwardCounter++;

			verifyNoMoreInteractions(request, response, chain);
		}

		public void destroy() throws ServletException, IOException {
			testedObject.destroy();
			verify(dispatcher, times(forwardCounter)).forward(
					Matchers.<ServletRequest>anyObject(),
					Matchers.<ServletResponse>anyObject());
			verifyNoMoreInteractions(dispatcher);
		}
	}

	@Test
	public void testNormal() throws ServletException, IOException {
		String includes = " /images/**/* \n /**/*.gif ";
		String excludes = "/dynamic/ahoj.gif\n";
		Tester tester = new Tester(includes, excludes);

		tester.assertFowradToDefault("/app/ahoj.gif", "/app");
		tester.assertFowradToDefault("/app/images/ahoj.png", "/app");
		tester.assertFowradToDefault("/images/ahoj.png", "");
		tester.assertAppliedToChain("/app/dynamic/ahoj.gif", "/app");

		tester.destroy();
	}

	@Test
	public void testWithoutExcludes() throws IOException, ServletException {
		String includes = " /images/**/* \n /**/*.gif ";
		String excludes = null;
		Tester tester = new Tester(includes, excludes);

		tester.assertFowradToDefault("/app/ahoj.gif", "/app");
		tester.assertFowradToDefault("/app/images/ahoj.png", "/app");
		tester.assertFowradToDefault("/images/ahoj.png", "");
		tester.assertAppliedToChain("/app/dynamic/ahoj.png", "/app");

		tester.destroy();
	}

	@Test
	public void testWithEmptyExcludes() throws IOException, ServletException {
		String includes = " /images/**/* \n /**/*.gif ";
		String excludes = "";
		Tester tester = new Tester(includes, excludes);

		tester.assertFowradToDefault("/app/ahoj.gif", "/app");
		tester.assertFowradToDefault("/app/images/ahoj.png", "/app");
		tester.assertFowradToDefault("/images/ahoj.png", "");
		tester.assertAppliedToChain("/app/dynamic/ahoj.png", "/app");

		tester.destroy();
	}

	/**
	 * Without property "includes" means all is included
	 */
	@Test
	public void testWithoudIncludes() throws IOException, ServletException {
		String includes = null;
		String excludes = "/**/*.png";
		Tester tester = new Tester(includes, excludes);

		tester.assertFowradToDefault("/app/ahoj.gif", "/app");
		tester.assertAppliedToChain("/app/images/ahoj.png", "/app");
		tester.assertFowradToDefault("/images/ahoj.gif", "");
		tester.assertAppliedToChain("/app/dynamic/ahoj.png", "/app");

		tester.destroy();
	}

	/**
	 * Empty property "includes" means nothing is included
	 */
	@Test
	public void testWithEmptyIncludes() throws IOException, ServletException {
		String includes = "";
		String excludes = "/**/*.png";
		Tester tester = new Tester(includes, excludes);

		tester.assertAppliedToChain("/app/ahoj.gif", "/app");
		tester.assertAppliedToChain("/app/images/ahoj.png", "/app");
		tester.assertAppliedToChain("/images/ahoj.gif", "");
		tester.assertAppliedToChain("/app/dynamic/ahoj.png", "/app");

		tester.destroy();
	}

	/**
	 * All patterns must start with '/' otherwise they cannot match input.
	 */
	@Test
	public void testFixingPattern() throws ServletException, IOException {
		String includes = " images/**/* \n **/*.gif ";
		String excludes = "dynamic/*.gif\n";
		Tester tester = new Tester(includes, excludes);

		tester.assertFowradToDefault("/app/ahoj.gif", "/app");
		tester.assertFowradToDefault("/app/images/ahoj.png", "/app");
		tester.assertFowradToDefault("/images/ahoj.png", "");
		tester.assertAppliedToChain("/app/dynamic/ahoj.gif", "/app");

		tester.destroy();
	}
}
