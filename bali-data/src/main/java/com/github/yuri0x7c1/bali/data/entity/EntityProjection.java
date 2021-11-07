package com.github.yuri0x7c1.bali.data.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityProjection<T> {
	Class<T> entityType;
	List<EntityProperty<T>> properties = new ArrayList<>();

	public EntityProjection(Class<T> entityType) {
		super();
		this.entityType = entityType;
	}

	public void addProperty(EntityProperty<T> property) {
		properties.add(property);
	}

	public List<EntityProperty<T>> getProperties() {
		return Collections.unmodifiableList(properties);
	}
}
