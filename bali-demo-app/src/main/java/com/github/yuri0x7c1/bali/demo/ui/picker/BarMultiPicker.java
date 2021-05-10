package com.github.yuri0x7c1.bali.demo.ui.picker;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.BarDataGrid;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.picker.EntityMultiPicker;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Tag("bar-multi-picker")
public class BarMultiPicker extends EntityMultiPicker<Bar> {
	public BarMultiPicker(I18N i18n, BarDataGrid dataGrid) {
		super(Bar.class, i18n, dataGrid);
	}
}
