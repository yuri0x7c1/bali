package com.github.yuri0x7c1.bali.demo.ui.view;

import javax.annotation.PostConstruct;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.demo.ui.layout.ApplicationLayout;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.util.NavigationUtil;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@UIScope
@SpringComponent
@Route(value = FooEditView.ROUTE, layout = ApplicationLayout.class)
public class FooEditView extends CommonView implements HasUrlParameter<Integer> {
	public static final String ROUTE = "foo/edit";
	public static final String TITLE_CODE = "Foo.edit";

	I18N i18n;

	FooService fooService;

	FooForm fooForm;

	@PostConstruct
	private void init() {
		setHeaderText(i18n.get(TITLE_CODE));

		// form saved handler
		fooForm.setSavedHandler(foo -> {
			fooService.save(foo);
			NavigationUtil.back();
		});

		// form reset handler
		fooForm.setResetHandler(foo -> {
			NavigationUtil.back();
		});

		// add form
		add(fooForm);
	}

	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		// validate params
		if (parameter == null) {
			log.error("Parameter is empty!");
			NavigationUtil.back();
		}

		// find entity
		Foo foo = fooService.findById(parameter).orElse(null);
		if (foo == null) {
			log.error("Entity not found!");
			NavigationUtil.back();
		}

		// initialize entity
		foo = fooService.init(foo);

		// set form entity
		fooForm.setEntity(foo);
	}
}
