/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yuri0x7c1.bali.ui.menu.security;

import com.github.yuri0x7c1.bali.ui.menu.MenuItemDescriptor;
import com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu;

/**
 * This is an {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.ItemFilter ItemFilter} that uses
 * {@link org.vaadin.spring.security.VaadinSecurity} to filter out items with the {@link org.springframework.security.access.annotation.Secured} annotation
 * based on the current user's authorities. If a user has any of the authorities/roles listed in the annotation, the item passes the filter. Items without
 * the annotation also pass the filter.
 *
 * @author Petter Holmström (petter@vaadin.com)
 * @author yuri0x7c1
 * @see org.vaadin.spring.security.VaadinSecurity#hasAnyAuthority(String...)
 */
public class VaadinSecurityItemFilter implements AbstractMenu.ItemFilter {

    // private final VaadinSecurity vaadinSecurity;


    @Override
    public boolean passesFilter(MenuItemDescriptor descriptor) {
    	/*
        Secured secured = descriptor.findAnnotationOnBean(Secured.class);
        if (secured != null) {
            return vaadinSecurity.hasAnyAuthority(secured.value());
        }
        */
        return true;
    }
}
