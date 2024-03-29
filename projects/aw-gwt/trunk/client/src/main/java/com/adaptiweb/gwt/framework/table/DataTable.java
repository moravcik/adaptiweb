package com.adaptiweb.gwt.framework.table;

import java.util.List;

import com.adaptiweb.gwt.framework.table.model.DefaultListModel;
import com.adaptiweb.gwt.framework.table.model.ListChangeEvent;
import com.adaptiweb.gwt.framework.table.model.ListChangeHandler;
import com.adaptiweb.gwt.framework.table.model.ListModel;
import com.adaptiweb.gwt.framework.table.model.ListRefreshHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public class DataTable<R extends DataTableRecord> extends Composite implements ListModel<R> { // FIXME only delegating DefaultListModel
	
	private final ListModel<R> model = new DefaultListModel<R>(this);
	private final DataTableWidget widget = new DataTableWidget();
//    private final FlexCellFormatter cell = new FlexCellFormatter(widget);
//    private final FlexRowFormatter row = new FlexRowFormatter(widget);
	
	public DataTable() {
		initWidget(widget);
	}

	public DataTableColumn addColumn(String string) {
		return null;
	}

	public HandlerRegistration addHandler(ListChangeHandler<R> handler) {
		addHandler(handler, ListChangeEvent.getType());
		return model.addHandler(handler);
	}

	public HandlerRegistration addHandler(ListRefreshHandler<R> handler) {
		return model.addHandler(handler);
	}

	public R get(int position) {
		return model.get(position);
	}

	public int indexOf(R item) {
		return model.indexOf(item);
	}

	public void refresh(int position, int count) {
		model.refresh(position, count);
	}

	public List<R> replace(int position, int count, List<R> listModeltem) {
		return model.replace(position, count, listModeltem);
	}

	public int size() {
		return model.size();
	}
	
	public boolean isEmpty() {
		return model.isEmpty();
	}
	
	public void add(R item) {
		model.add(item);
	}
	
	public void remove(R item) {
		model.remove(item);
	}
	
	public void removeAll() {
		model.removeAll();
	}
	
	public void removeAll(List<R> items) {
		model.removeAll(items);
	}
	
	@Override
	public void addAll(List<R> items) {
		model.addAll(items);
	}

	@Override
	public void setAll(List<R> items) {
		model.setAll(items);
	}

	@Override
	protected void onLoad() {
		renderHeaders();
	}

	@Override
	public void replace(R item) {
		model.replace(item);
	};
	
	public void refresh(R item) {
		model.refresh(item);
	};
	
	private void renderHeaders() {
        widget.clear();
//        formatHeaderRow(0, widget.getRowFormatter());
//        int col = 0, skip = 0;
//        for (Column<?> column : columns.getColumnOrdering()) {
//            if (skip-- > 0)
//                continue;
//
//            column.renderHeader(cellAccessor.setCurentPhysicalPosition(0, col),
//                    columns.indexOf(column) + 1);
//            skip = cellAccessor.getColSpan() - 1;
//            col++;
//        }
	}

	public List<R> getItems() {
		return model.getItems();
	}

}
