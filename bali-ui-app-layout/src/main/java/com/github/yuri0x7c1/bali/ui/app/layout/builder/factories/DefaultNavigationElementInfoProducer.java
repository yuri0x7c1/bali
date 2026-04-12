package com.github.yuri0x7c1.bali.ui.app.layout.builder.factories;

import com.github.yuri0x7c1.bali.ui.app.layout.annotations.AnnotationHelper;
import com.github.yuri0x7c1.bali.ui.app.layout.annotations.NavigatorViewName;

public class DefaultNavigationElementInfoProducer extends BasicViewInfoProducer {

    public DefaultNavigationElementInfoProducer() {
        super(info -> AnnotationHelper.getAnnotationFromView(info, NavigatorViewName.class).map(a -> a.value()));
    }
}
