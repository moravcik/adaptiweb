package com.adaptiweb.gwt.conf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.adaptiweb.utils.ci.VariableResolver;
import com.adaptiweb.utils.ci.VariableSource;



public class GoogleMapsApiKeyVariableSourceTest {

	@Mock VariableSource variableSource;
	@Mock HttpServletRequest servletRequest;
	@Mock RequestAttributes requestAttributes;
	private VariableResolver variableResolver;

	public GoogleMapsApiKeyVariableSource initializedGoogleMapsApiKeyVariableSource() throws IOException, NoSuchFieldException {
		GoogleMapsApiKeyVariableSource result = new GoogleMapsApiKeyVariableSource();
		MockitoAnnotations.initMocks(this);

		assertNotNull(variableSource);
		assertNotNull(servletRequest);
		assertNotNull(requestAttributes);

		result.initSource(variableResolver = new VariableResolver(variableSource));
		result.request = servletRequest;
		RequestContextHolder.setRequestAttributes(requestAttributes);
		return result;
	}

	@After
	public void releseMock() {
		if (variableResolver != null) {
			verifyNoMoreInteractions(variableSource, servletRequest, requestAttributes);
			RequestContextHolder.resetRequestAttributes();
			variableResolver = null;
			variableSource = null;
			servletRequest = null;
			requestAttributes = null;
		}
	}

	@Test
	public void testDefaultVariableName() {
		String defaultVariableName = new GoogleMapsApiKeyVariableSource().getVariableName();
		assertEquals("Default value should not be changed!", "googleMaps.apiKey", defaultVariableName);
	}

	@Test
	public void testCallingOutRequestScope() throws IOException, NoSuchFieldException {
		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();
		RequestContextHolder.resetRequestAttributes();
		assertNull(tested.getRawValue(tested.getVariableName()));
	}

	@Test
	public void testUnavailableKey() throws IOException, NoSuchFieldException {
		String servlerName = "adaptiweb.com";

		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();
		stub(servletRequest.getServerName()).toReturn(servlerName);

		assertEquals("", tested.getRawValue(tested.getVariableName()));

		verify(variableSource).getRawValue(tested.getVariableName() + "." + servlerName);
		verify(servletRequest).getServerName();
	}

	@Test
	public void testAvailableKey() throws IOException, NoSuchFieldException {
		String servlerName = "adaptiweb.com";
		String key = "ANYRANDOM_STRING";

		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();

		String targetVariableName = tested.getVariableName() + "." + servlerName;
		stub(servletRequest.getServerName()).toReturn(servlerName);
		stub(variableSource.getRawValue(targetVariableName)).toReturn(key);

		assertSame(key, tested.getRawValue(tested.getVariableName()));

		verify(variableSource).getRawValue(targetVariableName);
		verify(servletRequest).getServerName();
	}

	@Test
	public void testSimpleServerName() throws IOException, NoSuchFieldException {
		String servlerName = "adaptiweb";

		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();
		stub(servletRequest.getServerName()).toReturn(servlerName);

		assertEquals("", tested.getRawValue(tested.getVariableName()));

		verify(variableSource).getRawValue(tested.getVariableName() + "." + servlerName);
		verify(servletRequest).getServerName();
	}

	@Test
	public void testThreeLevelServerName() throws IOException, NoSuchFieldException {
		String prefix = "www.";
		String servlerName = "adaptiweb.com";

		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();
		stub(servletRequest.getServerName()).toReturn(prefix + servlerName);

		assertEquals("", tested.getRawValue(tested.getVariableName()));

		verify(variableSource).getRawValue(tested.getVariableName() + "." + servlerName);
		verify(servletRequest).getServerName();
	}

	@Test
	public void testIpAsServerName() throws IOException, NoSuchFieldException {
		String servlerName = "10.20.30.215";

		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();
		stub(servletRequest.getServerName()).toReturn(servlerName);

		assertEquals("", tested.getRawValue(tested.getVariableName()));

		verify(variableSource).getRawValue(tested.getVariableName() + "." + servlerName);
		verify(servletRequest).getServerName();
	}

	@Test
	public void testCustomVariableName() throws IOException, NoSuchFieldException {
		String customVariableName = "custom.variableName";
		String servlerName = "adaptiweb.com";

		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();
		tested.setVariableName(customVariableName);
		stub(servletRequest.getServerName()).toReturn(servlerName);

		assertEquals("", tested.getRawValue(tested.getVariableName()));

		verify(variableSource).getRawValue(customVariableName + "." + servlerName);
		verify(servletRequest).getServerName();
	}

	@Test
	public void testIgnoringOthersVariables() throws IOException, NoSuchFieldException {
		GoogleMapsApiKeyVariableSource tested = initializedGoogleMapsApiKeyVariableSource();

		String originalVariableName = tested.getVariableName();
		String customVariableName = "custom.variableName";

		tested.setVariableName(customVariableName);

		assertNull(tested.getRawValue(originalVariableName));
		assertNull(tested.getRawValue(customVariableName + "x"));
	}
}
