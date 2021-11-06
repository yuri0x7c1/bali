package com.github.yuri0x7c1.bali.ui;

import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.ui.view.ErrorView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

import kaesdingeling.hybridmenu.HybridMenu;
import kaesdingeling.hybridmenu.data.MenuConfig;
import kaesdingeling.hybridmenu.design.DesignItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 */
@Slf4j
@Push
@PushStateNavigation
@RequiredArgsConstructor
@StyleSheet("vaadin://bali.css")
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class CommonHybridUI extends UI implements DetachListener {

    final I18N i18n;

    final SpringViewProvider springViewProvider;

    Navigator navigator;

    HybridMenu hybridMenu;

    @Override
    protected void init(VaadinRequest request) {
    	getPage().setTitle(i18n.get("Application.name"));

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName(BaliStyle.VIEW_CONTAINER);
        viewContainer.setSizeFull();

        navigator = new Navigator(this, viewContainer);
        navigator.addProvider(springViewProvider);
        navigator.setErrorView(ErrorView.class);
        navigator.navigateTo(navigator.getState());

    	hybridMenu = HybridMenu.get()
    			.withNaviContent(viewContainer)
    			.withConfig(MenuConfig.get().withDesignItem(DesignItem.getWhiteDesign()))
    			.withInitNavigator(false)
    			.build();

    	buildTopMenu();
        buildLeftMenu();
    	setContent(hybridMenu);
    }

	protected abstract void buildTopMenu();

	protected abstract void buildLeftMenu();


	@Override
	public Navigator getNavigator() {
		return this.navigator;
	}

	@Override
	public void detach(DetachEvent event) {
		getUI().close();
	}
}
