package com.github.yuri0x7c1.bali.ui.view;

import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.HasDynamicTitle;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/**
 * The Class CommonView.
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
public abstract class CommonView extends VVerticalLayout implements HasDynamicTitle {
	VHorizontalLayout headerLayout;
	@NonFinal String headerText;
	H3 headerLabel;
	VHorizontalLayout headerComponentLayout;

	public CommonView() {
		headerText = this.getClass().getSimpleName();
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
		headerText = text;
		headerLabel.setText(text);
	}

	public void addHeaderComponent(Component c) {
		headerComponentLayout.add(c);
	}

	@Override
	public String getPageTitle() {
		return headerText;
	}
}