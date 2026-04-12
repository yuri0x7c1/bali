package com.github.yuri0x7c1.bali.ui.app.layout.builder.interfaces;

/**
 * An interface that should be implemented by {@link com.github.yuri0x7c1.bali.ui.app.layout.builder.elements.AbstractNavigationElement}
 * that is able to hold a {@link Factory<String,String>} to manipulate the captions for the Elements later on
 */
public interface HasCaptionInterceptor {
    void setCaptionInterceptor(Factory<String, String> captionInterceptor);
}
