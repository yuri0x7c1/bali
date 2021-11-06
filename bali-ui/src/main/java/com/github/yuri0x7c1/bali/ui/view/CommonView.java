package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.ui.header.Header;
import com.github.yuri0x7c1.bali.ui.header.HeaderTextSize;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class CommonView extends MVerticalLayout implements View {
	Header header;

	public CommonView() {
		setMargin(false);

		header = new Header();
		header.setTextSize(HeaderTextSize.H2);
		header.setMargin(false);

		addComponent(header);
	}

	public void setHeaderText(String text) {
		header.setText(text);
	}

	public void addHeaderComponent(Component c) {
		header.addHeaderComponent(c);
	}
}
