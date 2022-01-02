/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.demo.ui.view;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.ui.detail.EntityDetail;
import com.github.yuri0x7c1.bali.ui.view.EntityShowView;
import com.vaadin.spring.annotation.SpringView;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@Secured("ROLE_FOO_VIEW")
@SpringView(name = FooShowView.NAME)
public class FooShowView extends EntityShowView<Foo, Integer> {

	public static final String NAME = "foo/show";
	public static final String CAPTION_CODE = "Foo.show";

	FooListView fooListView;

	public FooShowView(I18N i18n, FooService fooService, FooListView fooListView) {
		super(Foo.class, Integer.class, i18n,new EntityDetail<Foo>(Foo.class),
				p -> fooService.findById(p).orElse(null));
		this.fooListView = fooListView;

		getEntityDetail().addProperties(fooListView.getDataGrid().getProperties());
	}

}
