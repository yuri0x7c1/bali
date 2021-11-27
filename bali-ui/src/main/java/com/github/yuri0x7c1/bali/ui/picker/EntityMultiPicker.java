package com.github.yuri0x7c1.bali.ui.picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityMultiPicker<T> extends CustomField<List<T>> {
	public static final String ACTIONS_COLUMN_ID = "_actions";

	final Class<T> entityType;

	final I18N i18n;

	final EntityDataGrid<T> dataGrid;

	MButton selectButton;

	MGrid<T> valueGrid;

	Window window;

	ConfirmButton deleteButton;

	MButton confirmButton;

	MButton cancelButton;

	List<T> value;

	boolean createEnabled = true;

	boolean deleteEnabled = true;

	@Getter
	@Setter
	String orderProperty;

	@Getter
	@Setter
	Direction orderDirection;

	final List<EntityProperty<T>> properties = new ArrayList<>();

	public EntityMultiPicker(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super();
		this.entityType = entityType;
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		addStyleName(BaliStyle.MULTI_PICKER);

		// window
		window = new Window();
        window.setCaption(i18n.get("Select"));
        window.setModal(true);
        window.setWidth("80%");
        window.setHeight("80%");

  		// grid
		dataGrid.setSelectionMode(SelectionMode.MULTI);

		// value grid
		valueGrid = new MGrid<>(entityType);
		valueGrid.setSelectionMode(SelectionMode.NONE);

		// add action columns
    	Column<T, MHorizontalLayout> c = valueGrid.addComponentColumn(entity -> {
    		MHorizontalLayout l = new MHorizontalLayout().withFullWidth();
    		if (deleteEnabled) {
    			MButton delete = new MButton(VaadinIcons.CLOSE, event -> {
					List<T> newValue = new ArrayList<>();
					newValue.addAll(getValue());
					newValue.remove(entity);
					setValue(Collections.unmodifiableList(newValue));
    			})
				.withDescription(i18n.get("Delete"))
				.withStyleName(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);
				l.add(delete);
    		}
    		return l;
    	});
    	c.setId(ACTIONS_COLUMN_ID);
    	c.setSortable(false);

		// select button
		selectButton = new MButton(VaadinIcons.PLUS)
			.withDescription(i18n.get("Select"))
			.withStyleName(
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON,
				BaliStyle.BUTTON_PRIMARY_FIX,
				ValoTheme.BUTTON_PRIMARY,
				ValoTheme.BUTTON_SMALL
			)
			.withListener(e -> {
				dataGrid.setSelectedItems(Collections.unmodifiableSet(new HashSet<T>(getValue())));
				getUI().addWindow(window);
			});

		// delete button
		deleteButton = new ConfirmButton()
			.withIcon(VaadinIcons.TRASH)
			.withDescription(i18n.get("Delete"))
			.setConfirmationText(i18n.get("Delete.confirm"))
			.withStyleName(
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON,
				ValoTheme.BUTTON_DANGER,
				ValoTheme.BUTTON_SMALL
			)
			.addClickListener(() -> {
				Set<T> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						List<T> newValue = new ArrayList<>();
						newValue.addAll(getValue());
						newValue.remove(selected.iterator().next());
						setValue(Collections.unmodifiableList(newValue));
					}
				}
			});

		// add buttons to header
		valueGrid.getDefaultHeaderRow().getCell(ACTIONS_COLUMN_ID).setComponent(
			new MHorizontalLayout(selectButton, deleteButton)
				.withFullWidth()
				.withMargin(false)
		);

		// window confirm button
		confirmButton = new MButton("Confirm")
			.withListener(e -> {window.close();
				if (!dataGrid.getSelectedItems().isEmpty()) {
					setValue(Collections.unmodifiableList(new ArrayList<>(dataGrid.getSelectedItems())));
				}
			})
			.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// window cancel button
		cancelButton = new MButton("Cancel")
			.withListener(e -> {
				window.close();
			})
			.withStyleName(ValoTheme.BUTTON_DANGER);

		// set window content
		window.setContent(new MVerticalLayout(
        	dataGrid,
        	new MHorizontalLayout(confirmButton, cancelButton))
        );

		// set ordering and  default property set from data grid
		orderProperty = dataGrid.getDefaultOrderProperty();
		orderDirection = dataGrid.getDefaultOrderDirection();
		setProperties(dataGrid.getProperties());
		refreshColumns();
	}

	@Override
	protected Component initContent() {
		return valueGrid;
	}

	@Override
	protected void doSetValue(List<T> value) {
		this.value = value;
		valueGrid.setItems(value);
	}

	@Override
	public List<T> getValue() {
		return value;
	}

	public List<EntityProperty<T>> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public void setProperties(List<EntityProperty<T>> properties) {
		this.properties.clear();
		this.properties.addAll(properties);
	}

	public void addProperty(EntityProperty<T> property) {
		properties.add(property);
	}

	public void addProperties(List<EntityProperty<T>> properties) {
		for (EntityProperty<T> p : properties) {
			addProperty(p);
		}
	}

	public void refreshColumns() {
		// clear columns
        for (Column<T, ?> column : valueGrid.getColumns()) {
        	if (!ACTIONS_COLUMN_ID.equals(column.getId())) {
        		valueGrid.removeColumn(column);
        	}
        }

		// property columns
		for (EntityProperty<T> property : properties) {
			if (property.getValueProvider() == null) {
				valueGrid.addColumn(property.getName()).setCaption(property.getCaption()).setSortable(property.isSortable());
			}
			else {
				valueGrid.addColumn(e -> property.getValueProvider().apply(e)).setId(property.getName())
						.setCaption(property.getCaption()).setSortable(property.isSortable());
			}
		}

		// sorg grid
		valueGrid.sort(orderProperty, UiUtil.convertDirection(orderDirection));
	}

	public void setHeaderVisble(boolean headerVisible) {
		valueGrid.setHeaderVisible(headerVisible);
	}

	/*
	public FooMultiPicker withCaption(String caption) {
		setCaption(caption);
		return this;
	}

	public FooMultiPicker clearColumns() {
		valueGrid.setColumns(new String[]{});
		return this;
	}

	public FooMultiPicker withColumn(String propertyName, String caption) {
		valueGrid.addColumn(propertyName).setCaption(caption);
		return this;
	}

	public FooMultiPicker withColumn(ValueProvider<T, String> valueProvider, String caption) {
		valueGrid.addColumn(valueProvider).setCaption(caption);
		return this;
	}

	public FooMultiPicker clearDataGridColumns() {
		// grid.clearColumns();
		return this;
	}

	public FooMultiPicker withDataGridColumn(String propertyName, String caption) {
		// grid.withColumn(propertyName, caption);
		return this;
	}

	public FooMultiPicker withDataGridColumn(ValueProvider<T, String> valueProvider, String caption) {
		// grid.withColumn(valueProvider, caption);
		return this;
	}

	public FooMultiPicker withHeaderVisble(boolean headerVisible) {
		valueGrid.setHeaderVisible(headerVisible);
		return this;
	}
	*/

}
