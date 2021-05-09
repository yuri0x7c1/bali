package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;

/**
 * The Class CommonView.
 *
 * @author yuri0x7c1
 */
public abstract class CommonView extends VVerticalLayout {
	private VHorizontalLayout headerLayout;
	private H3 headerLabel;
	private VHorizontalLayout headerComponentLayout;

	public CommonView() {
		headerLabel = new H3();

		headerComponentLayout = new VHorizontalLayout();

		headerLayout = new VHorizontalLayout(
			headerLabel,
			headerComponentLayout
		)
		.withFullWidth()
		.withAlignItems(Alignment.END);

		add(headerLayout);
	}

	public void setHeaderText(String text) {
		headerLabel.setText(text);
	}

	public void addHeaderComponent(Component c) {
		headerComponentLayout.add(c);
	}
}