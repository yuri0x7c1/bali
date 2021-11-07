package com.github.yuri0x7c1.bali.demo.projection;

import org.springframework.stereotype.Component;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.data.entity.EntityProjection;
import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.demo.domain.Foo;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class FooProjection extends EntityProjection<Foo> {

	I18N i18n;

	public FooProjection(I18N i18n) {
		super(Foo.class);
		this.i18n = i18n;

		addProperty(new EntityProperty<>(Foo.Fields.id, i18n.get("Foo.id")));
		addProperty(new EntityProperty<>(Foo.Fields.booleanValue, i18n.get("Foo.booleanValue"),
				e -> i18n.get(String.valueOf(e.getBooleanValue()))));
	}

}
