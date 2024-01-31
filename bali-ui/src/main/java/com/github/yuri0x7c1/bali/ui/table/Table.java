package com.github.yuri0x7c1.bali.ui.table;

import org.vaadin.viritin.layouts.MCssLayout;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;

public class Table extends MCssLayout {
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
		public TableBody() {
			addStyleName("bali-table-body");
			addStyleName("v-grid-body");
		}
	}

	public static class Row extends CssLayout {
		public Row() {
			addStyleName("bali-table-row");
			addStyleName("v-grid-row");
		}
	}

	public static class Cell extends CssLayout {
		public Cell() {
			addStyleName("bali-table-cell");
			addStyleName("v-grid-cell");
		}


		public void setValue(String value) {
			addComponent(new Label(value));
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

	public Table() {
		addStyleName("bali-table-wrapper");

		container.addComponent(header);
		container.addComponent(body);

		addComponent(container);
	}
}
