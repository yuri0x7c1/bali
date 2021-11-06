package com.github.yuri0x7c1.bali.demo.ui.view;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
// import EventQualifiedName;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.ui.util.NavigationUtil;
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
@Secured("ROLE_FOO_EDIT")
@SpringView(name = FooEditView.NAME)
public class FooEditView extends CommonView implements View {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "foo-edit";
	public static final String CAPTION_CODE = "Foo.edit";

	private I18N i18n;

	private UIEventBus eventBus;

	private ObjectMapper mapper;

	private FooService fooService;

	private FooForm fooForm;

	@PostConstruct
	public void init() {
		// header
		setHeaderText(i18n.get(CAPTION_CODE));

		// header
		setHeaderText(i18n.get("Foo.edit"));

		// back button
		addHeaderComponent(
			new MButton(
				VaadinIcons.ARROW_LEFT,
				i18n.get("Back"),
				(ClickListener) event -> NavigationUtil.back()
			).withStyleName(ValoTheme.BUTTON_PRIMARY)
		);

		// form saved handler
		fooForm.setSavedHandler(foo -> {
			// eventBus.publish(fooForm, new EventName(foo));

			fooService.save(foo);
			NavigationUtil.back();
		});

		// form reset handler
		fooForm.setResetHandler(foo -> {
			NavigationUtil.back();
		});

		// add form
		addComponent(fooForm);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// validate params
		if (event.getParameters() == null || event.getParameters().isEmpty()) {
			log.error("Parameters is empty");
			NavigationUtil.back();
		}

		try {
			// find entity
			Foo foo = fooService.findById(mapper.readValue(event.getParameters(), java.lang.Integer.class)).get();
			if (foo == null) {
				log.error("Entity not found");
				NavigationUtil.back();
			}

			// initialize entity
			foo = fooService.init(foo);

			// set form entity
			fooForm.setEntity(foo);
		}
		catch (JsonParseException | JsonMappingException e) {
			log.error("Error converting parameters to Foo primary key", e);
		}
		catch (IOException e) {
			log.error("Error converting parameters to Foo primary key", e);
		}
	}
}