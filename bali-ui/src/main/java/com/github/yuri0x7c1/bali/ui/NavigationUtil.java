package com.github.yuri0x7c1.bali.ui;

import com.vaadin.flow.component.UI;

public class NavigationUtil {
	/*
	 * Navigate back
	 */
	public static void back() {
		UI.getCurrent().getPage().getHistory().back();
	}
}