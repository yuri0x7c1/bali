package com.github.yuri0x7c1.bali.demo.ui.select;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.common.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooSelect extends ComboBox<Foo> {
	I18N i18n;

	UIEventBus eventBus;

	FooService fooService;

	@PostConstruct
	public void init() {
		addStyleName(BaliStyle.FORM_FIELD);

		setTextInputAllowed(false);
		setItemCaptionGenerator(item -> String.valueOf(item.getId()));

		setDataProvider(new CallbackDataProvider<Foo, String>(
			query -> {
				Pageable pageable = PageRequest.of(
					query.getOffset() / query.getLimit(),
					query.getLimit(),
					Direction.ASC,
					"id"
				);

				return fooService.findAll(pageable)
					.stream().map(foo -> fooService.init(foo));
			},
			query -> {
				return Long.valueOf(fooService.count()).intValue();
			}
		));
	}

	public FooSelect withCaption(String caption) {
		setCaption(caption);
		return this;
	}
}
