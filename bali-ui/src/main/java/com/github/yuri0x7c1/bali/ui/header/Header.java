package com.github.yuri0x7c1.bali.ui.header;

import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class Header extends MHorizontalLayout {
	MLabel label;
	MHorizontalLayout componentContainer;

	public Header() {
		setWidthFull();
		setMargin(true);
		setSpacing(true);
		label = new MLabel();
		componentContainer = new MHorizontalLayout();

	    add(label);
	    add(componentContainer);

		setComponentAlignment(label, Alignment.MIDDLE_LEFT);
		setComponentAlignment(componentContainer, Alignment.MIDDLE_RIGHT);
	}

	public void setText(String text) {
		label.setValue(text);
	}

	public void setTextSize(HeaderTextSize size) {
		label.addStyleName(size.name().toLowerCase());
	}

	public void addHeaderComponent(Component c) {
		componentContainer.add(c);
	}
}
