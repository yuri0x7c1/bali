package com.github.yuri0x7c1.bali.ui;

import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.sidebar.components.ValoSideBar;

import com.github.yuri0x7c1.bali.ui.menu.TopBar;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonSecuredUI extends CommonUI {

	@Getter
	VaadinSecurity vaadinSecurity;

	public CommonSecuredUI(SpringViewProvider viewProvider, SpringNavigator navigator, VaadinSecurity vaadinSecurity, TopBar topBar, ValoSideBar sideBar) {
		super(viewProvider, navigator, topBar, sideBar);
		this.vaadinSecurity = vaadinSecurity;

		topBar.withLogoutButton(event -> vaadinSecurity.logout());
	}
}
