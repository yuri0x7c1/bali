package com.github.yuri0x7c1.bali.ui.app.layout.builder;

import java.util.function.Consumer;

import com.github.yuri0x7c1.bali.ui.app.layout.behaviour.AppLayoutComponent;
import com.github.yuri0x7c1.bali.ui.app.layout.navigator.ComponentNavigator;

public class NoNavigatorAppLayoutBuilder extends AbstractViewAppLayoutBuilder<NoNavigatorAppLayoutBuilder> {
    protected NoNavigatorAppLayoutBuilder(AppLayoutComponent component) {
        super(component);
    }

    public static NoNavigatorAppLayoutBuilder get(AppLayoutComponent layout) {
        NoNavigatorAppLayoutBuilder builder = new NoNavigatorAppLayoutBuilder(layout);
        builder.config.setNavigatorEnabled(false);
        return builder;
    }

    public NoNavigatorAppLayoutBuilder withNavigatorConsumer(Consumer<ComponentNavigator> consumer) {
        config.setComponentNavigatorConsumer(consumer);
        return this;
    }
}
