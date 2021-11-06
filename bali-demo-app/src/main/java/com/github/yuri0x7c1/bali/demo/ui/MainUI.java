package com.github.yuri0x7c1.bali.demo.ui;

import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.sidebar.components.ValoSideBar;

import com.github.yuri0x7c1.bali.ui.CommonSecuredUI;
import com.github.yuri0x7c1.bali.ui.menu.TopBar;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Theme("mytheme")
@SpringUI
public class MainUI extends CommonSecuredUI {

	public MainUI(SpringViewProvider viewProvider, SpringNavigator navigator, VaadinSecurity vaadinSecurity, TopBar topBar, ValoSideBar sideBar) {
		super(viewProvider, navigator, vaadinSecurity, topBar, sideBar);
	}
}
