package com.github.yuri0x7c1.bali.demo.ui.sidebar;

import org.springframework.stereotype.Component;
import org.vaadin.spring.sidebar.annotation.SideBarSection;
import org.vaadin.spring.sidebar.annotation.SideBarSections;

@Component
@SideBarSections({
        @SideBarSection(id = Sections.VIEWS, captionCode = "Views"),
        @SideBarSection(id = Sections.OPERATIONS, captionCode = "Operations"),
})
public class Sections {
    public static final String VIEWS = "views";
    public static final String OPERATIONS = "operations";
}