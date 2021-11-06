package com.github.yuri0x7c1.bali.ui.handler;

import java.io.Serializable;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@FunctionalInterface
public interface CreateHandler<T> extends Serializable {
    void onCreate();
}