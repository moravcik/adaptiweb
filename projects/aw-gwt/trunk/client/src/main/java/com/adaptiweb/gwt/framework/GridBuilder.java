package com.adaptiweb.gwt.framework;

import java.util.HashMap;
import java.util.Map;

import com.adaptiweb.gwt.framework.style.Style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class GridBuilder {
	
	private int rows = 0;
	private int columns = 0;
	
	private final Map<Coordinates, Cell> cells = new HashMap<Coordinates, Cell>();
	
	private static class BuilderGrid extends Grid {
		public BuilderGrid(int rows, int columns) {
			super(rows, columns);
		}
		//changed visibility
		@Override
		public void removeCell(int row, int column) {
			super.removeCell(row, column);
		}
	}

	public Grid build() {
		BuilderGrid grid = new BuilderGrid(rows, columns);
		for(int r = 0; r < rows; r++) {
			for(int c = columns - 1; c >= 0; c--) {
				Cell cell = cells.get(new Coordinates(r, c));
				if(cell == null) grid.removeCell(r, c);
				else cell.apply(r, c, grid);
			}
		}

		return grid;
	}
	
	public Linker row() {
		return row(rows++);
	}
	
	public Linker row(final int row) {
		 return new Linker() {
			int col = 0; 
			@Override
			protected void link(Cell cell) {
				setCell(row, col++, cell);
			}
		 };
	}
	
	public Linker column() {
		return column(columns++);
	}
	
	public Linker column(final int col) {
		 return new Linker() {
			int row = 0; 
			@Override
			protected void link(Cell cell) {
				setCell(row++, col, cell);
			}
		 };
	}
	
	private void setCell(int row, int col, Cell cell) {
		while(row >= rows) rows++;
		while(col >= columns) columns++;
		
		if(cells.put(new Coordinates(row, col), cell) != null)
			GWT.log("Content [" + row + "," + col + "] owerwrited!", null);
	}
	
	public abstract class Linker {
		
		public Linker row() {
			return GridBuilder.this.row();
		}
		
		public Linker row(int row) {
			return GridBuilder.this.row(row);
		}
		
		public Linker column() {
			return GridBuilder.this.column();
		}
		
		public Linker column(int col) {
			return GridBuilder.this.column(col);
		}
		
		public Grid build() {
			return GridBuilder.this.build();
		}
		
		public Linker cell() {
			link(GridBuilder.this.cell());
			return this;
		}

		public Linker cell(String text, Style...styles) {
			link(GridBuilder.this.cell(text, styles));
			return this;
		}

		public Linker cell(Widget widget, Style...styles) {
			link(GridBuilder.this.cell(widget, styles));
			return this;
		}
		
		protected abstract void link(Cell cell);
	}

	protected Cell cell() {
		return new Cell() {
			@Override
			public void apply(int r, int c, Grid grid) {
				//nothing to do
			}
		};
	}

	protected Cell cell(final String text, final Style...styles) {
		return new Cell() {
			public void apply(int r, int c, Grid grid) {
				grid.setText(r, c, text);
				applyStyles(grid, r, c, styles);
			}
		};
	}
	
	protected Cell cell(final Widget widget, final Style...styles) {
		return new Cell() {
			public void apply(int r, int c, Grid grid) {
				grid.setWidget(r, c, widget);
				applyStyles(grid, r, c, styles);
			}
		};
	}
	
	private static void applyStyles(Grid grid, int row, int col, Style...styles) {
		if(styles.length > 0) {
			Element element = grid.getCellFormatter().getElement(row, col);
			for(Style style : styles)
				style.apply(element);
		}
	}
	
	protected interface Cell {
		void apply(int r, int c, Grid grid);
	}

	public static Style colspan(final int span) {
		return new Style() {
			public void apply(Element td) {
				DOM.setElementPropertyInt(td, "colSpan", span);
			}
		};
	}

	public static Style rowspan(final int span) {
		return new Style() {
			public void apply(Element td) {
				DOM.setElementPropertyInt(td, "rowSpan", span);
			}
		};
	}

	public static Style width(final String width) {
		return new Style() {
			public void apply(Element td) {
				td.setAttribute("width", width);
			}
		};
	}
}

final class Coordinates {
	public final int row;
	public final int column;
	
	Coordinates(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof Coordinates == false) return false;

		final Coordinates other = (Coordinates) obj;
		return column == other.column && row == other.row;
	}
}

