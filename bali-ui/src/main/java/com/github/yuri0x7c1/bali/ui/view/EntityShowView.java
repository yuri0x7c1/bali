package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Function;

import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.ui.detail.EntityDetail;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 * @param <P>
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityShowView<T, P> extends ParametrizedView<P> {

	Class<T> entityType;

	I18N i18n;

	@Getter
	EntityDetail<T> entityDetail;

	Function<P, T> entityProvider;

	public EntityShowView(Class<T> entityType, Class<P> paramsType, I18N i18n, EntityDetail<T> entityDetail,
			Function<P, T> entityProvider) {
		super(paramsType);
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityDetail = entityDetail;
		this.entityProvider = entityProvider;

		setHeaderText(this.getClass().getSimpleName());
		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back")));

		addComponent(entityDetail);
	}

	@Override
	public void onEnter() {
		if (getParams() != null) {
			T entity = entityProvider.apply(getParams());
			if (entity != null) {
				entityDetail.setEntity(entity);
			}
		}
	}
}
