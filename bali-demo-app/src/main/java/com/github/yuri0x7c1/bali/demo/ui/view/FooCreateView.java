package com.github.yuri0x7c1.bali.demo.ui.view;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.ui.view.EntityCreateView;
import com.vaadin.spring.annotation.SpringView;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@Secured("ROLE_FOO_CREATE")
@SpringView(name = FooCreateView.NAME)
public class FooCreateView extends EntityCreateView<Foo> {
	public static final String NAME = "foos/create";
	public static final String CAPTION_CODE = "Foo.create";

	public FooCreateView(I18N i18n, FooForm entityForm, FooService fooService) {
		super(Foo.class, i18n, entityForm);

		setHeaderText(i18n.get(CAPTION_CODE));

		setSaveHandler(e -> fooService.save(e));
	}
}
