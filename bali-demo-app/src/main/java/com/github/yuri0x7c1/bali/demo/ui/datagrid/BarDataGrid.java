package com.github.yuri0x7c1.bali.demo.ui.datagrid;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort.Direction;

import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.service.BarService;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BarDataGrid extends EntityDataGrid<Bar> {

	I18N i18n;

	BarService barService;

	public BarDataGrid(I18N i18n, BarService barService) {
		super(Bar.class);
		this.i18n = i18n;
		this.barService = barService;

		// entity grid default columns
		clearColumns();
		addColumn("id", i18n.get("Bar.id"));
		addColumn("value", i18n.get("Bar.value"));

        // query builder config
		setQbConfig(new QbConfig.Builder()
			.addField(new QbField("id", QbType.INTEGER, i18n.get("Bar.id")))
			.addField(new QbField("value", QbType.STRING, i18n.get("Bar.value")))
			.build()
		);

		setDefaultOrderProperty("id");
		setDefaultOrderDirection(Direction.ASC);

		setSearchProvider((criteria, pageRequest) -> barService.search(criteria, pageRequest).stream());
		setSearchCountProvider(criteria -> barService.searchCount(criteria));
	}
}
