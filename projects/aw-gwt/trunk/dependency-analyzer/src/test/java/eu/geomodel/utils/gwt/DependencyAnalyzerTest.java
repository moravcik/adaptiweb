package eu.geomodel.utils.gwt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DependencyAnalyzerTest extends TestCase {

	public DependencyAnalyzerTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DependencyAnalyzerTest.class);
	}

	public void testApp() {
		assertTrue(true);
	}
}
