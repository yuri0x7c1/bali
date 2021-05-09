package com.github.yuri0x7c1.bali.demo.ui.view;

import javax.annotation.PostConstruct;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.demo.ui.view.main.MainView;
import com.github.yuri0x7c1.bali.ui.util.NavigationUtil;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@SpringComponent
@Route(value = "foo/create", layout = MainView.class)
@PageTitle("Create Foo")
public class FooCreateView extends Div {
	final FooService fooService;

	final FooForm fooForm;

	@PostConstruct
	private void init() {
		// form saved handler
		fooForm.setSavedHandler(foo -> {
			fooService.save(foo);
			NavigationUtil.back();
		});

		// form reset handler
		fooForm.setResetHandler(foo -> {
			NavigationUtil.back();
		});

		fooForm.setEntity(new Foo());

		// add form
		add(fooForm);
	}
}