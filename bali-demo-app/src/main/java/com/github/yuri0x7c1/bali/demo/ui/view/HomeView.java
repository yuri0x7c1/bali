package com.github.yuri0x7c1.bali.demo.ui.view;

import com.github.yuri0x7c1.bali.demo.ui.view.main.MainView;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "", layout = MainView.class)
@PageTitle("Home")
public class HomeView extends CommonView {
	public HomeView() {
		setHeaderText("Home");
	}
}