package com.github.yuri0x7c1.bali.ui.util;

import com.vaadin.ui.JavaScript;

public class UiUtil {
	/*
	 * Navigate back
	 */
	public static void back() {
		JavaScript.getCurrent().execute("history.back()");
	}
}