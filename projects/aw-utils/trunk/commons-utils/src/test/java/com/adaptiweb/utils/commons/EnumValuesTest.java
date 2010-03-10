package com.adaptiweb.utils.commons;

import static org.junit.Assert.*;

import java.util.EnumSet;

import org.junit.Test;

import com.adaptiweb.utils.commons.EnumValues.EnumFilter;

public class EnumValuesTest {
	
	static enum SampleEnum { a, b, c, d, e, f }
	
	static EnumFilter<SampleEnum> evenFilter = new EnumFilter<SampleEnum>() {
		@Override
		public boolean fit(SampleEnum value) {
			System.out.printf((value.ordinal() + 1) % 2 == 0 ? "%s is parny\n" : "%s is not parny\n", value);
			return (value.ordinal() + 1) % 2 == 0;
		}
	};

	static EnumFilter<SampleEnum> oddFilter = new EnumFilter<SampleEnum>() {
		@Override
		public boolean fit(SampleEnum value) {
			System.out.printf((value.ordinal() + 1) % 2 == 1 ? "%s is neparny\n" : "%s is not neparny\n", value);
			return (value.ordinal() + 1) % 2 == 1;
		}
	};

	@Test
	public void testRetain() {
		EnumValues<SampleEnum> tested = EnumValues.of(SampleEnum.class);
		assertEquals(EnumSet.allOf(SampleEnum.class), tested.get());
		tested.retain(evenFilter);
		assertEquals(EnumSet.of(SampleEnum.b, SampleEnum.d, SampleEnum.f), tested.get());
		tested.retain(oddFilter);
		assertEquals(EnumSet.noneOf(SampleEnum.class), tested.get());
	}

	@Test
	public void testRemove() {
		EnumValues<SampleEnum> tested = EnumValues.of(SampleEnum.class);
		assertEquals(EnumSet.allOf(SampleEnum.class), tested.get());
		tested.remove(oddFilter);
		assertEquals(EnumSet.of(SampleEnum.b, SampleEnum.d, SampleEnum.f), tested.get());
		tested.remove(evenFilter);
		assertEquals(EnumSet.noneOf(SampleEnum.class), tested.get());
	}

}
