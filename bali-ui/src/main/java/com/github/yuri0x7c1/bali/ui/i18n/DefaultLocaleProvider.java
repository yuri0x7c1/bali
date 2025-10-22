package com.github.yuri0x7c1.bali.ui.i18n;

import java.util.Locale;

import com.vaadin.ui.UI;

public class DefaultLocaleProvider implements LocaleProvider {

	@Override
	public Locale getLocale() {
        UI currentUI = UI.getCurrent();
        Locale locale = currentUI == null ? null : currentUI.getLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
	}
}
