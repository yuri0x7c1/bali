package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;

public abstract class CommonView extends VVerticalLayout {
	private VHorizontalLayout headerLayout;
	private H3 headerLabel;
	private VHorizontalLayout headerComponentLayout;

	public CommonView() {
		// setMargin(new MarginInfo(false, false, false, false));
		// setStyleName("common-view");

		headerLabel = new H3();

		headerComponentLayout = new VHorizontalLayout();

		headerLayout = new VHorizontalLayout(
			headerLabel,
			headerComponentLayout
		)
		.withFullWidth();
			// .withAlign(headerLabel, Alignment.MIDDLE_LEFT)
			// .withAlign(headerComponentLayout, Alignment.MIDDLE_RIGHT)

			//.withStyleName("page-header");

		add(headerLayout);
	}

	public void setHeaderText(String text) {
		headerLabel.setText(text);
	}

	public void addHeaderComponent(Component c) {
		headerComponentLayout.add(c);
	}
}