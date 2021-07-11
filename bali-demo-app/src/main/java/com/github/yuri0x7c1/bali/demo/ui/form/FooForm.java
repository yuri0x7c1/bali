package com.github.yuri0x7c1.bali.demo.ui.form;

import java.time.ZoneId;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.firitin.components.datepicker.VDatePicker;
import org.vaadin.firitin.components.datetimepicker.VDateTimePicker;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VBigDecimalField;
import org.vaadin.firitin.components.textfield.VNumberField;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.ui.editor.BarMultiEditor;
import com.github.yuri0x7c1.bali.demo.ui.picker.BarMultiPicker;
import com.github.yuri0x7c1.bali.demo.ui.picker.BarPicker;
import com.github.yuri0x7c1.bali.ui.data.converter.BigDecimalToLongConverter;
import com.github.yuri0x7c1.bali.ui.field.BooleanField;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooForm extends AbstractForm<Foo> {

	private static final long serialVersionUID = 1L;

	private I18N i18n;

    VTextField stringValue;

    VBigDecimalField longValue;

    VNumberField doubleValue;

    BooleanField booleanValue;

    VDateTimePicker date;

    VDateTimePicker localDateTime;

    VDatePicker localDate;

    BarPicker bar;

    BarMultiPicker linkedBars;

    BarMultiEditor nestedBars;

	public FooForm(I18N i18n, BarPicker bar, BarMultiPicker linkedBars, BarMultiEditor nestedBars) {
		super(Foo.class);
		this.i18n = i18n;
		this.bar = bar;
		this.linkedBars = linkedBars;
		this.nestedBars = nestedBars;

		// initialize stringValue
		stringValue = new VTextField(i18n.get("Foo.stringValue")).withFullWidth();

		// initialize longValue
		longValue = new VBigDecimalField(i18n.get("Foo.longValue")).withFullWidth();

		// initialize doubleValue
		doubleValue = new VNumberField(i18n.get("Foo.doubleValue")).withFullWidth();

		// initialize booleanValue
		booleanValue = new BooleanField(i18n.get("Foo.booleanValue"));
		booleanValue.setWidthFull();

		// initialize date
		date = new VDateTimePicker(i18n.get("Foo.date")).withFullWidth();

		// initialize localDateTime
		localDateTime = new VDateTimePicker(i18n.get("Foo.localDateTime")).withFullWidth();

		// initialize localDate
		localDate = new VDatePicker(i18n.get("Foo.localDate")).withFullWidth();

		// initialize bar
		bar.setLabel(i18n.get("Foo.bar"));
		bar.setWidthFull();

		// initialize linkedBars
		linkedBars.setLabel(i18n.get("Foo.linkedBars"));
		linkedBars.setWidthFull();

		// initialize nestedBars
		nestedBars.setLabel(i18n.get("Foo.nestedBars"));
		nestedBars.setWidthFull();

	}

	@Override
	protected void bind() {
		// bind stringValue
    	getBinder().forField(stringValue)
			.withValidator(new BeanValidator(Foo.class, "stringValue"))
			.bind(Foo::getStringValue, Foo::setStringValue);

		// bind longValue
    	getBinder().forField(longValue)
			.withConverter(new BigDecimalToLongConverter())
			.withValidator(new BeanValidator(Foo.class, "longValue"))
			.bind(Foo::getLongValue, Foo::setLongValue);

		// bind doubleValue
    	getBinder().forField(doubleValue)
			.withValidator(new BeanValidator(Foo.class, "doubleValue"))
			.bind(Foo::getDoubleValue, Foo::setDoubleValue);

		// bind booleanValue
    	getBinder().forField(booleanValue)
			.withValidator(new BeanValidator(Foo.class, "booleanValue"))
			.bind(Foo::getBooleanValue, Foo::setBooleanValue);

		// bind date
    	getBinder().forField(date)
			.withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
			.withValidator(new BeanValidator(Foo.class, "date"))
			.bind(Foo::getDate, Foo::setDate);

		// bind localDateTime
    	getBinder().forField(localDateTime)
			.withValidator(new BeanValidator(Foo.class, "localDateTime"))
			.bind(Foo::getLocalDateTime, Foo::setLocalDateTime);

		// bind localDate
    	getBinder().forField(localDate)
			.withValidator(new BeanValidator(Foo.class, "localDate"))
			.bind(Foo::getLocalDate, Foo::setLocalDate);

		// bind bar
    	getBinder().forField(bar)
			.withValidator(new BeanValidator(Foo.class, "bar"))
			.bind(Foo::getBar, Foo::setBar);

		// bind linkedBars
    	getBinder().forField(linkedBars)
			.withValidator(new BeanValidator(Foo.class, "linkedBars"))
			.bind(Foo::getLinkedBars, Foo::setLinkedBars);

		// bind nestedBars
    	getBinder().forField(nestedBars)
			.withValidator(new BeanValidator(Foo.class, "nestedBars"))
			.bind(Foo::getNestedBars, Foo::setNestedBars);

	}

	@Override
	protected Component createContent() {
		VVerticalLayout layout = new VVerticalLayout();

		layout.withComponent(stringValue);
		layout.withComponent(longValue);
		layout.withComponent(doubleValue);
		layout.withComponent(booleanValue);
		layout.withComponent(date);
		layout.withComponent(localDateTime);
		layout.withComponent(localDate);
		layout.withComponent(bar);
		layout.withComponent(linkedBars);
		layout.withComponent(nestedBars);

		HorizontalLayout toolbar = getToolbar();
		layout.withComponent(toolbar);

		return layout;
	}
}
