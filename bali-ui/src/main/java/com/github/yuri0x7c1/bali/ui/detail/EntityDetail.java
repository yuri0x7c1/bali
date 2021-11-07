package com.github.yuri0x7c1.bali.ui.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MPanel;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.vaadin.shared.ui.ContentMode;

import lombok.AccessLevel;
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
public class EntityDetail<T> extends MPanel {

	final Class<T> entityType;

	final List<EntityProperty<T>> properties = new ArrayList<>();

	T entity;

	MVerticalLayout content = new MVerticalLayout();

	public EntityDetail(Class<T> entityType) {
		super();
		this.entityType = entityType;

		setWidthFull();
		setContent(content);
	}

	public void addProperty(EntityProperty<T> property) {
		properties.add(property);
	}

	public void setEntity(T entity) {
		Objects.requireNonNull(entity);
		this.entity = entity;
		content.removeAllComponents();
		for(EntityProperty<T> p : properties) {
			try {
				MLabel rowLabel = new MLabel(
					"<b>" + p.getCaption() + "</b>: " + BeanUtils.getProperty(entity, p.getName())
				).withContentMode(ContentMode.HTML);
				content.addComponent(rowLabel);
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}
}
