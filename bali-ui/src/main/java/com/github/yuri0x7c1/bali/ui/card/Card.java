package com.github.yuri0x7c1.bali.ui.card;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author yuri0x7c1
 *
 */
public class Card extends MCssLayout {
	MHorizontalLayout headerLayout = new MHorizontalLayout();
	MLabel headerLabel = new MLabel();
	public Card() {
		addStyleName(ValoTheme.LAYOUT_CARD);
        headerLayout.addStyleName("v-panel-caption");
        headerLayout.setWidth("100%");
        headerLayout.addComponent(headerLabel);
        headerLayout.setExpandRatio(headerLabel, 1);
        addComponent(headerLayout);
	}

	public void setHeaderText(String text) {
		headerLabel.setValue(text);
	}

	public void setHeaderMargin(boolean enabled) {
		headerLayout.setMargin(enabled);
	}

	public void addHeaderComponent(Component component) {
		 headerLayout.addComponent(component);
	}

	public void setContent(Component component) {
		addComponent(component);
	}
}