package com.github.yuri0x7c1.bali.demo.ui.datagrid;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MCssLayout;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.service.BarService;
import com.github.yuri0x7c1.bali.ui.qb.component.QueryBuilder;
import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.Registration;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.GridSelectionModel;
import com.vaadin.ui.components.grid.ItemClickListener;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class BarDataGrid extends MCssLayout {

	public static final String DEFAULT_ORDER_PROPERTY = "id";

	public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;

	final I18N i18n;

	final UIEventBus eventBus;

	final BarService barService;

	QueryBuilder qb;

	QbConfig qbConfig;

	QbModel qbModel;

	MGrid<Bar> grid;

	@PostConstruct
	public void init() {
		setSizeFull();

        // query builder
		qbConfig = new QbConfig.Builder()
			.addField(new QbField("id", QbType.INTEGER, i18n.get("Bar.id")))
			.addField(new QbField("value", QbType.STRING, i18n.get("Bar.value")))
			.build();

        qb = new QueryBuilder(qbConfig);
        qb.addSearchClickListener(event -> {
        	qbModel = qb.getModel();
        	list();
        });

		// entity grid
		grid = new MGrid<>(Bar.class)
			.withFullSize()
			.withStyleName("common-table");

		// entity grid default columns
		clearColumns();
		withColumn("id", i18n.get("Bar.id"));
		withColumn("value", i18n.get("Bar.value"));

		// list entities
		list();

		// add components
		addComponent(qb);
		addComponent(grid);

		// subscribe to events
		this.eventBus.subscribe(this);
	}

	protected void list() {
		grid.setDataProvider(
			(sortOrders, offset, limit) -> {
				String property = DEFAULT_ORDER_PROPERTY;
				Direction direction = DEFAULT_ORDER_DIRECTION;
				if (CollectionUtils.isNotEmpty(sortOrders)) {
					property = sortOrders.get(0).getSorted();
					direction = SortDirection.ASCENDING.equals(sortOrders.get(0).getDirection()) ? Direction.ASC : Direction.DESC;
				}

				Page<Bar> bars = barService.search(qbModel, PageRequest.of(
					offset / limit,
					limit,
					direction,
					property
				));

				return bars.stream().map(bar -> barService.init(bar));
			},
			() -> Long.valueOf(barService.searchCount(qbModel)).intValue()
		);
	}

	public BarDataGrid clearColumns() {
		grid.setColumns(new String[]{});
		return this;
	}

	public BarDataGrid withColumn(String propertyName, String caption) {
		grid.addColumn(propertyName).setCaption(caption);
		return this;
	}

	public BarDataGrid withColumn(ValueProvider<Bar, String> valueProvider, String caption) {
		grid.addColumn(valueProvider).setCaption(caption);
		return this;
	}

	public BarDataGrid withConfig(QbConfig config) {
		if (config != null) {
			qb = new QueryBuilder(config);
			qb.addSearchClickListener(event -> {
				qbModel = qb.getModel();
				grid.getDataProvider().refreshAll();
			});
		}
		return this;
	}

	public Set<Bar> getSelectedItems() {
		return grid.getSelectedItems();
	}

	public Optional<Bar> getFirstSelectedItem() {
		return grid.getSelectionModel().getFirstSelectedItem();
	}

	public GridSelectionModel<Bar> getSelectionModel() {
		return grid.getSelectionModel();
	}

	public void setSelectionMode(SelectionMode selectionMode) {
		grid.setSelectionMode(selectionMode);
	}

	public Registration addItemClickListener(ItemClickListener<Bar> listener) {
		return grid.addItemClickListener(listener);
	}
}