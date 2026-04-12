package com.github.yuri0x7c1.bali.ui.app.layout.component.layout;

import com.vaadin.ui.Component;

/**
 * The implementation of {@link AbstractFlexBoxLayout} which mimics some behaviour of the {@link com.vaadin.ui.HorizontalLayout} by also using "flexbox" via css
 */
public class HorizontalFlexBoxLayout extends AbstractFlexBoxLayout {
    public HorizontalFlexBoxLayout() {
        super("flex-row");
    }

    public HorizontalFlexBoxLayout(Component... components) {
        super("flex-row", components);
    }
}
