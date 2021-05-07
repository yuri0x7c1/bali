package com.github.yuri0x7c1.bali.demo.ui.datagrid;

import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort.Direction;

import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.github.yuri0x7c1.bali.datagrid.ui.EntityDataGrid;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooDataGrid extends EntityDataGrid<Foo> {

	public static final String DEFAULT_ORDER_PROPERTY = "id";

	public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;

	final I18N i18n;

	final FooService fooService;

	public FooDataGrid(I18N i18n, FooService fooService) {
		super(Foo.class);
		this.i18n = i18n;
		this.fooService = fooService;

		// entity grid default columns
		clearColumns();
		addColumn("id", i18n.get("Foo.id"));
		addColumn("stringValue", i18n.get("Foo.stringValue"));
		addColumn("longValue", i18n.get("Foo.longValue"));
		addColumn("doubleValue", i18n.get("Foo.doubleValue"));
		addColumn("booleanValue", i18n.get("Foo.booleanValue"));
		addColumn("date", i18n.get("Foo.date"));
		addColumn("instant", i18n.get("Foo.instant"));
		addColumn("localDateTime", i18n.get("Foo.localDateTime"));
		addColumn("zonedDateTime", i18n.get("Foo.zonedDateTime"));
		addColumn("localDate", i18n.get("Foo.localDate"));
		addColumn("bar", i18n.get("Foo.bar"));
		addColumn(
			f -> f.getLinkedBars().stream().map(l -> l.toString()).collect(Collectors.joining(", ")),
			i18n.get("Foo.linkedBars")
		);

        // query builder
		setQbConfig(new QbConfig.Builder()
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
			.build()
		);

		setDefaultOrderProperty(DEFAULT_ORDER_PROPERTY);
		setDefaultOrderDirection(DEFAULT_ORDER_DIRECTION);

		setSearchProvider((criteria, pageRequest) -> fooService.search(criteria, pageRequest).stream());
		setSearchCountProvider(criteria -> fooService.searchCount(criteria));
	}

}