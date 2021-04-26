package com.github.yuri0x7c1.bali.demo.ui.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.grid.VGrid;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.view.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UIScope
@SpringComponent
@Route(value = "foo/list", layout = MainView.class)
@PageTitle("Foos")
public class FooListView extends Div {
	public static final String DEFAULT_ORDER_PROPERTY = "id";

	public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;

	final FooService fooService;

	VGrid<Foo> fooGrid;

	VButton createButton;

	VButton showButton;

	VButton editButton;

	DeleteButton deleteButton;

	@PostConstruct
	public void init() {
		add(new H3("Foos"));

		// create button00
		createButton = new VButton("Create", e -> {
			createButton.getUI().ifPresent(ui -> ui.navigate("foo/create"));
		});
		add(createButton);

		// edit button
		editButton = new VButton("Edit", e -> {
			Set<Foo> selected = fooGrid.getSelectedItems();
			if (CollectionUtils.isNotEmpty(selected)) {
				if (selected.size() == 1) {
					editButton.getUI().ifPresent(ui ->
						ui.navigate("foo/edit/" + selected.iterator().next().getId().toString())
					);
				}
			}
		});
		add(editButton);

		// entity grid
		fooGrid = new VGrid<>(Foo.class);

		// entity grid default columns
		fooGrid.withProperties("id", "stringValue", "longValue", "doubleValue", "booleanValue", "date", "instant",
				"localDateTime", "zonedDateTime", "localDate", "bar");

		// list entities
		list();

		add(fooGrid);
	}

	protected void list() {
		fooGrid.setItems(
			query -> {
				String property = DEFAULT_ORDER_PROPERTY;
				Direction direction = DEFAULT_ORDER_DIRECTION;
				if (CollectionUtils.isNotEmpty(query.getSortOrders())) {
					property = query.getSortOrders().get(0).getSorted();
					direction = SortDirection.ASCENDING.equals(query.getSortOrders().get(0).getDirection()) ? Direction.ASC : Direction.DESC;
				}

				Page<Foo> foos = fooService.findAll(PageRequest.of(
					query.getOffset() / query.getLimit(),
					query.getLimit(),
					direction,
					property
				));

				return foos.stream().map(foo -> fooService.init(foo));
			},
			query -> Long.valueOf(fooService.count()).intValue()

		);
	}

}