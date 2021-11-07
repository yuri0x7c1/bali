package com.github.yuri0x7c1.bali.demo.ui.view;

import javax.annotation.PostConstruct;

import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.bali.demo.ui.sidebar.Sections;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, captionCode = "Home", order = 0)
@VaadinFontIcon(VaadinIcons.HOME)
public class HomeView extends CommonView implements View {

	@PostConstruct
    public void init() {
		setHeaderText("Home");
    }
}
