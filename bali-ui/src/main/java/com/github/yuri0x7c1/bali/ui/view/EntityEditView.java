package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.github.yuri0x7c1.bali.ui.provider.EntityProvider;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;

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
public class EntityEditView<T, P> extends ParametrizedView<P> {

	Class<T> entityType;

	I18N i18n;

	AbstractForm<T> entityForm;

	EntityProvider<T, P> entityProvider;

	SavedHandler<T> savedHandler;

	ResetHandler<T> resetHandler;

	public EntityEditView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm,
			EntityProvider<T, P> entityProvider, SavedHandler<T> savedHandler, ResetHandler<T> resetHandler) {
		super(paramsType);
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

	public EntityEditView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm,
			EntityProvider<T, P> entityProvider, SavedHandler<T> savedHandler) {
		this(entityType, paramsType, i18n, entityForm, entityProvider, savedHandler, event -> {
			entityForm.setEntity(null);
			UiUtil.back();
		});
	}

	@Override
	public void onEnter() {
		if (params != null) {
			T entity = entityProvider.getEntity(params);
			if (entity != null) {
				entityForm.setEntity(entity);
			}
		}
	}
}
