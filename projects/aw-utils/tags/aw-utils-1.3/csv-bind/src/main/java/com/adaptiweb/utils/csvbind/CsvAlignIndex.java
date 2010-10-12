package com.adaptiweb.utils.csvbind;

/**
 * Csv bean interface to indicate index of left aligned line cells, interpreted later as bean.
 * Index of first not empty cell.
 */
public interface CsvAlignIndex {

	/**
	 * Set align index.
	 * @param index
	 */
	public void setAlignIndex(Integer index);
	
	/**
	 * Get align index.
	 * @return
	 */
	public Integer getAlignIndex();
	
}
