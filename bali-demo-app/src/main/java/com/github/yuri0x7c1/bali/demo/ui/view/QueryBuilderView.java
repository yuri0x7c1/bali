package com.github.yuri0x7c1.bali.demo.ui.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.github.yuri0x7c1.bali.ui.qb.component.QueryBuilder;
import com.github.yuri0x7c1.bali.ui.sidebar.Sections;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringView(name = "query-builder")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Query Builder", order = 1)
@VaadinFontIcon(VaadinIcons.FILE_TREE)
public class QueryBuilderView extends CommonView implements View {

	QueryBuilder qb;

	MButton dumpModelButton;

	@Autowired
	public QueryBuilderView() {
		setHeaderText("Query Builder");

		QbConfig config = new QbConfig.Builder().withFields(
			new QbField("name", QbType.STRING, "Name")
		).build();

		qb = new QueryBuilder(config);

		dumpModelButton = new MButton("Dump", event -> log.info("Model: {}", qb.getModel()));

		addHeaderComponent(dumpModelButton);
		addComponent(qb);
	}
}
