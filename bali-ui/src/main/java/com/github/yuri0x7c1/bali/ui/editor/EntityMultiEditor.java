package com.github.yuri0x7c1.bali.ui.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.ui.component.EntityMultiComponent;
import com.github.yuri0x7c1.bali.ui.form.EntityForm;
import com.github.yuri0x7c1.bali.ui.form.EntityForm.FormActionType;
import com.github.yuri0x7c1.bali.ui.handler.AddHandler;
import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.CloseHandler;
import com.github.yuri0x7c1.bali.ui.handler.ConfirmHandler;
import com.github.yuri0x7c1.bali.ui.handler.EditHandler;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.ui.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class EntityMultiEditor<T> extends EntityMultiComponent<T> {

	@Getter
	private final AbstractForm<T> entityForm;

	@Getter
	private Supplier<T> entitySupplier;

	public EntityMultiEditor(Class<T> entityType, I18N i18n, AbstractForm<T> entityForm) {
		super(entityType, i18n);
		this.entityForm = entityForm;

		addStyleName(BaliStyle.MULTI_EDITOR);

		setAddMessage(getI18n().get("Create"));

		entitySupplier = initEntitySupplier();
		setAddHandler(initAddHandler());
		setEditHandler(initEditHandler());
		setCloseHandler(initCloseHandler());
		setConfirmHandler(initConfirmHandler());
		setCancelHandler(initCancelHandler());

		// confirm button
		entityForm.setSavedHandler(entity -> {
			getConfirmHandler().onConfirm(entity);
		});

		// cancel button
		entityForm.setResetHandler(entity -> {
			getCancelHandler().onCancel(entity);
		});
	}

	protected Supplier<T> initEntitySupplier() {
		return () -> {
			try {
				return getEntityType().newInstance();
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
		};
	}

	protected AddHandler<T> initAddHandler() {
		return () -> {
			if (entityForm instanceof EntityForm) {
				((EntityForm<T>) entityForm).setActionType(FormActionType.CREATE);
			}
			entityForm.setEntity(entitySupplier.get());
			getWindow().setCaption(getAddMessage());
			getUI().addWindow(getWindow());
		};
	}

	protected EditHandler<T> initEditHandler() {
		return entity -> {
			if (entityForm instanceof EntityForm) {
				((EntityForm<T>) entityForm).setActionType(FormActionType.EDIT);
			}
			entityForm.setEntity(entity);
			getWindow().setCaption(getI18n().get(getAddMessage()));
			getUI().addWindow(getWindow());
		};
	}

	protected CloseHandler initCloseHandler() {
		return () -> entityForm.setEntity(null);
	}

	protected ConfirmHandler<T> initConfirmHandler() {
		return entity -> {
			if (getValue().contains(entityForm.getEntity())) {
				getValueGrid().getDataProvider().refreshItem(entityForm.getEntity());
			}
			else {
				List<T> newValue = new ArrayList<>(getValue());
				newValue.add(entityForm.getEntity());
				setValue(Collections.unmodifiableList(newValue));
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
		return new MVerticalLayout(entityForm);
	}
}
