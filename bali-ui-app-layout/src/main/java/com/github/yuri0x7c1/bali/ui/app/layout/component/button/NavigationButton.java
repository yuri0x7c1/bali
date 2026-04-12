package com.github.yuri0x7c1.bali.ui.app.layout.component.button;

import static com.github.yuri0x7c1.bali.ui.app.layout.builder.design.Styles.APP_LAYOUT_MENU_BUTTON_NO_BORDER_READIUS;

import com.github.yuri0x7c1.bali.ui.app.layout.builder.design.Styles;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The default {@link com.vaadin.ui.Component} to be used to display menu entries
 */
public class NavigationButton extends Button {
    public NavigationButton(String name, Resource icon) {
        super(name);
        setIcon(icon);
        addStyleName(ValoTheme.BUTTON_BORDERLESS);
        addStyleName(Styles.APP_LAYOUT_MENU_BUTTON);
        addStyleName(APP_LAYOUT_MENU_BUTTON_NO_BORDER_READIUS); // for material theme only
        setHeight(48, Unit.PIXELS);
        setWidth(100, Sizeable.Unit.PERCENTAGE);
    }

    public NavigationButton(String name, Resource icon, Button.ClickListener listener) {
        this(name, icon);
        addClickListener(listener);
    }
}
