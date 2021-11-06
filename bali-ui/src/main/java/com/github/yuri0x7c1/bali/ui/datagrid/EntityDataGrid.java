package com.github.yuri0x7c1.bali.ui.datagrid;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MPanel;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.search.model.SearchModel;
import com.github.yuri0x7c1.bali.ui.pagination.Pagination;
import com.github.yuri0x7c1.bali.ui.pagination.PaginationResource;
import com.github.yuri0x7c1.bali.ui.search.CommonSearchForm;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.GridSelectionModel;
import com.vaadin.ui.components.grid.ItemClickListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * The Class EntityDataGrid.
 *
 * @param <T> the generic type
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level=AccessLevel.PROTECTED)
public abstract class EntityDataGrid<T> extends MVerticalLayout {

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

	@Getter
	final SearchProvider<T> searchProvider;

	@Getter
	final SearchCountProvider<T> searchCountProvider;

	@Getter
	@Setter
	EntityProcessor<T> entityProcessor;

	final CommonSearchForm searchForm;

	MGrid<T> grid;

	@Getter
	@Setter
	Integer page = 0;

	@Getter
	@Setter
	Integer pageSize = 15;

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

	Pagination pagination;

	public EntityDataGrid(Class<T> entityType, I18N i18n, CommonSearchForm searchForm, String defaultOrderProperty, Direction defaultOrderDirection,
			SearchProvider<T> searchProvider, SearchCountProvider<T> searchCountProvider) {
		this.entityType = entityType;
		this.searchForm = searchForm;
		this.defaultOrderProperty = defaultOrderProperty;
		this.defaultOrderDirection = defaultOrderDirection;
		this.searchProvider = searchProvider;
		this.searchCountProvider = searchCountProvider;

		setSizeFull();

		// search form
		searchForm.setSearchHandler(() -> search());

		// pagination
		pagination = new Pagination(
			i18n,
			PaginationResource.newBuilder()
				.setPage(page)
				.setLimit(pageSize)
				.build()
		);
		pagination.setItemsPerPage(15, 25, 50, 75, 100);

		pagination.addPageChangeListener(event -> {
			page = event.page() - 1;
			pageSize = event.limit();

			search();
		});

		// entity grid
		grid = new MGrid<T>(entityType);
		grid.setHeightByRows(pageSize);

		// list entities
		search();

		// add components
		add(searchForm, new MPanel(grid).withFullWidth(), pagination);
	}

	protected void search() {
		Page<T> entityPage = searchProvider.search(page, pageSize, defaultOrderProperty, defaultOrderDirection,
				searchForm.getModel());
		pagination.setTotalCount(entityPage.getTotalElements());
		grid.setItems(entityPage.getContent());
	}

	public void clearColumns() {
		grid.setColumns(new String[]{});
	}

	public void addColumn(String propertyName, String caption) {
		grid.addColumn(propertyName).setCaption(caption);
	}

	public void addColumn(ValueProvider<T, String> valueProvider, String caption) {
		grid.addColumn(valueProvider).setCaption(caption);
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
}
