package com.github.yuri0x7c1.bali.ui.view;

import java.io.Serializable;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.ui.detail.EntityDetail;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

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
public class EntityShowView<T, P> extends ParametrizedView<P> {

	public interface EntityProvider<T, P> extends Serializable {
		T getEntity(P params);
	}

	Class<T> entityType;

	I18N i18n;

	EntityProvider<T, P> entityProvider;

	EntityDetail<T> entityDetail;

	public EntityShowView(Class<T> entityType, Class<P> paramsType, I18N i18n, EntityDetail<T> entityDetail,
			EntityProvider<T, P> entityProvider) {
		super(paramsType);
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityDetail = entityDetail;
		this.entityProvider = entityProvider;

		setHeaderText(this.getClass().getSimpleName());
		addHeaderComponent(new MButton(VaadinIcons.ARROW_LEFT, i18n.get("Back"), (ClickListener) event -> UiUtil.back())
				.withStyleName(ValoTheme.BUTTON_PRIMARY));

		addComponent(entityDetail);
	}

	@Override
	public void onEnter() {
		if (params != null) {
			T entity = entityProvider.getEntity(params);
			if (entity != null) {
				entityDetail.setEntity(entity);
			}
		}
	}
}
