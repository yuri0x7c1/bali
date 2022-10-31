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

package com.github.yuri0x7c1.bali.ui.datagrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MPanel;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.vaadin.viritinv7.fields.MTable;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.data.search.model.SearchModel;
import com.github.yuri0x7c1.bali.ui.handler.DeleteHandler;
import com.github.yuri0x7c1.bali.ui.handler.EditHandler;
import com.github.yuri0x7c1.bali.ui.handler.ShowHandler;
import com.github.yuri0x7c1.bali.ui.pagination.Pagination;
import com.github.yuri0x7c1.bali.ui.pagination.PaginationResource;
import com.github.yuri0x7c1.bali.ui.search.CommonSearchForm;
import com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentDescriptor;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.GridSelectionModel;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class EntityDataGrid.
 *
 * @param <T> the generic type
 *
 * @author yuri0x7c1
 */
@Slf4j
@SuppressWarnings("deprecation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class EntityDataGrid<T> extends MVerticalLayout {

	private static final int ACTION_BUTTON_WIDTH = 38;

	public static final String ACTIONS_COLUMN_ID = "_actions";

	public interface SearchProvider<T> extends Serializable {
		Page<T> search(Integer page, Integer pageSize, String orderProperty, Direction orderDirection, SearchModel searchModel);
	}

    public interface SearchCountProvider<T> extends Serializable {
        Long searchCount(SearchModel searchModel);
    }

    public interface EntityProcessor<T> extends Serializable {
    	T process(T entity);
    }

	final Class<T> entityType;

	final I18N i18n;

	@Getter
	@Setter
	SearchProvider<T> searchProvider = (page, pageSize, orderProperty, orderDirection, searchModel) -> Page.empty();

	@Getter
	@Setter
	SearchCountProvider<T> searchCountProvider = (searchModel) -> 0L;

	ShowHandler<T> showHandler;

	EditHandler<T> editHandler;

	DeleteHandler<T> deleteHandler;

	@Getter
	@Setter
	EntityProcessor<T> entityProcessor;

	final CommonSearchForm searchForm;

	@Getter
	final MTable<T> grid;

	final Pagination pagination;

	Column<T, MHorizontalLayout> actionsColumn;

	@Getter
	@Setter
	int page = 0;

	@Getter
	@Setter
	int pageSize = 15;

	@Getter
	final String defaultOrderProperty;

	@Getter
	final Direction defaultOrderDirection;

	@Getter
	@Setter
	String orderProperty;

	@Getter
	@Setter
	Direction orderDirection;

	final List<EntityProperty<T>> properties = new ArrayList<>();

	final List<T> items = new ArrayList<>();

	public EntityDataGrid(Class<T> entityType, I18N i18n, CommonSearchForm searchForm, String defaultOrderProperty, Direction defaultOrderDirection) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.searchForm = searchForm;
		this.defaultOrderProperty = defaultOrderProperty;
		this.defaultOrderDirection = defaultOrderDirection;

		this.orderProperty = defaultOrderProperty;
		this.orderDirection = defaultOrderDirection;

		setSizeFull();
		setMargin(false);
		addStyleName(BaliStyle.ENTITY_DATA_GRID);

		// search form
		searchForm.setSearchHandler(() -> refresh());

		// entity grid
		grid = new MTable<T>(entityType);
		//grid.removeAllColumns();
		//grid.setSelectionMode(SelectionMode.NONE);
		grid.setWidthFull();
		//grid.setHeightByRows(pageSize);

		/*
		grid.setDataProvider(
			DataProvider.fromCallbacks(
				query -> items.stream(),
				query -> items.size()
			)
		);
		*/

		grid.addSortListener(event -> {
			orderDirection = getOrderDirection();
			orderProperty = getOrderProperty();
			refresh();
		});

		// pagination
		pagination = new Pagination(
			i18n,
			PaginationResource.newBuilder()
				.setPage(page + 1)
				.setLimit(pageSize)
				.build()
		);
		pagination.setItemsPerPage(15, 25, 50, 75, 100);

		pagination.addPageChangeListener(event -> {
			page = event.page() - 1;
			pageSize = event.limit();
			// grid.setHeightByRows(pageSize);
			refresh();
		});

		// add components
		add(searchForm, grid, new MPanel().withFullWidth()
			.withContent(
				new MHorizontalLayout(pagination)
					.withFullWidth()
					.withMargin(true)
			)
		);
	}

	private int calculateActionsColumnWidth() {
		int buttonCount = (showHandler != null ? 1 : 0) + (editHandler != null ? 1 : 0) + (deleteHandler != null ? 1 : 0);
		if (buttonCount == 0) return 8;
		return 8 + buttonCount*ACTION_BUTTON_WIDTH + (buttonCount-1)*4;
	}

	private void refreshActionsColumn() {
        if (showHandler != null || editHandler != null || deleteHandler != null) {
        	/*
        	if (grid.getColumn(ACTIONS_COLUMN_ID) != null) {
        		grid.removeColumn(ACTIONS_COLUMN_ID);
        	}
        	*/

        	grid.withGeneratedColumn(ACTIONS_COLUMN_ID, (T entity) -> {
        		MCssLayout l = new MCssLayout().withFullWidth();
        		if (showHandler != null) {
        			MButton show = new MButton(VaadinIcons.EYE, event -> {
        				showHandler.onShow(entity);
        			})
        			.withDescription(i18n.get("Show"))
					.withStyleName(
						BaliStyle.ACTION_BUTTON,
						ValoTheme.BUTTON_SMALL
					);
        			l.add(show);
        		}
        		if (editHandler != null) {
        			MButton edit = new MButton(VaadinIcons.PENCIL, event -> {
        				editHandler.onEdit(entity);
        			})
					.withDescription(i18n.get("Edit"))
					.withStyleName(
						BaliStyle.ACTION_BUTTON,
						BaliStyle.BUTTON_PRIMARY_FIX,
						ValoTheme.BUTTON_PRIMARY,
						ValoTheme.BUTTON_SMALL
					);
        			l.add(edit);
        		}
        		if (deleteHandler != null) {
        			ConfirmButton delete = new ConfirmButton(VaadinIcons.TRASH, i18n.get("Delete.confirm"), () -> {
        				deleteHandler.onDelete(entity);
        				refresh();
        			})
					.withDescription(i18n.get("Delete"))
					.withStyleName(
						BaliStyle.ACTION_BUTTON,
						ValoTheme.BUTTON_DANGER,
						ValoTheme.BUTTON_SMALL
					);
        			l.add(delete);
        		}
        		return l;
        	});
        	// c.setId(ACTIONS_COLUMN_ID);
        	// c.setWidth(calculateActionsColumnWidth());
        	// c.setSortable(false);
        	//c.setStyleGenerator(item -> BaliStyle.ACTION_CELL);
        	// grid.setColumnOrder(c);
        }
	}

	public void setHandlers(ShowHandler<T> showHandler, EditHandler<T> editHandler, DeleteHandler<T> deleteHandler) {
		this.showHandler = showHandler;
		this.editHandler = editHandler;
		this.deleteHandler = deleteHandler;
		refreshActionsColumn();
	}

	public void setShowHandler(ShowHandler<T> showHandler) {
		this.showHandler = showHandler;
		refreshActionsColumn();
	}

	public void setEditHandler(EditHandler<T> editHandler) {
		this.editHandler = editHandler;
		refreshActionsColumn();
	}

	public void setDeleteHandler(DeleteHandler<T> deleteHandler) {
		this.deleteHandler = deleteHandler;
		refreshActionsColumn();
	}

	public void refresh() {
		items.clear();
		Page<T> entityPage = searchProvider.search(page, pageSize, orderProperty, orderDirection,
				searchForm.getModel());
		pagination.setTotalCount(entityPage.getTotalElements());
		if (entityProcessor == null) {
			items.addAll(entityPage.getContent());
		}
		else {
			for (T entity : entityPage.getContent()) {
				items.add(entityProcessor.process(entity));
			}
		}
		grid.setBeans(items);
	}

	public void sortAndRefresh() {
		grid.sort(new Object[] {orderProperty}, new boolean[] {orderDirection.isAscending()});
	}

	public void refreshColumns() {
		List<String> propertyNames = new ArrayList<>();
		for (EntityProperty<T> property : properties) {
			propertyNames.add(property.getName());
			if (property.getValueProvider() != null) {
				grid.withGeneratedColumn(property.getName(), entity -> {
					return property.getValueProvider().apply(entity);
				});
			}
		}
		grid.withProperties(propertyNames);
		refreshActionsColumn();
	}

	public List<EntityProperty<T>> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public void setProperties(List<EntityProperty<T>> properties) {
		this.properties.clear();
		this.properties.addAll(properties);
	}

	public void addProperty(EntityProperty<T> property) {
		properties.add(property);
	}

	public void addProperties(List<EntityProperty<T>> properties) {
		for (EntityProperty<T> p : properties) {
			addProperty(p);
		}
	}

	public void registerSearchFieldComponent(SearchFieldComponentDescriptor fieldComponent) {
		searchForm.registerFieldComponent(fieldComponent);
	}

	public void addSearchField(String fieldName, SearchFieldOperator operator, Object[] params, Object value) {
		searchForm.createFieldComponent(fieldName, operator, params, value);
	}

	public void addSearchField(String fieldName, SearchFieldOperator operator, Object[] params) {
		searchForm.createFieldComponent(fieldName, operator, params, null);
	}

	public void addSearchField(String fieldName, SearchFieldOperator operator, Object value) {
		searchForm.createFieldComponent(fieldName, operator, new Object[0], value);
	}

	public void addSearchField(String fieldName, SearchFieldOperator operator) {
		searchForm.createFieldComponent(fieldName, operator, new Object[0], null);
	}

	public void clearSearchFields() {
		searchForm.clearFieldComponents();
	}

	public SearchModel getSearchModel() {
		return searchForm.getModel();
	}

	public Set<T> getSelectedItems() {
		// return grid.getSelectedItems();
		return Collections.emptySet();
	}

	public void setSelectedItems(Set<T> items) {
		/*
		grid.deselectAll();
		for (T item : items) {
			grid.select(item);
		}
		*/
	}

	public Optional<T> getFirstSelectedItem() {
		// return grid.getSelectionModel().getFirstSelectedItem();]
		return Optional.empty();
	}

	public void setSelectedItem(T item) {
		grid.select(item);
	}

	public GridSelectionModel<T> getSelectionModel() {
		// return grid.getSelectionModel();
		return null;
	}

	public void setSelectionMode(SelectionMode selectionMode) {
		// grid.setSelectionMode(selectionMode);
	}

	public Registration addItemClickListener(ItemClickListener<? super T> listener) {
		// return grid.addItemClickListener(listener);
		return null;
	}

	public Direction getOrderDirection() {
		// return UiUtil.getGridOrderDirection(grid, defaultOrderDirection);
		return defaultOrderDirection;
	}

	public String getOrderProperty() {
		// return UiUtil.getGridOrderProperty(grid, defaultOrderProperty);
		return defaultOrderProperty;
	}

	public SearchFieldComponentDescriptor.Builder searchFieldComponentBuilder() {
		return SearchFieldComponentDescriptor.builder();
	}

	public EntityProperty.Builder<T> propertyBuilder() {
		return EntityProperty.<T>builder();
	}
}
