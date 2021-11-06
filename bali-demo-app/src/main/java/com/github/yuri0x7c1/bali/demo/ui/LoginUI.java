package com.github.yuri0x7c1.bali.demo.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import com.github.yuri0x7c1.bali.common.ui.security.SharedLoginUI;
import com.vaadin.spring.annotation.SpringUI;

@SpringUI(path="/login")
public class LoginUI extends SharedLoginUI {

	@Autowired
	public LoginUI(I18N i18n, VaadinSharedSecurity vaadinSecurity) {
		super(i18n, vaadinSecurity);
	}

}
