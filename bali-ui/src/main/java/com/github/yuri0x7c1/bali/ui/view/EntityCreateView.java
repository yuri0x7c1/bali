package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.AccessLevel;
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

	SavedHandler<T> savedHandler;

	ResetHandler<T> resetHandler;

	public EntityCreateView(Class<T> entityType, I18N i18n, AbstractForm<T> entityForm) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		setHeaderText(this.getClass().getSimpleName());
		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back")));

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

		// default reset handler
		setResetHandler(e -> {
			entityForm.setEntity(null);
			UiUtil.back();
		});

		addComponent(entityForm);
	}

	public void setSavedHandler(SavedHandler<T> savedHandler) {
		this.savedHandler = savedHandler;
		entityForm.setSavedHandler(savedHandler);
	}

	public void setResetHandler(ResetHandler<T> resetHandler) {
		this.resetHandler = resetHandler;
		entityForm.setResetHandler(resetHandler);
	}

	@Override
	public void onEnter() {
		T entity = entitySupplier.get();
		if (entity != null) {
			entityForm.setEntity(entity);
		}
	}
}
