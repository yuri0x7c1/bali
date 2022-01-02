package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Function;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.SaveHandler;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.AccessLevel;
import lombok.Getter;
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

	@Getter
	private final AbstractForm<T> entityForm;

	@Setter
	private Function<P, T> entityProvider;

	@Getter
	@Setter
	private SaveHandler<T> saveHandler;

	@Getter
	@Setter
	private CancelHandler<T> cancelHandler;

	public EntityEditView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm) {
		super(paramsType);
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		setHeaderText(this.getClass().getSimpleName());

		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back"), e -> {
			entityForm.setEntity(null);
			UiUtil.back();
		}));

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
		if (getParams() != null) {
			T entity = entityProvider.apply(getParams());
			if (entity != null) {
				entityForm.setEntity(entity);
			}
		}
	}
}
