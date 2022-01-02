package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.SaveHandler;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityCreateView<T> extends CommonView {

	final Class<T> entityType;

	final I18N i18n;

	final AbstractForm<T> entityForm;

	@Setter
	Supplier<T> entitySupplier;

	@Getter
	@Setter
	private SaveHandler<T> saveHandler;

	@Getter
	@Setter
	private CancelHandler<T> cancelHandler;

	public EntityCreateView(Class<T> entityType, I18N i18n, AbstractForm<T> entityForm) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		setHeaderText(this.getClass().getSimpleName());

		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back"), e -> {
			entityForm.setEntity(null);
			UiUtil.back();
		}));

		// default entity supplier
		setEntitySupplier(() -> {
			try {
				return entityType.newInstance();
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
		});

		// form save handler
		entityForm.setSavedHandler(e -> {
			if (saveHandler != null) {
				saveHandler.onSave(e);
			}
			entityForm.setEntity(null);
			UiUtil.back();
		});

		// form reset handler
		entityForm.setResetHandler(e -> {
			if (cancelHandler != null) {
				cancelHandler.onCancel(e);
			}
			entityForm.setEntity(null);
			UiUtil.back();
		});

		addComponent(entityForm);
	}

	@Override
	public void onEnter() {
		T entity = entitySupplier.get();
		if (entity != null) {
			entityForm.setEntity(entity);
		}
	}
}
