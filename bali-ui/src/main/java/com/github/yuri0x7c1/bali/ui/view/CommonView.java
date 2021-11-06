package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.viritin.layouts.MPanel;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.ui.header.Header;
import com.github.yuri0x7c1.bali.ui.header.HeaderTextSize;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
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
		setMargin(new MarginInfo(false, false, false, false));

		header = new Header();
		header.setTextSize(HeaderTextSize.H2);

		addComponent(
			new MVerticalLayout(new MPanel(header))
				.withMargin(new MarginInfo(false, true, false, true))
		);
	}

	public void setHeaderText(String text) {
		header.setText(text);
	}

	public void addHeaderComponent(Component c) {
		header.addHeaderComponent(c);
	}
}
