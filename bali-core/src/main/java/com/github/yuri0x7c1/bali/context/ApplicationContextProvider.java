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

package com.github.yuri0x7c1.bali.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.extern.slf4j.Slf4j;

/**
 * ApplicationContextProvider allows static access to the {@link org.springframework.context.ApplicationContext}.
 * This implementation exists to provide access from non-managed spring beans.
 * <p>A ApplicationContextProvider provides:
 * <ul>
 * <li>Access to the Spring {@link org.springframework.context.ApplicationContext}.
 * </ul>
 *
 * @author G.J.R. Timmer
 * @author Petter Holmström (petter@vaadin.com)
 * @author yuri0x7c1
 * @see org.springframework.context.ApplicationContext
 */
@Slf4j
public class ApplicationContextProvider implements InitializingBean, ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Return the spring {@link org.springframework.context.ApplicationContext}
     *
     * @return the spring {@link org.springframework.context.ApplicationContext}
     */
    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (ApplicationContextProvider.class) {
            if (context != null) {
                log.warn("The application context has already been set. Do you have multiple instances of VaadinApplicationContext in your application?");
            }
            context = applicationContext;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("{} initialized", getClass().getName());
    }
}
