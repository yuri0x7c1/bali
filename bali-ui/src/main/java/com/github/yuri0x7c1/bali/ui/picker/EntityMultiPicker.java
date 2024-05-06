package com.github.yuri0x7c1.bali.ui.picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.ui.component.EntityMultiComponent;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.handler.AddHandler;
import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.CloseHandler;
import com.github.yuri0x7c1.bali.ui.handler.ConfirmHandler;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

public abstract class EntityMultiPicker<T> extends EntityMultiComponent<T> {

	private final EntityDataGrid<T> dataGrid;

	private final MButton confirmButton;

	private final MButton cancelButton;

	public EntityMultiPicker(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super(entityType, i18n);
		this.dataGrid = dataGrid;

		addStyleName(BaliStyle.MULTI_EDITOR);

  		// grid
		dataGrid.setSelectionMode(SelectionMode.MULTI);

		// init handlers
		setAddHandler(initAddHandler());
		setCloseHandler(initCloseHandler());
		setConfirmHandler(initConfirmHandler());
		setCancelHandler(initCancelHandler());

		// window confirm button
		confirmButton = new MButton(i18n.get("Confirm"), e -> {
			if (!dataGrid.getSelectedItems().isEmpty()) {
				setValue(Collections.unmodifiableList(new ArrayList<>(dataGrid.getSelectedItems())));
			}
			getWindow().close();
		})
		.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// window cancel button
		cancelButton = new MButton(i18n.get("Cancel"), e -> {
			getWindow().close();
		});
	}

	@Override
	protected List<EntityProperty<T>> initProperties() {
		return dataGrid.getProperties();
	}

	protected AddHandler<T> initAddHandler() {
		return () -> {
			dataGrid.sortAndRefresh();
			dataGrid.setSelectedItems(Collections.unmodifiableSet(new HashSet<T>(getValue())));
			getWindow().setCaption(getAddMessage());
			getUI().addWindow(getWindow());
		};
	}

	protected CloseHandler initCloseHandler() {
		return () -> {
			dataGrid.clearSearchFields();
			dataGrid.setPage(0);
		};
	}

	protected ConfirmHandler<T> initConfirmHandler() {
		return entity -> {
			if (!dataGrid.getSelectedItems().isEmpty()) {
				setValue(Collections.unmodifiableList(new ArrayList<>(dataGrid.getSelectedItems())));
			}
			getWindow().close();
		};
	}

	protected CancelHandler<T> initCancelHandler() {
		return entity -> {
			getWindow().close();
		};
	}

	@Override
	protected Component initWindowContent() {
		return new MVerticalLayout(dataGrid, new MHorizontalLayout(confirmButton, cancelButton));
	}
}
