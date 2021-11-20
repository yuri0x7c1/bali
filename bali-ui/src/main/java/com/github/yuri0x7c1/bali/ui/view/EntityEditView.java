package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Function;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 * @param <P>
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityEditView<T, P> extends ParametrizedView<P> {

	private final Class<T> entityType;

	private final I18N i18n;

	private final AbstractForm<T> entityForm;

	@Setter
	private Function<P, T> entityProvider;

	public EntityEditView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm) {
		super(paramsType);
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		setHeaderText(this.getClass().getSimpleName());
		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back")));

		// default reset handler
		setResetHandler(e -> {
			entityForm.setEntity(null);
			UiUtil.back();
		});

		addComponent(entityForm);
	}

	public void setSavedHandler(SavedHandler<T> savedHandler) {
		entityForm.setSavedHandler(savedHandler);
	}

	public void setResetHandler(ResetHandler<T> resetHandler) {
		entityForm.setResetHandler(resetHandler);
	}

	@Override
	public void onEnter() {
		if (getParams() != null) {
			T entity = entityProvider.apply(getParams());
			if (entity != null) {
				entityForm.setEntity(entity);
			}
		}
	}
}
