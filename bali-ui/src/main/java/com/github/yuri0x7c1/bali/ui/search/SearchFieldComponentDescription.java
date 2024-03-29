/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.ui.search;

import static com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentLifecycle.NON_MANAGED;

import java.util.function.Supplier;

import com.vaadin.ui.Component;

import lombok.Getter;

public class SearchFieldComponentDescription {
	@Getter
	private final Class<? extends Component> componentClass;

	@Getter
	private final SearchFieldComponentLifecycle componentLifecycle;

	@Getter
	private final Supplier<? extends Component> componentSupplier;

	public SearchFieldComponentDescription(Class<? extends Component> componentClass) {
		super();
		this.componentClass = componentClass;
		this.componentLifecycle = NON_MANAGED;
		this.componentSupplier = null;
	}

	public SearchFieldComponentDescription(Class<? extends Component> componentClass,
			SearchFieldComponentLifecycle componentLifecycle) {
		super();
		this.componentClass = componentClass;
		this.componentLifecycle = componentLifecycle;
		this.componentSupplier = null;
	}

	public SearchFieldComponentDescription(Supplier<? extends Component> componentSupplier) {
		super();
		this.componentClass = null;
		this.componentLifecycle = null;
		this.componentSupplier = componentSupplier;
	}




}