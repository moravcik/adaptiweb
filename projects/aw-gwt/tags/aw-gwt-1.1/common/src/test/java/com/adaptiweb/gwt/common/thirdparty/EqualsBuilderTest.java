package com.adaptiweb.gwt.common.thirdparty;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing both EqualsBuilder and HashCodeBuilder
 */
public class EqualsBuilderTest {

	private static class TestCompound {
		String someString;
		Integer someInteger;
		Double someDouble;
		BigDecimal someBigDecimal;
		Float[] someArray;
		
		TestCompound(String someString, Integer someInteger,
				Double someDouble, BigDecimal someBigDecimal, Float[] someArray) {
			this.someString = someString;
			this.someInteger = someInteger;
			this.someDouble = someDouble;
			this.someBigDecimal = someBigDecimal;
			this.someArray = someArray;
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder()
				.append(someString)
				.append(someInteger)
				.append(someDouble)
				.append(someBigDecimal)
				.append(someArray)
				.toHashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof TestCompound)) return false;
			TestCompound other = (TestCompound) obj;
			return new EqualsBuilder()
				.append(someString, other.someString)
				.append(someInteger, other.someInteger)
				.append(someDouble, other.someDouble)
				.append(someBigDecimal, other.someBigDecimal)
				.append(someArray, other.someArray)
				.isEquals();
		}
	}
	
	private Set<TestCompound> testedSet;
	
	@Before
	public void setup() {
		testedSet = new HashSet<TestCompound>();
		testedSet.add(new TestCompound("1", 1, 1D, new BigDecimal(1), new Float[] {1F, 2F}));
		testedSet.add(new TestCompound("1", 1, 1D, new BigDecimal(1), new Float[] {1F, 2F})); // duplicate
		testedSet.add(new TestCompound("2", 2, 2D, new BigDecimal(2), new Float[] {1F}));
		testedSet.add(new TestCompound("22", 22, 22D, new BigDecimal(22), new Float[] {1F}));
		testedSet.add(new TestCompound("22", 22, 22D, new BigDecimal(223), new Float[] {1F}));
		testedSet.add(new TestCompound("22", 22, 222D, new BigDecimal(22), new Float[] {1F}));
		testedSet.add(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F}));
		testedSet.add(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F, 3F}));
		testedSet.add(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F, 4F}));
		testedSet.add(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {}));
		testedSet.add(new TestCompound("22", 222, 22D, new BigDecimal(22), null));
		Assert.assertTrue(testedSet.size() == 10);
	}
	
	@Test
	public void test() {
		// contains
		Assert.assertTrue(testedSet.contains(new TestCompound("1", 1, 1D, new BigDecimal(1), new Float[] {1F, 2F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("2", 2, 2D, new BigDecimal(2), new Float[] {1F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 22, 22D, new BigDecimal(22), new Float[] {1F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 22, 22D, new BigDecimal(223), new Float[] {1F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 22, 222D, new BigDecimal(22), new Float[] {1F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F, 3F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F, 4F})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {})));
		Assert.assertTrue(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), null)));
		// not contains
		Assert.assertFalse(testedSet.contains(new TestCompound("1", 1, null, new BigDecimal(1), new Float[] {1F, 2F})));
		Assert.assertFalse(testedSet.contains(new TestCompound("2", 2, 2D, new BigDecimal(2), new Float[] {null})));
		Assert.assertFalse(testedSet.contains(new TestCompound("22", 22, 22D, new BigDecimal(21), new Float[] {1F})));
		Assert.assertFalse(testedSet.contains(new TestCompound("22x", 22, 22D, new BigDecimal(223), new Float[] {1F})));
		Assert.assertFalse(testedSet.contains(new TestCompound("22", null, 222D, new BigDecimal(22), new Float[] {1F})));
		Assert.assertFalse(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {2F})));
		Assert.assertFalse(testedSet.contains(new TestCompound("22", 222, 22D, null, new Float[] {1F, 3F})));
		Assert.assertFalse(testedSet.contains(new TestCompound("22", 222, 22D, new BigDecimal(22), new Float[] {1F, null})));
		Assert.assertFalse(testedSet.contains(new TestCompound("", 222, 22D, new BigDecimal(22), new Float[] {})));
		Assert.assertFalse(testedSet.contains(new TestCompound(null, 222, 22D, new BigDecimal(22), null)));
	}
	
}
