package com.github.yuri0x7c1.bali.ui.app.layout.builder.factories.top;

import com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.ClickableNavigationElement;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces.ComponentFactory;
import com.github.yuri0x7c1.bali.ui.app.layout.component.button.NavigationButton;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

public class DefaultTopClickableNavigationElementFactory implements ComponentFactory<Component, ClickableNavigationElement> {
    @Override
    public Component get(ClickableNavigationElement element) {
        NavigationButton button = new NavigationButton(element.getName(), element.getIcon());
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.setWidthUndefined();
        button.addClickListener(element.getListener());
        return button;
    }
}
