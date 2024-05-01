package com.github.yuri0x7c1.bali.ui.table;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.viritin.layouts.MCssLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;

public class SimpleTable extends MCssLayout {
	public static class TableContainer extends CssLayout {
		public TableContainer() {
			addStyleName("bali-table");
		}
	}

	public static class TableHeader extends CssLayout {
		public TableHeader() {
			addStyleName("bali-table-header");
			addStyleName("v-grid-header");
		}
	}

	public static class TableBody extends CssLayout {
		List<Row> rows = new ArrayList<>();

		public TableBody() {
			addStyleName("bali-table-body");
			addStyleName("v-grid-body");
		}

		public void addRow(Row row) {
			rows.add(row);
		}
	}

	public static class Row extends CssLayout {
		List<Cell> cells = new ArrayList<>();

		public Row() {
			addStyleName("bali-table-row");
			addStyleName("v-grid-row");
		}

		public void addCell(Cell cell) {
			cells.add(cell);
		}
	}

	public static class Cell extends CssLayout {
		@Getter
		private final Component component;

		public Cell(Component component) {
			this.component = component;
			addStyleName("bali-table-cell");
			addStyleName("v-grid-cell");
		}
	}

	public static class HeaderCell extends CssLayout {
		private final Label content;

		public HeaderCell() {
			addStyleName("bali-table-cell");
			addStyleName("v-grid-cell");
			content = new Label();
			content.addStyleName(ValoTheme.LABEL_SMALL);
			content.addStyleName("bali-table-header-cell-content");
			addComponent(content);
		}

		public void setValue(String value) {
			content.setValue(value);
		}
	}

	@Getter
	private TableContainer container = new TableContainer();

	@Getter
	private TableHeader header = new TableHeader();

	@Getter
	private TableBody body = new TableBody();

	public SimpleTable() {
		addStyleName("bali-table-wrapper");

		container.addComponent(header);
		container.addComponent(body);

		addComponent(container);
	}
}
