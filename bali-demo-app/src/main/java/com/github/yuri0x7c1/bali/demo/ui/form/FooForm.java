package com.github.yuri0x7c1.bali.demo.ui.form;

import java.time.ZoneId;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.fields.DoubleField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.ui.picker.BarMultiPicker;
import com.github.yuri0x7c1.bali.demo.ui.picker.BarPicker;
import com.github.yuri0x7c1.bali.ui.field.BooleanField;
import com.github.yuri0x7c1.bali.ui.field.LongField;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooForm extends AbstractForm<Foo> {

	private static final long serialVersionUID = 1L;

	private I18N i18n;

	private UIEventBus eventBus;

    MTextField stringValue;

    LongField longValue;

    DoubleField doubleValue;

    BooleanField booleanValue;

    DateTimeField date;

    DateTimeField localDateTime;

    DateField localDate;

    BarPicker bar;

    BarMultiPicker linkedBars;

	public FooForm(I18N i18n, UIEventBus eventBus, BarPicker bar, BarMultiPicker linkedBars) {
		super(Foo.class);
		this.i18n = i18n;
		this.eventBus = eventBus;
		this.bar = bar;
		this.linkedBars = linkedBars;

		// initialize stringValue
		stringValue = new MTextField(i18n.get("Foo.stringValue"));
		stringValue.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize longValue
		longValue = new LongField(i18n.get("Foo.longValue"));
		longValue.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize doubleValue
		doubleValue = new DoubleField(i18n.get("Foo.doubleValue"));
		doubleValue.addStyleNames(BaliStyle.FORM_FIELD);

		// initialize booleanValue
		booleanValue = new BooleanField(i18n);
		booleanValue.setCaption(i18n.get("Foo.booleanValue"));
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
		bar.setCaption(i18n.get("Foo.bar"));

		// initialize linkedBars
		linkedBars.setCaption(i18n.get("Foo.linkedBars"));

	}

	@Override
	protected void bind() {
		// bind stringValue
    	getBinder().forField(stringValue)
			.withValidator(new BeanValidator(Foo.class, "stringValue"))
			.bind(Foo::getStringValue, Foo::setStringValue);

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

	}

	@Override
	protected Component createContent() {
		ResponsiveLayout responsiveLayout = new ResponsiveLayout();
		responsiveLayout.setStyleName(BaliStyle.FORM);
		responsiveLayout.setDefaultRules(12, 6, 6, 4);

		responsiveLayout.addRow().addColumn().withComponent(stringValue);
		responsiveLayout.addRow().addColumn().withComponent(longValue);
		responsiveLayout.addRow().addColumn().withComponent(doubleValue);
		responsiveLayout.addRow().addColumn().withComponent(booleanValue);
		responsiveLayout.addRow().addColumn().withComponent(date);
		responsiveLayout.addRow().addColumn().withComponent(localDateTime);
		responsiveLayout.addRow().addColumn().withComponent(localDate);
		responsiveLayout.addRow().addColumn().withComponent(bar);
		responsiveLayout.addRow().addColumn().withComponent(linkedBars);

		HorizontalLayout toolbar = getToolbar();
		toolbar.setStyleName(BaliStyle.FORM_TOOLBAR);
		responsiveLayout.addRow().addColumn().withComponent(toolbar);

		return responsiveLayout;
	}
}
