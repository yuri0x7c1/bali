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

package com.github.yuri0x7c1.bali.ui.i18n.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.vaadin.spring.i18n.CompositeMessageSource;
import org.vaadin.spring.i18n.MessageProvider;
import org.vaadin.spring.i18n.MessageProviderCacheCleanupExecutor;
import org.vaadin.spring.i18n.ResourceBundleMessageProvider;

import com.github.yuri0x7c1.bali.ui.i18n.I18N;

/**
 *
 * @author yuri0x7c1
 *
 */
@Configuration
public class I18nConfiguration {

    @Bean
    I18N i18n(ApplicationContext context) {
        return new I18N(context);
    }

    @Bean
    CompositeMessageSource messageSource(ApplicationContext context) {
        return new CompositeMessageSource(context);
    }

	@Bean
	MessageProvider messageProvider() {
		return new ResourceBundleMessageProvider("messages");
	}

	@Bean
	MessageProvider baliMessageProvider() {
		return new ResourceBundleMessageProvider("bali_messages");
	}

    @Bean
    MessageProviderCacheCleanupExecutor messageProviderCacheCleanupExecutor(Environment environment,
        CompositeMessageSource messageSource) {
        return new MessageProviderCacheCleanupExecutor(environment, messageSource);
    }
}