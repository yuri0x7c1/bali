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

package com.github.yuri0x7c1.bali.ui.view;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.yuri0x7c1.bali.data.message.CommonMessages;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <P>
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ParametrizedView<P> extends CommonView {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MAPPER.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		MAPPER.registerModule(new JavaTimeModule());
		MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		MAPPER.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
	}

	@Getter
	final Class<P> paramsType;

	@Getter
	@Setter
	P params;

	public ParametrizedView(Class<P> paramsType) {
		super();
		this.paramsType = paramsType;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		showFlashMessages();
		try {
			if (StringUtils.isNotBlank(event.getParameters())) {
				this.params = MAPPER.readValue(URLDecoder.decode(event.getParameters(), StandardCharsets.UTF_8.name()),
						paramsType);
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		onEnter();
	}

	public static void navigateTo(String viewName, Object params) {
		try {
			UI.getCurrent().getNavigator().navigateTo(viewName + "/" +
					URLEncoder.encode(MAPPER.writeValueAsString(params), StandardCharsets.UTF_8.name()));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}
}
