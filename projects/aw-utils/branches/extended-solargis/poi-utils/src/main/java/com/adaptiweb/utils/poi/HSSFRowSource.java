package com.adaptiweb.utils.poi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.adaptiweb.utils.commons.CollectionUtils;
import com.adaptiweb.utils.commons.StringUtils.StringArraySource;

public class HSSFRowSource implements StringArraySource {
	HSSFWorkbook workbook;
	HSSFDataFormatter formatter;
	HSSFFormulaEvaluator evaluator;
	
	Iterator<Row> sheetRowIterator;
	List<String> stringCacheList;
	
	private HSSFRowSource(HSSFWorkbook workbook) {
		this.workbook = workbook;
		this.formatter = new HSSFDataFormatter();
		this.evaluator = new HSSFFormulaEvaluator(workbook);
		this.stringCacheList = new ArrayList<String>();
		
		initSheetIterator(workbook.getSheetAt(0)); 
	}
	
	/**
	 * Zero-based.
	 */
	public HSSFRowSource useSheet(int sheetIndex) {
		initSheetIterator(workbook.getSheetAt(sheetIndex));
		return this;
	}

	public HSSFRowSource useSheet(String sheetName) {
		initSheetIterator(workbook.getSheet(sheetName));
		return this;
	}
	
	private void initSheetIterator(HSSFSheet sheet) {
		sheetRowIterator = sheet != null ? sheet.iterator() : null;
		stringCacheList.clear();
	}
	
	@Override
	public String[] getStringArray() {
		if (sheetRowIterator != null && sheetRowIterator.hasNext()) {
			return rowToStringArray(sheetRowIterator.next());
		} else return null;
	}

	private String[] rowToStringArray(Row row) {
		stringCacheList.clear();
		for (Cell cell : CollectionUtils.asIterable(row.iterator()))
			stringCacheList.add(formatter.formatCellValue(cell, evaluator));
		return stringCacheList.toArray(new String[stringCacheList.size()]);
	}
	
	public static HSSFRowSource fromResource(String resourcePath) {
		return new HSSFRowSource(PoiUtils.loadWorkbookFromResource(resourcePath));
	}

	public static HSSFRowSource fromFile(String filePath) {
		return new HSSFRowSource(PoiUtils.loadWorkbookFromFile(filePath));
	}

	public static HSSFRowSource fromStream(InputStream stream) {
		return new HSSFRowSource(PoiUtils.loadWorkbookFromStream(stream));
	}
	
}
