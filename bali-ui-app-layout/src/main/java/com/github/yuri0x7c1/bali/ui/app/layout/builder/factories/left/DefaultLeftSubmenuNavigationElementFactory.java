package com.github.yuri0x7c1.bali.ui.app.layout.builder.factories.left;

import com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.SubmenuNavigationElement;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces.ComponentFactory;
import com.github.yuri0x7c1.bali.ui.app.layout.component.ExpandingMenuContainer;

public class DefaultLeftSubmenuNavigationElementFactory implements ComponentFactory<SubmenuNavigationElement.SubmenuComponent, SubmenuNavigationElement> {
    @Override
    public SubmenuNavigationElement.SubmenuComponent get(SubmenuNavigationElement element) {
        ExpandingMenuContainer container = new ExpandingMenuContainer(element.getTitle(), element.getIcon());
        element.getSubmenuElements().forEach(element1 -> container.addComponent(element1.getComponent()));
        return container;
    }
}