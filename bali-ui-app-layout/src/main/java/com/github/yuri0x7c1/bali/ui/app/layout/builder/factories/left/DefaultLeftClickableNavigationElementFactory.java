package com.github.yuri0x7c1.bali.ui.app.layout.builder.factories.left;

import com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.ClickableNavigationElement;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces.ComponentFactory;
import com.github.yuri0x7c1.bali.ui.app.layout.component.button.NavigationButton;
import com.vaadin.ui.Component;

public class DefaultLeftClickableNavigationElementFactory implements ComponentFactory<Component, ClickableNavigationElement> {
    @Override
    public Component get(ClickableNavigationElement element) {
        NavigationButton button = new NavigationButton(element.getName(), element.getIcon());
        button.addClickListener(element.getListener());
        return button;
    }
}
