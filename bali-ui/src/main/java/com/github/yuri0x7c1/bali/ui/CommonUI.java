package com.github.yuri0x7c1.bali.ui;

import org.vaadin.spring.sidebar.components.ValoSideBar;

import com.github.yuri0x7c1.bali.ui.menu.TopBar;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.ui.view.AccessDeniedView;
import com.github.yuri0x7c1.bali.ui.view.ErrorView;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@StyleSheet("vaadin://bali.css")
public class CommonUI extends UI {

	@Getter
	SpringViewProvider viewProvider;

	@Getter
	SpringNavigator navigator;

	@Getter
	TopBar topBar;

	@Getter
    ValoSideBar sideBar;

	public CommonUI(SpringViewProvider viewProvider, SpringNavigator navigator, TopBar topBar, ValoSideBar sideBar) {
		super();
		this.viewProvider = viewProvider;
		this.navigator = navigator;
		this.topBar = topBar;
		this.sideBar = sideBar;
	}

	@Override
	protected void init(VaadinRequest request) {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setWidth(100f, Unit.PERCENTAGE);
        verticalLayout.setHeight(100f, Unit.PERCENTAGE);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        final int SIDEBAR_COLLAPSE_WIDTH = 1024;

        if (getPage().getBrowserWindowWidth() < SIDEBAR_COLLAPSE_WIDTH) {
        	sideBar.setLargeIcons(true);
        }

        getPage().addBrowserWindowResizeListener(event -> {
        	if (event.getWidth() < SIDEBAR_COLLAPSE_WIDTH) {
        		sideBar.setLargeIcons(true);
        	}
        	else {
        		sideBar.setLargeIcons(false);
        	}
		});

        horizontalLayout.addComponent(sideBar);

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName(BaliStyle.VIEW_CONTAINER);
        viewContainer.setSizeFull();
        horizontalLayout.addComponent(viewContainer);
        horizontalLayout.setExpandRatio(viewContainer, 1f);

        verticalLayout.addComponents(topBar, horizontalLayout);
        verticalLayout.setExpandRatio(horizontalLayout, 1f);

		// Without an AccessDeniedView, the view provider would act like the restricted views did not exist at all.
        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        navigator.setErrorView(ErrorView.class);
		navigator.addProvider(viewProvider);
		navigator.init(this, viewContainer);

	    setContent(verticalLayout);
	}
}
