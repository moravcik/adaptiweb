package com.adaptiweb.gwt.framework.table.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultListModelTest {

	static class TestRecord {
		int number;
		String str;
		
		TestRecord(int number, String str) {
			this.number = number;
			this.str = str;
		}
	}
	
	private static TestRecord[] records;

	class TestChangeHandler implements ListChangeHandler<TestRecord> {
		@Override
		public void onListChange(ListChangeEvent<TestRecord> event) {
			changeIndex = event.getIndex();
			changeInsertedRecords = event.getInsertedRecords();
			changeRemovedRecords = event.getRemovedRecords();
		}
	}

	class TestRefreshHandler implements ListRefreshHandler<TestRecord> {
		@Override
		public void onListRefresh(ListRefreshEvent<TestRecord> event) {
			refreshIndex = event.getIndex();
			refreshList = event.getRefreshed();
		}
	}

	private Integer changeIndex;
	private List<TestRecord> changeInsertedRecords;
	private List<TestRecord> changeRemovedRecords;
	
	private Integer refreshIndex;
	private List<TestRecord> refreshList;
	
	private ListModel<TestRecord> model;
	
	@BeforeClass
	public static void initRecords() {
		records = new TestRecord[] {
			new TestRecord(1, "first"),
			new TestRecord(2, "second"),
			new TestRecord(3, "third"),
			new TestRecord(4, "fourth"),
			new TestRecord(5, "fifth"),
			new TestRecord(6, "sixth"),
			new TestRecord(7, "seventh"),
		};
	}
	
	@Before
	public void initListModel() {
		model = new DefaultListModel<TestRecord>();
		model.addHandler(new TestChangeHandler());
		model.addHandler(new TestRefreshHandler());
	}
	
	@After
	public void clearHandlerValues() {
		changeIndex = null;
		changeInsertedRecords = null;
		changeRemovedRecords = null;
		refreshIndex = null;
		refreshList = null;
	}
	
	@Test
	public void testListModel() {
		testReplace(-1, 0, records[0], records[1], records[4]);
		testRefresh(0, 2);
		
		testReplace(2, 1, records[6], records[5], records[4], records[2]);
		testRefresh(3, 3);
		
		testReplace(1, 4);
		testRefresh(0, model.size());
	}

	private void testReplace(int index, int remove, TestRecord... records) {
		List<TestRecord> list = Arrays.asList(records);
		int oldSize = model.size();
		
		model.replace(index, remove, list);
		
		assertEquals(oldSize - remove + records.length, model.size());
		for (int i = 0; i < records.length; i++) {
			assertSame(records[i], model.get((index == -1 ? 0 : index) + i));
			assertEquals((index == -1 ? 0 : index) + i, model.indexOf(records[i]));
		}
		
		assertEquals(new Integer(index), changeIndex);
		assertEquals(new HashSet<TestRecord>(list), new HashSet<TestRecord>(changeInsertedRecords));
		assertEquals(remove, changeRemovedRecords.size());		
	}
	
	private void testRefresh(int position, int count) {
		model.refresh(position, count);
		
		assertEquals(new Integer(position), refreshIndex);
		assertEquals(count, refreshList.size());
	}
}
