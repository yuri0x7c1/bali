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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MPanel;
import org.vaadin.viritin.layouts.MVerticalLayout;

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
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.Column.NestedNullBehavior;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.GridSelectionModel;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class EntityDataGrid.
 *
 * @param <T> the generic type
 *
 * @author yuri0x7c1
 */
public abstract class EntityDataGrid<T> extends MVerticalLayout {

	@Deprecated
	public interface SearchProviderOld<T> extends Serializable {
		Page<T> search(Integer page, Integer pageSize, String orderProperty, Direction orderDirection, SearchModel searchModel);
	}

	public interface SearchProvider<T> extends Serializable {
		Page<T> search(Pageable pageable, SearchModel searchModel);
	}

    public interface SearchCountProvider<T> extends Serializable {
        Long searchCount(SearchModel searchModel);
    }

    public interface EntityProcessor<T> extends Serializable {
    	T process(T entity);
    }

    public interface SortProcessor extends Serializable {
    	Sort process(Sort currentSort);
    }

	private final Class<T> entityType;

	private final I18N i18n;

	@Getter
	@Setter
	private SearchProvider<T> searchProvider = (pageable, searchModel) -> Page.empty();

	@Getter
	@Setter
	private SearchCountProvider<T> searchCountProvider = (searchModel) -> 0L;

	private ShowHandler<T> showHandler;

	private EditHandler<T> editHandler;

	private DeleteHandler<T> deleteHandler;

	@Setter
	@Getter
	private EntityProcessor<T> entityProcessor;

	private final CommonSearchForm searchForm;

	@Getter
	private final MGrid<T> grid;

	private final Pagination pagination;

	private Column<T, MHorizontalLayout> actionsColumn;

	@Getter
	@Setter
	private int page = 0;

	@Getter
	@Setter
	private int pageSize = 15;

	@Getter
	private final Sort defaultSort;

	@Getter
	@Setter
	private Sort sort = Sort.unsorted();

	private SortProcessor sortProcessor;

	private final List<EntityProperty<T>> properties = new ArrayList<>();

	private final List<T> items = new ArrayList<>();

	@Deprecated
	public EntityDataGrid(Class<T> entityType, I18N i18n, CommonSearchForm searchForm, String defaultOrderProperty, Direction defaultOrderDirection) {
		this(entityType, i18n, searchForm, Sort.by(defaultOrderDirection, defaultOrderProperty));
	}

	public EntityDataGrid(Class<T> entityType, I18N i18n, CommonSearchForm searchForm, Sort defaultSort) {
		this(entityType, i18n, searchForm, defaultSort, null);
	}

	public EntityDataGrid(Class<T> entityType, I18N i18n, CommonSearchForm searchForm, Sort defaultSort, SortProcessor sortProcessor) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.searchForm = searchForm;
		this.sortProcessor = sortProcessor;
		this.defaultSort = defaultSort;
		this.sort = defaultSort;

		setSizeFull();
		setMargin(false);
		addStyleName(BaliStyle.ENTITY_DATA_GRID);

		// search form
		searchForm.setSearchHandler(() -> refresh());

		// entity grid
		grid = new MGrid<T>(entityType);
		grid.removeAllColumns();
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setWidthFull();
		grid.setHeightByRows(pageSize);

		grid.setDataProvider(
			DataProvider.fromCallbacks(
				query -> items.stream(),
				query -> items.size()
			)
		);

		grid.addSortListener(event -> {
			sort = UiUtil.convertGridSortOrders(event.getSortOrder());
			if (sortProcessor != null) {
				sort = sortProcessor.process(sort);
			}
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
			if (grid.getHeightByRows() != Double.valueOf(pageSize)) {
				grid.setHeightByRows(pageSize);
			}
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
		return 8 + buttonCount*BaliStyle.ACTION_BUTTON_WIDTH + (buttonCount-1)*4;
	}

	private void refreshActionsColumn() {
        if (showHandler != null || editHandler != null || deleteHandler != null) {
        	if (grid.getColumn(BaliStyle.ACTIONS_COLUMN_ID) != null) {
        		grid.removeColumn(BaliStyle.ACTIONS_COLUMN_ID);
        	}
        	Column<T, MCssLayout> c = grid.addComponentColumn(entity -> {
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
        	c.setId(BaliStyle.ACTIONS_COLUMN_ID);
        	c.setWidth(calculateActionsColumnWidth());
        	c.setSortable(false);
        	c.setStyleGenerator(item -> BaliStyle.ACTION_CELL);
        	grid.setColumnOrder(c);
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
		Page<T> entityPage = searchProvider.search(PageRequest.of(page, pageSize, sort),
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
		grid.getDataProvider().refreshAll();
		if (entityPage.getNumberOfElements() > 0) {
			grid.setHeightByRows(entityPage.getNumberOfElements());
		}
		else {
			grid.setHeightByRows(1);
		}
	}

	public void sortAndRefresh() {
		grid.setSortOrder(UiUtil.convertSort(grid, sort));
	}

	public void refreshColumns() {
		grid.removeAllColumns();
		for (EntityProperty<T> property : properties) {
			if (property.getValueProvider() == null) {
				grid.addColumn(property.getName(), new TextRenderer(), NestedNullBehavior.ALLOW_NULLS).setCaption(property.getCaption())
						.setSortable(property.isSortable());
			}
			else {
				grid.addColumn(e -> property.getValueProvider().apply(e)).setId(property.getName())
						.setCaption(property.getCaption()).setSortable(property.isSortable());
			}
		}
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
		return grid.getSelectedItems();
	}

	public void setSelectedItems(Set<T> items) {
		grid.deselectAll();
		for (T item : items) {
			grid.select(item);
		}
	}

	public Optional<T> getFirstSelectedItem() {
		return grid.getSelectionModel().getFirstSelectedItem();
	}

	public void setSelectedItem(T item) {
		grid.select(item);
	}

	public GridSelectionModel<T> getSelectionModel() {
		return grid.getSelectionModel();
	}

	public void setSelectionMode(SelectionMode selectionMode) {
		grid.setSelectionMode(selectionMode);
	}

	public Registration addItemClickListener(ItemClickListener<? super T> listener) {
		return grid.addItemClickListener(listener);
	}

	public SearchFieldComponentDescriptor.Builder searchFieldComponentBuilder() {
		return SearchFieldComponentDescriptor.builder();
	}

	public EntityProperty.Builder<T> propertyBuilder() {
		return EntityProperty.<T>builder();
	}

	@Deprecated
	public void setSearchProvider(SearchProviderOld<T> sp) {
		searchProvider = (pageable, searchModel) -> sp.search(pageable.getPageNumber(), pageable.getPageSize(),
				pageable.getSort().toList().get(0).getProperty(), pageable.getSort().toList().get(0).getDirection(),
				searchModel);
	}

	public void setSearchProvider(SearchProvider<T> searchProvider) {
		this.searchProvider = searchProvider;
	}
}
