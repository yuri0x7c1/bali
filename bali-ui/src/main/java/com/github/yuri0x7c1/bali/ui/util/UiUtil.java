package com.github.yuri0x7c1.bali.ui.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort.Direction;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Grid;
import com.vaadin.ui.JavaScript;

public class UiUtil {
	/*
	 * Navigate back
	 */
	public static void back() {
		JavaScript.getCurrent().execute("history.back()");
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
}