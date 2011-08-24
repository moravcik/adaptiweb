package com.adaptiweb.gwt;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adaptiweb.gwt.conf.UriRelatedConfiguration;

/**
 * This filter automatically add to URL a <b>gwt.codesvr</b> parameter if <code>Referer</code> (header) has same parameters.
 * 
 * This is easy way how to simply keep OOPHM while navigating true many pages.
 */
public class GwtCodesvrFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(GwtCodesvrFilter.class);

	private static final Pattern pattern = Pattern.compile("(\\?|&)gwt\\.codesvr=([^&]*)");

	private final UriRelatedConfiguration config = new UriRelatedConfiguration();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String referer = req.getHeader("Referer");
		Matcher m;

		if (referer != null
				&& req.getMethod().equalsIgnoreCase("GET")
				&& (m = pattern.matcher(referer)).find()
				&& req.getParameter("gwt.codesvr") == null
				&& config.matchesUri(req.getRequestURI().substring(req.getContextPath().length()))
		) {
			String gwtCodesvr = m.group(2);
			logger.info("gwt.codesvr=" + gwtCodesvr);
			StringBuffer url = req.getRequestURL().append('?');
			if (req.getQueryString() != null)
				url.append(req.getQueryString()).append('&');
			url.append("gwt.codesvr=").append(gwtCodesvr);
			((HttpServletResponse) response).sendRedirect(url.toString());
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String includes = filterConfig.getInitParameter("includes");
		if (includes != null) config.setIncludes(includes);
		String excludes = filterConfig.getInitParameter("excludes");
		if (excludes != null) config.setExcludes(excludes);
	}

	public void destroy() {}

}
