package com.github.yuri0x7c1.bali.demo.ui.form;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BarForm extends AbstractForm<Bar> {

	private static final long serialVersionUID = 1L;

	private I18N i18n;

    VTextField value;

	public BarForm(I18N i18n) {
		super(Bar.class);
		this.i18n = i18n;

		// initialize value
		value = new VTextField(i18n.get("Bar.value"));

	}

	@Override
	protected void bind() {
		// bind value
    	getBinder().forField(value)
			.withValidator(new BeanValidator(Bar.class, Bar.Fields.value))
			.bind(Bar::getValue, Bar::setValue);

	}

	@Override
	protected Component createContent() {
		VVerticalLayout layout = new VVerticalLayout();

		layout.withComponent(value);

		HorizontalLayout toolbar = getToolbar();
		layout.withComponent(toolbar);

		return layout;
	}
}
