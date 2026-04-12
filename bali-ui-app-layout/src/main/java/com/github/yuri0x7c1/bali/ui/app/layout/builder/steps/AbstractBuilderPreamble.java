package com.github.yuri0x7c1.bali.ui.app.layout.builder.steps;

import com.github.yuri0x7c1.bali.ui.app.layout.builder.AbstractAppLayoutBuilderBase;

public class AbstractBuilderPreamble<T extends AbstractAppLayoutBuilderBase> {

    private T builder;

    public AbstractBuilderPreamble(T builder) {
        this.builder = builder;
    }

    protected T getBuilder() {
        return builder;
    }

}
