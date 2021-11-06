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

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.github.yuri0x7c1.bali.qb.ui.component.QueryBuilder;
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
public class FooDataGrid extends MCssLayout {

	public static final String DEFAULT_ORDER_PROPERTY = "id";

	public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;

	final I18N i18n;

	final UIEventBus eventBus;

	final FooService fooService;

	QueryBuilder qb;

	QbConfig qbConfig;

	QbModel qbModel;

	MGrid<Foo> grid;

	@PostConstruct
	public void init() {
		setSizeFull();

        // query builder
		qbConfig = new QbConfig.Builder()
			.addField(new QbField("id", QbType.INTEGER, i18n.get("Foo.id")))
			.addField(new QbField("stringValue", QbType.STRING, i18n.get("Foo.stringValue")))
			.addField(new QbField("longValue", QbType.LONG, i18n.get("Foo.longValue")))
			.addField(new QbField("doubleValue", QbType.DOUBLE, i18n.get("Foo.doubleValue")))
			.addField(new QbField("booleanValue", QbType.BOOLEAN, i18n.get("Foo.booleanValue")))
			.addField(new QbField("date", QbType.DATE, i18n.get("Foo.date")))
			.addField(new QbField("instant", QbType.DATETIME, i18n.get("Foo.instant")))
			.addField(new QbField("localDateTime", QbType.DATETIME, i18n.get("Foo.localDateTime")))
			.addField(new QbField("zonedDateTime", QbType.DATETIME, i18n.get("Foo.zonedDateTime")))
			.addField(new QbField("localDate", QbType.DATE, i18n.get("Foo.localDate")))
			.build();

        qb = new QueryBuilder(qbConfig);
        qb.addSearchClickListener(event -> {
        	qbModel = qb.getModel();
        	list();
        });

		// entity grid
		grid = new MGrid<>(Foo.class)
			.withFullSize()
			.withStyleName("common-table");

		// entity grid default columns
		clearColumns();
		withColumn("id", i18n.get("Foo.id"));
		withColumn("stringValue", i18n.get("Foo.stringValue"));
		withColumn("longValue", i18n.get("Foo.longValue"));
		withColumn("doubleValue", i18n.get("Foo.doubleValue"));
		withColumn("booleanValue", i18n.get("Foo.booleanValue"));
		withColumn("date", i18n.get("Foo.date"));
		withColumn("instant", i18n.get("Foo.instant"));
		withColumn("localDateTime", i18n.get("Foo.localDateTime"));
		withColumn("zonedDateTime", i18n.get("Foo.zonedDateTime"));
		withColumn("localDate", i18n.get("Foo.localDate"));
		withColumn("bar", i18n.get("Foo.bar"));
		withColumn(
			f -> f.getLinkedBars().stream().map(l -> l.toString()).collect(Collectors.joining(", ")),
			i18n.get("Foo.linkedBars")
		);

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

				Page<Foo> foos = fooService.search(qbModel, PageRequest.of(
					offset / limit,
					limit,
					direction,
					property
				));

				return foos.stream().map(foo -> fooService.init(foo));
			},
			() -> Long.valueOf(fooService.searchCount(qbModel)).intValue()
		);
	}

	public FooDataGrid clearColumns() {
		grid.setColumns(new String[]{});
		return this;
	}

	public FooDataGrid withColumn(String propertyName, String caption) {
		grid.addColumn(propertyName).setCaption(caption);
		return this;
	}

	public FooDataGrid withColumn(ValueProvider<Foo, String> valueProvider, String caption) {
		grid.addColumn(valueProvider).setCaption(caption);
		return this;
	}

	public FooDataGrid withConfig(QbConfig config) {
		if (config != null) {
			qb = new QueryBuilder(config);
			qb.addSearchClickListener(event -> {
				qbModel = qb.getModel();
				grid.getDataProvider().refreshAll();
			});
		}
		return this;
	}

	public Set<Foo> getSelectedItems() {
		return grid.getSelectedItems();
	}

	public Optional<Foo> getFirstSelectedItem() {
		return grid.getSelectionModel().getFirstSelectedItem();
	}

	public GridSelectionModel<Foo> getSelectionModel() {
		return grid.getSelectionModel();
	}

	public void setSelectionMode(SelectionMode selectionMode) {
		grid.setSelectionMode(selectionMode);
	}

	public Registration addItemClickListener(ItemClickListener<Foo> listener) {
		return grid.addItemClickListener(listener);
	}
}