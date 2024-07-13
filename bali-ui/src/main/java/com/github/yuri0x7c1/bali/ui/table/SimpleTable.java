package com.github.yuri0x7c1.bali.ui.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import lombok.Getter;

public class SimpleTable extends CssLayout {

	public static class TableHeader extends CssLayout {
		@Getter
		HeaderRow row = new HeaderRow();

		public TableHeader() {
			addStyleName("bali-simple-table-header");
		}

		public void setComponent(Component component, int cellIndex) {
			row.getCell(cellIndex).setComponent(component);
		}

		public void removeComponent(int cellIndex) {
			row.getCell(cellIndex).removeAllComponents();
		}
	}

	public static class HeaderRow extends CssLayout {
		private List<HeaderCell> cells = new ArrayList<>();

		public HeaderRow() {
			addStyleName("bali-simple-table-header-row");
		}

		public void addCell(HeaderCell cell) {
			cells.add(cell);
			addComponent(cell);
		}

		public HeaderCell getCell(int cellIndex) {
			return cells.get(cellIndex);
		}
	}

	public static class HeaderCell extends CssLayout {
		@Getter
		private Component component;

		public HeaderCell() {
			addStyleName("bali-simple-table-header-cell");
		}

		public HeaderCell(Component component) {
			this();
			setComponent(component);
		}

		public void setComponent(Component component) {
			removeComponent();
			this.component = component;
			addComponent(component);
		}

		public void removeComponent() {
			this.component = null;
			removeAllComponents();
		}
	}

	public static class TableBody extends CssLayout {
		private List<Row> rows = new ArrayList<>();

		public TableBody() {
			addStyleName("bali-simple-table-body");
		}

		public void addRow(Row row) {
			rows.add(row);
			addComponent(row);
		}

		public Row getRow(int rowIndex) {
			return rows.get(rowIndex);
		}

		public Component getComponent(int cellIndex, int rowIndex) {
			return rows.get(rowIndex).getCell(cellIndex).getComponent();
		}

		public void setComponent(Component component, int cellIndex, int rowIndex) {
			rows.get(rowIndex).getCell(cellIndex).setComponent(component);
		}

		public void removeComponent(int cellIndex, int rowIndex) {
			rows.get(rowIndex).getCell(cellIndex).removeAllComponents();
		}
	}

	public static class Row extends CssLayout {
		private List<Cell> cells = new ArrayList<>();

		public Row() {
			addStyleName("bali-simple-table-row");
		}

		public void addCell(Cell cell) {
			cells.add(cell);
			addComponent(cell);
		}

		public Cell getCell(int cellIndex) {
			return cells.get(cellIndex);
		}
	}

	public static class Cell extends CssLayout {
		@Getter
		private Component component;

		public Cell() {
			addStyleName("bali-simple-table-cell");
		}

		public Cell(Component component) {
			this();
			setComponent(component);
		}

		public void setComponent(Component component) {
			removeComponent();
			this.component = component;
			addComponent(component);
		}

		public void removeComponent() {
			this.component = null;
			removeAllComponents();
		}
	}

	@Getter
	private TableHeader header = new TableHeader();

	@Getter
	private TableBody body = new TableBody();

	public SimpleTable(boolean headerVisible, int width, int height) {
		addStyleName("bali-simple-table");

		for (int w = 0; w < width; w++) {
			header.getRow().addCell(new HeaderCell());
		}

		for (int h = 0; h < height; h++) {
			Row row = new Row();
			for (int w = 0; w < width; w++) {
				row.addCell(new Cell());
			}
			body.addRow(row);
		}

		if (headerVisible) {
			addComponent(header);
		}
		addComponent(body);
	}

	public void addRow(Row row) {
		body.addRow(row);
	}

	public Row getRow(int rowIndex) {
		return body.getRow(rowIndex);
	}

	public Component getComponent(int cellIndex, int rowIndex) {
		return body.getComponent(cellIndex, rowIndex);
	}

	public void setComponent(Component component, int cellIndex, int rowIndex) {
		body.setComponent(component, cellIndex, rowIndex);
	}

	public void removeComponent(int cellIndex, int rowIndex) {
		body.removeComponent(cellIndex, rowIndex);
	}
}