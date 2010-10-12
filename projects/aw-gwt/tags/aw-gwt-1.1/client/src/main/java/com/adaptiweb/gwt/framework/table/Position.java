package com.adaptiweb.gwt.framework.table;

public final class Position {
	private final int column;
	private final int row;
	
	public Position(int row, int column) {
		this.column = column;
		this.row = row;
	}

	/**
	 * @return Logical column position of current cell based on 1.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return If current cell is header returns 0, otherwise
	 * returns logical row position of current cell based on 1.
	 * 
	 */
	public int getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getColumn();
		result = prime * result + getRow();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Position == false)
			return false;
		
		final Position other = (Position) obj;
		return this.getColumn() == other.getColumn()
			&& this.getRow() == other.getRow();
	}
}
