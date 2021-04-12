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
package com.github.yuri0x7c1.bali.ui.menu.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.yuri0x7c1.bali.ui.menu.MenuItemDescriptor;
import com.github.yuri0x7c1.bali.ui.menu.MenuSectionDescriptor;
import com.github.yuri0x7c1.bali.ui.menu.MenuUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;

/**
 * Base class for visual side bar components. The side bar has access to an instance of {@link com.github.yuri0x7c1.bali.ui.menu.MenuUtils}
 * that will provide information about the sections and items to show.
 *
 * @author Petter Holmström (petter@vaadin.com)
 * @author yuri0x7c1
 */
public abstract class AbstractMenu<CR extends HasComponents> extends Component {

    private final MenuUtils menuUtils;
    private SectionComponentFactory<CR> sectionComponentFactory;
    private ItemComponentFactory itemComponentFactory;
    private ItemFilter itemFilter;

    /**
     * Protected constructor. The instance of {@link com.github.yuri0x7c1.bali.ui.menu.MenuUtils} should come from the Spring application context.
     */
    protected AbstractMenu(MenuUtils sideBarUtils) {
        this.menuUtils = sideBarUtils;
    }

    /**
     * Creates the component that actually contain the side bar sections and items. This method is called every time
     * the side bar is attached to a UI. Every time the side bar is detached, its composition root will be set back to {@code null}.
     */
    protected abstract CR createCompositionRoot();

    /**
     * Creates the default {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.SectionComponentFactory} to use.
     * This method must never return {@code null}.
     */
    protected abstract SectionComponentFactory<CR> createDefaultSectionComponentFactory();

    /**
     * Returns the current {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.SectionComponentFactory}. If no
     * factory has been set, a default factory is created.
     *
     * @see #setSectionComponentFactory(com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.SectionComponentFactory)
     * @see #createDefaultSectionComponentFactory()
     */
    protected SectionComponentFactory<CR> getSectionComponentFactory() {
        if (sectionComponentFactory == null) {
            sectionComponentFactory = createDefaultSectionComponentFactory();
        }
        sectionComponentFactory.setItemComponentFactory(getItemComponentFactory());
        return sectionComponentFactory;
    }

    /**
     * Sets the {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.SectionComponentFactory} to use.
     */
    protected void setSectionComponentFactory(SectionComponentFactory<CR> sectionComponentFactory) {
        this.sectionComponentFactory = sectionComponentFactory;
    }

    /**
     * Creates the default {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.ItemComponentFactory} to use.
     * This method must never return {@code null}.
     */
    protected abstract ItemComponentFactory createDefaultItemComponentFactory();

    /**
     * Returns the current {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.ItemComponentFactory}. If no
     * factory has been set, a default factory is created.
     *
     * @see #setItemComponentFactory(com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.ItemComponentFactory)
     * @see #createDefaultItemComponentFactory()
     */
    protected ItemComponentFactory getItemComponentFactory() {
        if (itemComponentFactory == null) {
            itemComponentFactory = createDefaultItemComponentFactory();
        }
        return itemComponentFactory;
    }

    /**
     * Sets the {@link com.github.yuri0x7c1.bali.ui.menu.components.AbstractMenu.ItemComponentFactory} to use.
     */
    protected void setItemComponentFactory(ItemComponentFactory itemComponentFactory) {
        this.itemComponentFactory = itemComponentFactory;
    }

    private void createSection(CR compositionRoot, MenuSectionDescriptor section, Collection<MenuItemDescriptor> items) {
        if (items.isEmpty()) {
            return;
        }
        if (itemFilter == null) {
            getSectionComponentFactory().createSection(compositionRoot, section, items);
        } else {
            List<MenuItemDescriptor> passedItems = new ArrayList<MenuItemDescriptor>();
            for (MenuItemDescriptor candidate : items) {
                if (itemFilter.passesFilter(candidate)) {
                    passedItems.add(candidate);
                }
            }
            if (!passedItems.isEmpty()) {
                getSectionComponentFactory().createSection(compositionRoot, section, passedItems);
            }
        }
    }

    /**
     * Sets an optional filter that is consulted before an item is added to the side bar. If set,
     * only items that pass the filter are added.
     */
    public void setItemFilter(ItemFilter itemFilter) {
        if (isAttached()) {
            throw new IllegalStateException("An ItemFilter cannot be set when the SideBar is attached");
        }
        this.itemFilter = itemFilter;
    }

    /**
     * Returns the item filter to use or {@code null} if not set.
     */
    public ItemFilter getItemFilter() {
        return itemFilter;
    }

    /**
     * Interface defining a factory for creating components that correspond to sections in a side bar.
     */
    public interface SectionComponentFactory<CR extends HasComponents> {
        /**
         * Sets the {@code ItemComponentFactory} to use when creating the items of the section. This method
         * is always called before the first invocation of {@link #createSection(com.vaadin.ui.ComponentContainer, com.github.yuri0x7c1.bali.ui.menu.MenuSectionDescriptor, java.util.Collection)}.
         *
         * @param itemComponentFactory the item component factory, must not be {@code null}.
         */
        void setItemComponentFactory(ItemComponentFactory itemComponentFactory);

        /**
         * Creates and adds the specified section and items to the composition root.
         *
         * @param compositionRoot the component to which the section and items are to be added, must not be {@code null}.
         * @param descriptor      the descriptor of the side bar section, must not be {@code null}.
         * @param itemDescriptors the descriptors of the items to be added to the section, must not be {@code null} nor empty.
         */
        void createSection(CR compositionRoot, MenuSectionDescriptor descriptor, Collection<MenuItemDescriptor> itemDescriptors);
    }

    /**
     * Interface defining a factory for creating components that correspond to items in a side bar section. When
     * the item is clicked by the user, {@link com.github.yuri0x7c1.bali.ui.menu.MenuItemDescriptor#itemInvoked(com.vaadin.ui.UI)}
     * must be called.
     */
    public interface ItemComponentFactory {

        /**
         * Creates a component to be added to a side bar section by a {@link AccordionSideBar.SectionComponentFactory}.
         * Remember to call {@link com.github.yuri0x7c1.bali.ui.menu.MenuItemDescriptor#itemInvoked(com.vaadin.ui.UI)} when the item
         * is clicked by the user.
         *
         * @param descriptor the descriptor of the side bar item, must not be {@code null}.
         * @return a component, never {@code null}.
         */
        Component createItemComponent(MenuItemDescriptor descriptor);
    }

    /**
     * Interface defining a filter that can limit which items show up in the side bar.
     */
    public interface ItemFilter {

        /**
         * Checks if the specified side bar item passes the filter or not. Items that do not pass are excluded
         * from the side bar.
         *
         * @param descriptor the descriptor of the side bar item, must not be {@code null}.
         * @return true if the item passes the filter, false if it does not.
         */
        boolean passesFilter(MenuItemDescriptor descriptor);

    }
}
