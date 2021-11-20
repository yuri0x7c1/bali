package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 * @param <P>
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class EntityCreateView<T, P> extends CommonView {

	Class<T> entityType;

	I18N i18n;

	AbstractForm<T> entityForm;

	Supplier<T> entityProvider;

	SavedHandler<T> savedHandler;

	ResetHandler<T> resetHandler;

	public EntityCreateView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm,
			Supplier<T> entityProvider, SavedHandler<T> savedHandler, ResetHandler<T> resetHandler) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;
		this.entityProvider = entityProvider;
		this.savedHandler = savedHandler;
		this.resetHandler = resetHandler;

		setHeaderText(this.getClass().getSimpleName());
		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back")));

		entityForm.setSavedHandler(savedHandler);
		entityForm.setResetHandler(resetHandler);
		addComponent(entityForm);
	}

	public EntityCreateView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm,
			Supplier<T> entityProvider, SavedHandler<T> savedHandler) {
		this(entityType, paramsType, i18n, entityForm, entityProvider, savedHandler, event -> {
			entityForm.setEntity(null);
			UiUtil.back();
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		T entity = entityProvider.get();
		if (entity != null) {
			entityForm.setEntity(entity);
		}
	}
}
