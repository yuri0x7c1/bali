package com.github.yuri0x7c1.bali.ui.app.layout.component;

import static com.github.yuri0x7c1.bali.ui.app.layout.builder.design.Styles.APP_LAYOUT_ROUND_IMAGE;

import com.vaadin.server.Resource;
import com.vaadin.ui.Image;

/**
 * A component containing an image which is tweaked by css to be round without overflow with a certain height.
 */
public class RoundImage extends Image {
    public RoundImage(Resource icon) {
        this(icon, "75px", "75px");
    }

    public RoundImage(Resource icon, String width, String height) {
        super(null, icon);
        setWidth(width);
        setHeight(height);
        addStyleNames(APP_LAYOUT_ROUND_IMAGE);
    }
}
