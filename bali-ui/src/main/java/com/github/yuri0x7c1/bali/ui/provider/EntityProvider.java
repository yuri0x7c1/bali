package com.github.yuri0x7c1.bali.ui.provider;

import java.io.Serializable;

@FunctionalInterface
public interface EntityProvider<T, P> extends Serializable {
	T getEntity(P params);
}