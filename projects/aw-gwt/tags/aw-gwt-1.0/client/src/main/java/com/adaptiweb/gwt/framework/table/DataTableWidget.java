package com.adaptiweb.gwt.framework.table;

import java.util.Arrays;
import java.util.Iterator;

import com.adaptiweb.gwt.framework.ElementToWidgetMapper;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class DataTableWidget extends Panel implements HasClickHandlers, HasDoubleClickHandlers, HasMouseOverHandlers,
		HasMouseOutHandlers {

	private final TableAccessor body = new TableAccessor();
	private final TableAccessor head = new TableAccessor();
	private final TableAccessor foot = new TableAccessor();
	private final ElementToWidgetMapper widgets = new ElementToWidgetMapper();

	public DataTableWidget() {
		Element tableElement = DOM.createTable();
		body.setBaseElement(DOM.createTBody());
		DOM.appendChild(tableElement, body.getBaseElement());
		setElement(tableElement);
	}

	public int getCellPadding() {
		return DOM.getElementPropertyInt(getElement(), "cellPadding");
	}

	public int getCellSpacing() {
		return DOM.getElementPropertyInt(getElement(), "cellSpacing");
	}

	public void setBorderWidth(int width) {
		DOM.setElementPropertyInt(getElement(), "border", width);
	}

	public void setCellPadding(int padding) {
		DOM.setElementPropertyInt(getElement(), "cellPadding", padding);
	}

	public void setCellSpacing(int spacing) {
		DOM.setElementPropertyInt(getElement(), "cellSpacing", spacing);
	}

	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return addHandler(handler, DoubleClickEvent.getType());
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler, ClickEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addHandler(handler, MouseOverEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addHandler(handler, MouseOutEvent.getType());
	}

	public Position getCellPosistionForEvent(DomEvent<?> event) {
		return body.getPosistionForEvent(event);
	}

	public int getRowCount() {
		return body.getRowCount();
	}

	@Override
	public boolean remove(Widget child) {
		if (child.getParent() != this) return false;
		orphan(child);

		// Physical detach.
		Element elem = child.getElement();
		DOM.removeChild(DOM.getParent(elem), elem);

		// Logical detach.
		widgets.removeByElement(elem);
		return true;
	}

	@Override
	public Iterator<Widget> iterator() {
		return widgets.iterator();
	}

	@Override
	public void clear() {
		head.clear();
		body.clear();
		foot.clear();
	}

	public TableAccessor getBody() {
		return body;
	}

	public TableAccessor getHead() {
		return head;
	}

	public TableAccessor getFoot() {
		return foot;
	}

	public class TableAccessor {

		private Element element; // TBODY, THEAD or TFOOT

		public Element getBaseElement() {
			return element;
		}

		public void setBaseElement(Element element) {
			assert Arrays.asList("tbody", "thead", "tfoot").contains(element.getTagName().toLowerCase());
			this.element = element;
		}

		public String getHTML(int row, int column) {
			return DOM.getInnerHTML(getCellElement(row, column));
		}

		public String getText(int row, int column) {
			return DOM.getInnerText(getCellElement(row, column));
		}

		public Widget getWidget(int row, int column) {
			Element child = DOM.getFirstChild(getCellElement(row, column));
			return child == null ? null : widgets.get(child);
		}

		public void setHTML(int row, int column, String html) {
			Element td = prepareCell(row, column);
			cleanCell(row, column);
			if (html != null) DOM.setInnerHTML(td, html);
		}

		public void setText(int row, int column, String text) {
			Element td = prepareCell(row, column);
			cleanCell(row, column);
			if (text != null) DOM.setInnerText(td, text);
		}

		public void setWidget(int row, int column, Widget widget) {
			Element td = prepareCell(row, column);
			cleanCell(row, column);
			if (widget != null) {
				widget.removeFromParent();
				// Logical attach.
				widgets.put(widget);
				// Physical attach.
				DOM.appendChild(td, widget.getElement());

				adopt(widget);
			}
		}

		protected Element prepareRow(int row) {
			if (row < 0) {
				throw new IndexOutOfBoundsException("Cannot create a row with a negative index: " + row);
			}

			// Ensure that the requested row exists.
			int rowCount = getRowCount();
			for (int i = rowCount; i <= row; i++) {
				DOM.appendChild(element, DOM.createTR());
			}
			return getRowElement(row);
		}

		protected Element prepareCell(int row, int column) {
			if (column < 0) {
				throw new IndexOutOfBoundsException("Cannot create a column with a negative index: " + column);
			}
			Element tr = prepareRow(row);

			// Ensure that the requested column exists.
			int cellCount = getCellCount(row);
			for (int i = cellCount; i <= column; i++) {
				DOM.appendChild(tr, DOM.createTD());
			}
			return getCellElement(row, column);
		}

		public Position getPosistionForEvent(DomEvent<?> event) {
			if (element == null) return null;
			Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
			if (td == null) return null;

			Element tr = DOM.getParent(td);
			Element tbody = DOM.getParent(tr);
			if (tbody != element) return null;

			int row = DOM.getChildIndex(tbody, tr);
			int column = DOM.getChildIndex(tr, td);
			return new Position(row, column);
		}

		protected Element getEventTargetCell(Event event) {
			for (Element elm = DOM.eventGetTarget(event); elm != null; elm = DOM.getParent(elm)) {
				// If it's a TD, it might be the one we're looking for.
				if (DOM.getElementProperty(elm, "tagName").equalsIgnoreCase("td")) {
					// Make sure it's directly a part of this table before
					// returning it.
					Element tr = DOM.getParent(elm);
					Element tbody = DOM.getParent(tr);
					if (tbody == element) return elm;
				}
				// If we run into this table's body, we're out of options.
				if (elm == element) break;
			}
			return null;
		}

		public void clear() {
			int rowCount = getRowCount();
			for (int row = 0; row < rowCount; row++) {

				int cellCount = getCellCount(row);
				for (int col = 0; col < cellCount; col++) {
					cleanCell(row, col);
				}
			}
		}

		public boolean cleanCell(int row, int column) {
			Widget widget = getWidget(row, column);
			if (widget != null) {
				// If there is a widget, remove it.
				remove(widget);
				return true;
			}
			// Otherwise, simply clear whatever text and/or HTML may be there.
			else DOM.setInnerHTML(getCellElement(row, column), "");
			return false;
		}

		protected void removeCell(int row, int column) {
			cleanCell(row, column);
			DOM.removeChild(getRowElement(row), getCellElement(row, column));
		}

		protected void removeRow(int row) {
			int columnCount = getCellCount(row);
			for (int column = 0; column < columnCount; column++)
				cleanCell(row, column);
			DOM.removeChild(element, getRowElement(row));
		}

		private boolean isRowPressent(int row) {
			return row >= 0 && row < getRowCount();
		}

		public boolean isCellPresent(int row, int column) {
			return isRowPressent(row) && column >= 0 && column < getCellCount(row);
		}

		public Element getRowElement(int row) {
			checkRowBounds(row);
			return getDOMRowElement(element, row);
		}

		public Element getCellElement(int row, int column) {
			checkCellBounds(row, column);
			return getDOMCellElement(element, row, column);
		}

		public void checkRowBounds(int row) {
			if (row < 0 || row >= getRowCount()) {
				int count = getRowCount();
				throw new IndexOutOfBoundsException("Row index: " + row + ", count: " + count);
			}
		}

		public void checkCellBounds(int row, int column) {
			checkRowBounds(row);
			if (column < 0 || column >= getCellCount(row)) {
				int count = getCellCount(row);
				throw new IndexOutOfBoundsException("Column index: " + column + ", count: " + count);
			}
		}

		public int getRowCount() {
			return element == null ? 0 : getDOMRowCount(element);
		}

		public int getCellCount(int row) {
			return element == null ? 0 : getDOMCellCount(element, row);
		}

	}

	private static native Element getDOMRowElement(Element elem, int row) /*-{
		return elem.rows[row];
	}-*/;

	private static native Element getDOMCellElement(Element elem, int row, int column) /*-{
		return elem.rows[row].cells[column];
	}-*/;

	private static native int getDOMRowCount(Element elem) /*-{
		return elem.rows.length;
	}-*/;

	private static native int getDOMCellCount(Element elem, int row) /*-{
		return elem.rows[row].cells.length;
	}-*/;
}
