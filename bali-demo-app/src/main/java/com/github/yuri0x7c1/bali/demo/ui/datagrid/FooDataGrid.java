package com.github.yuri0x7c1.bali.demo.ui.datagrid;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.fields.IntegerField;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.data.search.util.SearchUtil;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.search.CommonSearchForm;
import com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentDescriptor;
import com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentLifecycle;
import com.vaadin.spring.annotation.SpringComponent;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooDataGrid extends EntityDataGrid<Foo> {

	FooService fooService;

	public FooDataGrid(I18N i18n, CommonSearchForm searchForm, FooService fooService) {
		super(Foo.class, i18n, searchForm, Foo.Fields.id, Direction.ASC);
		this.fooService = fooService;

		// search provider
		setSearchProvider((page, pageSize, orderProperty, orderDirection, searchModel) ->
			fooService.findAll(
				SearchUtil.buildSpecification(Foo.class, searchModel),
				PageRequest.of(
					page,
					pageSize,
					orderDirection,
					orderProperty
				)
			)
		);

		// search count provider
		setSearchCountProvider(searchModel -> fooService.count(SearchUtil.buildSpecification(Foo.class, searchModel)));

		// search fields
		registerSearchFieldComponent(new SearchFieldComponentDescriptor("id", i18n.get("Foo.id"), Integer.class, IntegerField.class,
				SearchFieldComponentLifecycle.NON_MANAGED));

		// grid columns
		addProperty(new EntityProperty<>("id", i18n.get("Foo.id")));
		refreshColumns();
	}
}
