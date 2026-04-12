package com.github.yuri0x7c1.bali.ui.app.layout.builder.entities;

/**
 * Simple Data class to hold an instance of the type {@link T}
 *
 * @param <T>
 */
public class Holder<T> {
    public T value;

    public Holder(T value) {
        this.value = value;
    }
}
