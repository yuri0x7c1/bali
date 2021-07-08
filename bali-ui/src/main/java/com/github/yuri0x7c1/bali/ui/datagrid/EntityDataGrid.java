package com.github.yuri0x7c1.bali.ui.datagrid;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;

import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.ui.qb.QueryBuilder;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;

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
@FieldDefaults(level=AccessLevel.PRIVATE)
public abstract class EntityDataGrid<T> extends VDiv {
    public interface SearchProvider<T> extends Serializable {
        Stream<T> search(QbModel criteria, PageRequest pageRequest);
    }

    public interface SearchCountProvider<T> extends Serializable {
        Long searchCount(QbModel criteria);
    }

    public interface EntityProcessor<T> extends Serializable {
    	T process(T entity);
    }

	final Class<T> entityType;

	@Getter
	@Setter
	SearchProvider<T> searchProvider;

	@Getter
	@Setter
	SearchCountProvider<T> searchCountProvider;

	@Getter
	@Setter
	EntityProcessor<T> entityProcessor;

	@Getter
	@Setter
	String defaultOrderProperty;

	@Getter
	@Setter
	Direction defaultOrderDirection = Direction.ASC;

	QueryBuilder qb;

	@Getter
	QbConfig qbConfig;

	@Getter
	QbModel qbModel;

	VGrid<T> grid;

	public EntityDataGrid(Class<T> entityType) {
		this.entityType = entityType;

		setSizeFull();

        // create query builder
		qbConfig = new QbConfig.Builder().build();

        qb = new QueryBuilder(qbConfig);
        qb.addSearchClickListener(event -> {
        	qbModel = qb.getModel();
        	list();
        });

		// entity grid
		grid = new VGrid<T>(entityType);

		// list entities
		list();

		// add components
		add(qb);
		add(grid);
	}

	protected void list() {
		grid.setItems(
			query -> {
				String property = getDefaultOrderProperty();
				Direction direction = defaultOrderDirection;
				if (CollectionUtils.isNotEmpty(query.getSortOrders())) {
					property = query.getSortOrders().get(0).getSorted();
					direction = SortDirection.ASCENDING.equals(query.getSortOrders().get(0).getDirection()) ? Direction.ASC : Direction.DESC;
				}

				Stream<T> entities = getSearchProvider().search(
					qbModel,
					PageRequest.of(
						query.getOffset() / query.getLimit(),
						query.getLimit(),
						direction,
						property
					)
				);

				if (entityProcessor != null) {
					entities = entities.map(e -> entityProcessor.process(e));
				}

				return entities;

			},
			query -> getSearchCountProvider().searchCount(qbModel).intValue()
		);
	}

	public void clearColumns() {
		grid.setColumns(new String[]{});
	}

	public void addColumn(String propertyName, String caption) {
		grid.addColumn(propertyName).setHeader(caption);
	}

	public void addColumn(ValueProvider<T, String> valueProvider, String caption) {
		grid.addColumn(valueProvider).setHeader(caption);
	}

	public void setQbConfig(QbConfig config) {
		if (config != null) {
			qb = new QueryBuilder(config);
			qb.addSearchClickListener(event -> {
				qbModel = qb.getModel();
				grid.getDataProvider().refreshAll();
			});
		}
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

	public Registration addItemClickListener(ComponentEventListener<ItemClickEvent<T>> listener) {
		return grid.addItemClickListener(listener);
	}
}
