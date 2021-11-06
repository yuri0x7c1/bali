package com.github.yuri0x7c1.bali.ui.view;

import java.util.Optional;
import java.util.function.Consumer;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.DeleteButton;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;

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
	EntityDataGrid<T> entityDataGrid;

	MButton createButton;

	MButton showButton;

	MButton editButton;

	DeleteButton deleteButton;

	@Getter
	@NonFinal
	Optional<Runnable> createHandler = Optional.empty();

	@Getter
	@NonFinal
	Optional<Consumer<T>> showHandler= Optional.empty();

	@Getter
	@NonFinal
	Optional<Consumer<T>> editHandler= Optional.empty();

	@Getter
	@NonFinal
	Optional<Consumer<T>> deleteHandler= Optional.empty();

	public EntityListView(Class<T> entityType, I18N i18n, EntityDataGrid<T> entityDataGrid) {
		super();
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityDataGrid = entityDataGrid;

		// create button
		createButton = new MButton(i18n.get("Create"), e -> create()).withVisible(false);

		// show button
		showButton = new MButton(i18n.get("Show"), e -> show()).withVisible(false);

		// edit button
		editButton = new MButton(i18n.get("Edit"), e -> edit()).withVisible(false);

		// delete button
		deleteButton = new DeleteButton(i18n.get("Delete"), "", event -> delete());

		addHeaderComponent(createButton);
		addHeaderComponent(showButton);
		addHeaderComponent(editButton);
		addHeaderComponent(deleteButton);

		add(entityDataGrid);
	}

	private void create() {
		createHandler.ifPresent(h -> h.run());
	}

	private void show() {
		showHandler.ifPresent(h ->
			entityDataGrid.getFirstSelectedItem().ifPresent(
				item -> h.accept(item)
			)
		);
	}

	private void edit() {
		editHandler.ifPresent(h ->
			entityDataGrid.getFirstSelectedItem().ifPresent(
				item -> h.accept(item)
			)
		);
	}

	private void delete() {
		deleteHandler.ifPresent(h ->
			entityDataGrid.getFirstSelectedItem().ifPresent(
				item -> h.accept(item)
			)
		);
	}

	public void setCreateHandler(Runnable createHandler) {
		this.createHandler = Optional.of(createHandler);
		createButton.setVisible(true);
	}

	public void setShowHandler(Consumer<T> showHandler) {
		this.showHandler = Optional.of(showHandler);
		showButton.setVisible(true);
	}

	public void setEditHandler(Consumer<T> editHandler) {
		this.editHandler = Optional.of(editHandler);
		editButton.setVisible(true);
	}

	public void setDeleteHandler(Consumer<T> deleteHandler) {
		this.deleteHandler = Optional.of(deleteHandler);
		deleteButton.setVisible(true);
	}
}