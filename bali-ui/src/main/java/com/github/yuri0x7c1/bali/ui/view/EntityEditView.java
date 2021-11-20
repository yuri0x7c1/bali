package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Function;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.Setter;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 * @param <P>
 */
public class EntityEditView<T, P> extends ParametrizedView<P> {

	protected final Class<T> entityType;

	protected final I18N i18n;

	protected final AbstractForm<T> entityForm;

	@Setter
	private Function<P, T> entityProvider;

	private SavedHandler<T> savedHandler;

	private ResetHandler<T> resetHandler;

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
		this.savedHandler = savedHandler;
		entityForm.setSavedHandler(savedHandler);
	}

	public void setResetHandler(ResetHandler<T> resetHandler) {
		this.resetHandler = resetHandler;
		entityForm.setResetHandler(resetHandler);
	}

	@Override
	public void onEnter() {
		if (params != null) {
			T entity = entityProvider.apply(params);
			if (entity != null) {
				entityForm.setEntity(entity);
			}
		}
	}
}
