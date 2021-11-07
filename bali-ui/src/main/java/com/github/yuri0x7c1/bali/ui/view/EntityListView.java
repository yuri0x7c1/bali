package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.handler.CreateHandler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

/**
 * Entity list view
 *
 * @author yuri0x7c1
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class EntityListView<T> extends CommonView {

    Class<T> entityType;

	I18N i18n;

	@Getter
	EntityDataGrid<T> dataGrid;

	MButton createButton;

	@Getter
	@NonFinal
	CreateHandler<T> createHandler;

	public EntityListView(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super();
		this.entityType = entityType;
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		// create button
		createButton = new MButton(i18n.get("Create")).withVisible(false);
		addHeaderComponent(createButton);

		add(dataGrid);
	}

	public void setCreateHandler(CreateHandler createHandler) {
		this.createHandler = createHandler;
		createButton.addClickListener(event -> createHandler.onCreate());
		createButton.setVisible(true);
	}
}
