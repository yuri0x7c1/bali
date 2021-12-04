package com.github.yuri0x7c1.bali.demo.ui.picker;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.picker.EntityPicker;
import com.vaadin.spring.annotation.SpringComponent;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BarPicker extends EntityPicker<Bar> {
	public BarPicker(I18N i18n, EntityDataGrid<Bar> dataGrid) {
		super(Bar.class, i18n, dataGrid);

		setValueCaptionGenerator(b -> b.getId() + "." + b.getValue());
	}
}
