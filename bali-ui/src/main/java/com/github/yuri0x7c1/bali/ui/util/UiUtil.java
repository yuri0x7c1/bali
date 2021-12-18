package com.github.yuri0x7c1.bali.ui.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author yuri0x7c1
 *
 */
public class UiUtil {
	/*
	 * Navigate back
	 */
	public static void back() {
		JavaScript.getCurrent().execute("history.back()");
	}

	public static void navigateTo(String viewName) {
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

	public static Direction convertSortDirection(SortDirection sortDirection) {
		if (SortDirection.ASCENDING.equals(sortDirection)) {
			return Direction.ASC;
		}
		return Direction.DESC;
	}

	public static SortDirection convertDirection(Direction direction) {
		if (Direction.ASC.equals(direction)) {
			return SortDirection.ASCENDING;
		}
		return SortDirection.DESCENDING;
	}

	public static String getGridSortProperty(List<GridSortOrder> sortOrders, String defaultProperty) {
		if (CollectionUtils.isNotEmpty(sortOrders)) {
			return sortOrders.get(0).getSorted().getId();
		}
		return defaultProperty;
	}

	public static SortDirection getGridSortDirection(List<GridSortOrder> sortOrders, SortDirection defaultDirection) {
		if (CollectionUtils.isNotEmpty(sortOrders)) {
			return sortOrders.get(0).getDirection();
		}
		return defaultDirection;
	}

	public static String getGridOrderProperty(Grid grid, String defaultProperty) {
		return getGridSortProperty(grid.getSortOrder(), defaultProperty);
	}

	public static SortDirection getGridSortDirection(Grid grid, SortDirection defaultDirection) {
		return getGridSortDirection(grid.getSortOrder(), defaultDirection);
	}

	public static Direction getGridOrderDirection(Grid grid, Direction defaultDirection) {
		return convertSortDirection(getGridSortDirection(grid.getSortOrder(), convertDirection(defaultDirection)));
	}

	public static MButton createBackButton(String caption) {
		return new MButton(VaadinIcons.ARROW_LEFT, caption, (ClickListener) event -> UiUtil.back())
				.withStyleName(ValoTheme.BUTTON_PRIMARY);

	}

	public static MButton createBackButton(String caption, ClickListener listener) {
		return new MButton(VaadinIcons.ARROW_LEFT, caption, listener)
				.withStyleName(ValoTheme.BUTTON_PRIMARY);

	}

	public static MLabel createErrorLabel(String value) {
		return new MLabel(value).withStyleName(ValoTheme.LABEL_FAILURE);
	}
}