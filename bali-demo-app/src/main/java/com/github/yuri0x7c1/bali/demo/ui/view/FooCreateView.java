package com.github.yuri0x7c1.bali.demo.ui.view;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
// import EventQualifiedName;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.github.yuri0x7c1.bali.demo.service.FooService;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@Secured("ROLE_FOO_CREATE")
@SpringView(name = FooCreateView.NAME)
public class FooCreateView extends CommonView implements View {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "foo-create";
	public static final String CAPTION_CODE = "Foo.create";

	private I18N i18n;

	private UIEventBus eventBus;

	private FooService fooService;

	private FooForm fooForm;

	@PostConstruct
	public void init() {
		// header
		setHeaderText(i18n.get(CAPTION_CODE));

		// back button
		addHeaderComponent(
			new MButton(
				VaadinIcons.ARROW_LEFT,
				i18n.get("Back"),
				(ClickListener) event -> UiUtil.back()
			).withStyleName(ValoTheme.BUTTON_PRIMARY)
		);

		// form saved handler
		fooForm.setSavedHandler(foo -> {
			// eventBus.publish(fooForm, new EventName(foo));

			fooService.save(foo);
			UiUtil.back();
		});

		// form reset handler
		fooForm.setResetHandler(foo -> {
			UiUtil.back();
		});

		// add form
		addComponent(fooForm);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// set entity
		fooForm.setEntity(new Foo());
	}
}