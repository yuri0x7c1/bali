package com.github.yuri0x7c1.bali.demo.ui.form;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.components.textfield.VTextField;
import org.vaadin.firitin.form.AbstractForm;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.ui.picker.BarMultiPicker;
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
public class FooForm extends AbstractForm<Foo> {

	private static final long serialVersionUID = 1L;

	private I18N i18n;

	// private UIEventBus eventBus;

    VTextField stringValue;

    /*

    VLongField longValue;

    DoubleField doubleValue;

    BooleanField booleanValue;

    DateTimeField date;

    DateTimeField localDateTime;

    DateField localDate;

    BarSelect bar;
    */

    BarMultiPicker linkedBars;

	public FooForm(I18N i18n, /* UIEventBus eventBus, BarSelect bar,*/ BarMultiPicker linkedBars) {
		super(Foo.class);

		this.i18n = i18n;
//		this.eventBus = eventBus;
//		this.bar = bar;
		this.linkedBars = linkedBars;

		// initialize stringValue
		stringValue = new VTextField(i18n.get("Foo.stringValue"));

		/*
		// initialize longValue
		longValue = new LongField(i18n.get("Foo.longValue"));
		longValue.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize doubleValue
		doubleValue = new DoubleField(i18n.get("Foo.doubleValue"));
		doubleValue.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize booleanValue
		booleanValue = new BooleanField(i18n.get("Foo.booleanValue"));
		booleanValue.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize date
		date = new DateTimeField(i18n.get("Foo.date"));
		date.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize localDateTime
		localDateTime = new DateTimeField(i18n.get("Foo.localDateTime"));
		localDateTime.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize localDate
		localDate = new DateField(i18n.get("Foo.localDate"));
		localDate.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize bar
		bar.withCaption(i18n.get("Foo.bar"));
		*/

		// initialize linkedBars
		linkedBars.withCaption(i18n.get("Foo.linkedBars"));

	}

	@Override
	protected void bind() {
		// bind stringValue
    	getBinder().forField(stringValue)
			.withValidator(new BeanValidator(Foo.class, "stringValue"))
			.bind(Foo::getStringValue, Foo::setStringValue);

    	/*
		// bind longValue
    	getBinder().forField(longValue)
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
		*/

	}

	@Override
	protected Component createContent() {
		VVerticalLayout layout = new VVerticalLayout();

		layout.withComponent(stringValue);
		layout.withComponent(linkedBars);
		/*
		responsiveLayout.addRow().addColumn().withComponent(longValue);
		responsiveLayout.addRow().addColumn().withComponent(doubleValue);
		responsiveLayout.addRow().addColumn().withComponent(booleanValue);
		responsiveLayout.addRow().addColumn().withComponent(date);
		responsiveLayout.addRow().addColumn().withComponent(localDateTime);
		responsiveLayout.addRow().addColumn().withComponent(localDate);
		responsiveLayout.addRow().addColumn().withComponent(bar);
		responsiveLayout.addRow().addColumn().withComponent(linkedBars);
		*/

		HorizontalLayout toolbar = getToolbar();
		layout.withComponent(toolbar);

		return layout;
	}
}
