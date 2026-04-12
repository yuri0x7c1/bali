package com.github.yuri0x7c1.bali.ui.app.layout.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces.ComponentFactory;

/**
 * This annotation is meant to be used in combination with
 * {@link com.github.yuri0x7c1.bali.ui.app.layout.builder.CDIAppLayoutBuilder#add(Class)}
 * which is also inherited by {@link com.github.yuri0x7c1.bali.ui.app.layout.builder.NavigatorAppLayoutBuilder} and
 * {@link com.github.yuri0x7c1.bali.ui.app.layout.builder.NoNavigatorAppLayoutBuilder} and also in combination with
 * {@link com.github.yuri0x7c1.bali.ui.app.layout.builder.AbstractAppLayoutBuilderBase#withNavigationElementProvider(ComponentFactory)}
 * which is also inherited by all Builders
 *
 * The Value of this annotation will be used for the {@link com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.NavigatorNavigationElement} as the caption of the button
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuCaption {
    String value();
}
