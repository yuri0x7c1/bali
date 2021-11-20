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
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.grid.MGrid;
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
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.GridSelectionModel;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class EntityDataGrid<T> extends MVerticalLayout {

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

	final MGrid<T> grid;

	final Pagination pagination;

	Column<T, MHorizontalLayout> actionsColumn;

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

	final List<EntityProperty<T>> properties = new ArrayList<>();

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

		// search form
		searchForm.setSearchHandler(() -> refresh());

		// entity grid
		grid = new MGrid<T>(entityType);
		grid.removeAllColumns();
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setWidthFull();
		grid.setHeightByRows(pageSize);

		grid.addSortListener(event -> {
			orderDirection = UiUtil.getGridOrderDirection(grid, defaultOrderDirection);
			orderProperty = UiUtil.getGridOrderProperty(grid, defaultOrderProperty);
			refresh();
		});

		UI.getCurrent().getPage().addBrowserWindowResizeListener(event -> {
           grid.recalculateColumnWidths();
        });

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
			grid.setHeightByRows(pageSize);
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

	private void refreshActionsColumn() {
        if (showHandler != null || editHandler != null || deleteHandler != null) {
        	if (grid.getColumn(ACTIONS_COLUMN_ID) != null) {
        		grid.removeColumn(ACTIONS_COLUMN_ID);
        	}
        	Column<T, MHorizontalLayout> c = grid.addComponentColumn(entity -> {
        		MHorizontalLayout l = new MHorizontalLayout().withFullWidth();
        		if (showHandler != null) {
        			MButton show = new MButton(VaadinIcons.EYE, event -> {
        				showHandler.onShow(entity);
        			})
        			.withDescription(i18n.get("Show"))
					.withStyleName(ValoTheme.BUTTON_SMALL);
        			l.add(show);
        		}
        		if (editHandler != null) {
        			MButton edit = new MButton(VaadinIcons.PENCIL, event -> {
        				editHandler.onEdit(entity);
        			})
					.withDescription(i18n.get("Edit"))
					.withStyleName(
						BaliStyle.BUTTON_PRIMARY_FIX,
						ValoTheme.BUTTON_PRIMARY,
						ValoTheme.BUTTON_SMALL
					);
        			l.add(edit);
        		}
        		if (deleteHandler != null) {
        			MButton delete = new MButton(VaadinIcons.CLOSE, event -> {
        				deleteHandler.onDelete(entity);
        			})
					.withDescription(i18n.get("Delete"))
					.withStyleName(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);
        			l.add(delete);
        		}
        		return l;
        	});
        	c.setId(ACTIONS_COLUMN_ID);
        	c.setWidth(100);
        	c.setSortable(false);
        	grid.setColumnOrder(c);
        }
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
		Page<T> entityPage = searchProvider.search(page, pageSize, orderProperty, orderDirection,
				searchForm.getModel());
		pagination.setTotalCount(entityPage.getTotalElements());
		grid.setItems(entityPage.getContent());
	}

	public void refreshColumns() {
		grid.removeAllColumns();
		for (EntityProperty<T> property : properties) {
			if (property.getValueProvider() == null) {
				grid.addColumn(property.getName()).setCaption(property.getCaption()).setSortable(property.isSortable());
			}
			else {
				grid.addColumn(e -> property.getValueProvider().apply(e)).setId(property.getName())
						.setCaption(property.getCaption()).setSortable(property.isSortable());
			}
		}
		refreshActionsColumn();
		grid.sort(orderProperty, UiUtil.convertDirection(orderDirection));
	}

	public void addProperty(EntityProperty<T> property) {
		properties.add(property);
	}

	public void addProperties(List<EntityProperty<T>> properties) {
		for (EntityProperty<T> p : properties) {
			addProperty(p);
		}
	}

	public List<EntityProperty<T>> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public void registerSearchFieldComponent(SearchFieldComponentDescriptor fieldComponent) {
		searchForm.registerFieldComponent(fieldComponent);
	}

	public void addSearchField(String fieldName, SearchFieldOperator operator, Object value) {
		searchForm.createFieldComponent(fieldName, operator, value);
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
