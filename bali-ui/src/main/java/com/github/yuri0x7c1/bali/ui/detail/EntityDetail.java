/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.ui.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	public void addProperties(List<EntityProperty<T>> properties) {
		this.properties.addAll(properties);
	}

	public void setEntity(T entity) {
		Objects.requireNonNull(entity);
		this.entity = entity;
		content.removeAllComponents();
		for(EntityProperty<T> p : properties) {
			try {
				MLabel rowLabel = new MLabel(
					"<b>" + p.getCaption() + "</b>: " + p.getValueAsString(entity)
				).withContentMode(ContentMode.HTML);
				content.addComponent(rowLabel);
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}
}
