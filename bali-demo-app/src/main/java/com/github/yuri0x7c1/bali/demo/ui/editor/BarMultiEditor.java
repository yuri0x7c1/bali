package com.github.yuri0x7c1.bali.demo.ui.editor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.ui.form.BarForm;
import com.github.yuri0x7c1.bali.ui.editor.EntityMultiEditor;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Tag("bar-multi-editor")
public class BarMultiEditor extends EntityMultiEditor<Bar> {
	public BarMultiEditor(I18N i18n, BarForm form) {
		super(Bar.class, i18n, form);
	}
}
