package com.github.yuri0x7c1.bali.demo.ui.view;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.github.yuri0x7c1.bali.ui.view.EntityEditView;
import com.vaadin.spring.annotation.SpringView;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@Secured("ROLE_FOO_EDIT")
@SpringView(name = FooEditView.NAME)
public class FooEditView extends EntityEditView<Foo, Integer> {
	public static final String NAME = "foos/edit";
	public static final String CAPTION_CODE = "Foo.create";

	FooService fooService;

	public FooEditView(I18N i18n, FooForm entityForm, FooService fooService) {
		super(Foo.class, Integer.class, i18n, entityForm);
		this.fooService = fooService;

		setHeaderText(i18n.get(CAPTION_CODE));

		setEntityProvider(id -> fooService.findById(id).orElse(null));
		setSavedHandler(e -> {
			fooService.save(e);
			UiUtil.back();
		});
	}
}
