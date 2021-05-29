package com.github.yuri0x7c1.bali.demo.ui.layout;

import com.github.yuri0x7c1.bali.demo.ui.view.CalendarView;
import com.github.yuri0x7c1.bali.demo.ui.view.FooListView;
import com.github.yuri0x7c1.bali.demo.ui.view.HomeView;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.layout.CommonAppLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * The main view is a top-level placeholder for other views.
 */
@UIScope
@SpringComponent
public class ApplicationLayout extends CommonAppLayout {

	public ApplicationLayout(I18N i18n) {
		super(i18n);
	}

	@Override
	protected Tab[] createMenuItems() {
        return new Tab[]{
        	createTab(i18n.get(HomeView.TITLE_CODE), HomeView.class),
        	createTab("Calendar", CalendarView.class),
        	createTab("Foos", FooListView.class),
        };
	}

}
