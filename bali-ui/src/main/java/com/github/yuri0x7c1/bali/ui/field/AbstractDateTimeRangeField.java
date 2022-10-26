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

package com.github.yuri0x7c1.bali.ui.field;

import java.util.List;

import org.vaadin.viritin.layouts.MCssLayout;

import com.github.yuri0x7c1.bali.context.ApplicationContextProvider;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.util.TimePeriod;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 *
 * @author yuri0x7c1
 *
 */
public abstract class AbstractDateTimeRangeField<T> extends CustomField<List<T>> {
	protected final I18N i18n;
	protected final LocalDateTimeField startDateTimeField;
	protected final LocalDateTimeField endDateTimeField;
	protected final MenuBar optionsMenu;

	public AbstractDateTimeRangeField() {
		this(ApplicationContextProvider.getContext().getBean(I18N.class));
	}

	public AbstractDateTimeRangeField(I18N i18n) {
		this.i18n = i18n;

		startDateTimeField = new LocalDateTimeField(i18n);
		startDateTimeField.setWidth(190, Unit.PIXELS);
		startDateTimeField.setPlaceholder(i18n.get("From"));

		endDateTimeField = new LocalDateTimeField(i18n);
		endDateTimeField.setWidth(190, Unit.PIXELS);
		endDateTimeField.setPlaceholder(i18n.get("To"));

		optionsMenu = new MenuBar();
		MenuItem optionsItem = optionsMenu.addItem("", VaadinIcons.CALENDAR_CLOCK, null);
		optionsItem.setDescription(i18n.get("Options"));
		for (TimePeriod timePeriod : TimePeriod.values()) {
			optionsItem.addItem(i18n.get(TimePeriod.class.getSimpleName() + "." + timePeriod.name()), selectedItem -> {
				doSetValue(getTimePeriodValues(timePeriod));
			});
		}

		addStyleName(BaliStyle.TEMPORAL_RANGE_FIELD);
		setWidthUndefined();
	}

	protected abstract List<T> getTimePeriodValues(TimePeriod timePeriod);

	@Override
	protected Component initContent() {
		return new MCssLayout(startDateTimeField, endDateTimeField, optionsMenu);
	}
}
