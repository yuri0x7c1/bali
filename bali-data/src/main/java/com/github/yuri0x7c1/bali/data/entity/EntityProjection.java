package com.github.yuri0x7c1.bali.data.entity;

import java.util.ArrayList;
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
	List<EntityProperty> properties = new ArrayList<>();

	public EntityProjection(Class<T> entityType) {
		super();
		this.entityType = entityType;
	}
}
