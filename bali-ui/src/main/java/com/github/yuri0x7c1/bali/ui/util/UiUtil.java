/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.ui.util;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;

import com.github.yuri0x7c1.bali.data.message.CommonMessages;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.GridSortOrderBuilder;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
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

	public static final String FLASH_MESSAGES = "FLASH_MESSAGES";

	/*
	 * Navigate back
	 */
	public static void back() {
		JavaScript.getCurrent().execute("history.back()");
	}

	public static void back(CommonMessages flashMessages) {
		writeFlashMessages(flashMessages);
		JavaScript.getCurrent().execute("history.back()");
	}

	public static void navigateTo(String viewName) {
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

	public static void reload() {
		UI.getCurrent().getPage().reload();
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

	public static <T> List<GridSortOrder<T>> convertSort(Grid grid, Sort sort) {
		GridSortOrderBuilder<T> builder = new GridSortOrderBuilder<>();
		sort.forEach(order -> {
			if (Direction.ASC.equals(order.getDirection())) {
				builder.thenAsc(grid.getColumn(order.getProperty()));
			}
			else {
				builder.thenDesc(grid.getColumn(order.getProperty()));
			}
		});
		return builder.build();
	}

	public static <T> Sort convertGridSortOrders(List<GridSortOrder<T>> sortOrders) {
		return Sort.by(sortOrders.stream()
			.map(so -> SortDirection.ASCENDING.equals(so.getDirection()) ? Order.asc(so.getSorted().getId()) : Order.desc(so.getSorted().getId()))
			.collect(Collectors.toList()));
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

	public static Pageable createPageable(Query query, Direction direction, String...fields) {
		return PageRequest.of(
			query.getOffset() / query.getLimit(),
			query.getLimit(),
			direction,
			fields
		);
	}

	public static Pageable createPageable(Query query, String...fields) {
		return createPageable(query, Direction.ASC, fields);
	}

	public static CommonMessages readFlashMessages() {
		CommonMessages messages = (CommonMessages) VaadinSession.getCurrent().getAttribute(FLASH_MESSAGES);
		if (messages != null) {
			VaadinSession.getCurrent().setAttribute(FLASH_MESSAGES, null);
		}
		return messages;
	}

	public static void writeFlashMessages(CommonMessages messages) {
		VaadinSession.getCurrent().setAttribute(FLASH_MESSAGES, messages);
	}

	public static void enableHorizontalSpacing(ResponsiveLayout layout) {
		layout.iterator().forEachRemaining(c -> ((ResponsiveRow) c).setHorizontalSpacing(true));
	}
}