package com.github.yuri0x7c1.bali.data.search.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;


public abstract class SearchSpecProvider<T> {

	private Map<String, Function<Object, Specification<T>>> specFactories = new TreeMap<>();

	public void registerSpec(String name, Function<Object, Specification<T>> specFactory) {
		specFactories.put(name, specFactory);
	}

	Set<String> getAvailableSpecNames() {
		return specFactories.keySet();
	}

	public Specification<T> getSpec(String name, Object value) {
		if (specFactories.get(name) == null) return null;
		return specFactories.get(name).apply(value);
	}
}
