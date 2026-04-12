package com.github.yuri0x7c1.bali.ui.app.layout.session;

import com.github.yuri0x7c1.bali.ui.app.layout.behaviour.Behaviour;
import com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.NavigatorNavigationElement;
import com.github.yuri0x7c1.bali.ui.app.layout.component.button.NavigationBadgeButton;
import com.vaadin.ui.UI;

import static com.github.yuri0x7c1.bali.ui.app.layout.builder.design.Styles.APP_LAYOUT_MENU_ELEMENT_ACTIVE;

import java.util.Optional;

public class AppLayoutSessionHelper {
    
    /**
     * FIXME I changed this member to final. Since this seems to be a constant an a constant must always be static final
     */
    public static final String UI_SESSION_KEY = "app-layout-menu-button-active";
    public static final String UI_SESSION_KEY_VARIANT = "app-layout-variant-active";

    public static void updateActiveElementSessionData(NavigatorNavigationElement element) {
        setActiveNavigationElement(element);
    }

    public static void removeStyleFromCurrentlyActiveNavigationElement() {
        getActiveNavigationElement().ifPresent(element1 -> UI.getCurrent().access(() -> element1.getComponent().removeStyleName(APP_LAYOUT_MENU_ELEMENT_ACTIVE)));
    }

    public static Optional<NavigatorNavigationElement> getActiveNavigationElement() {
        Object object = UI.getCurrent().getSession().getAttribute(UI_SESSION_KEY);
        if (object instanceof NavigatorNavigationElement) {
            return Optional.of((NavigatorNavigationElement) object);
        } else {
            return Optional.empty();
        }
    }

    public static void setActiveNavigationElement(NavigatorNavigationElement element) {
        removeStyleFromCurrentlyActiveNavigationElement();
        NavigationBadgeButton button = (NavigationBadgeButton) element.getComponent();
        UI ui = UI.getCurrent();
        ui.access(() -> button.addStyleName(APP_LAYOUT_MENU_ELEMENT_ACTIVE));
        ui.getSession().setAttribute(UI_SESSION_KEY, element);
    }

    public static Behaviour getActiveVariant() {
        return (Behaviour) UI.getCurrent().getSession().getAttribute(UI_SESSION_KEY_VARIANT);
    }

    public static void setActiveVariant(Behaviour variant) {
        UI.getCurrent().getSession().setAttribute(UI_SESSION_KEY_VARIANT, variant);
    }
}
