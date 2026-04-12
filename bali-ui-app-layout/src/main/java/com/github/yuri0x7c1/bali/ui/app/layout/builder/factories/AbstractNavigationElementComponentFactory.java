package com.github.yuri0x7c1.bali.ui.app.layout.builder.factories;

import com.github.yuri0x7c1.bali.ui.app.layout.behaviour.AppLayoutComponent;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.NavigatorNavigationElement;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces.ComponentFactory;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces.NavigationElementComponent;
import com.github.yuri0x7c1.bali.ui.app.layout.component.button.NavigationBadgeButton;

public abstract class AbstractNavigationElementComponentFactory implements ComponentFactory<NavigationElementComponent, NavigatorNavigationElement> {

    public void setNavigationClickListener(NavigatorNavigationElement element) {
        NavigationBadgeButton button = (NavigationBadgeButton) element.getComponent();
        button.getButton().addClickListener(clickEvent -> {
            element.buttonClick(clickEvent);
            AppLayoutComponent.closeDrawerIfNotPersistent();
        });
    }
}
