package com.github.yuri0x7c1.bali.ui.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.search.model.SearchField;
import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.data.search.model.SearchModel;
import com.github.yuri0x7c1.bali.ui.card.Card;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 */
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommonSearchForm extends Card {

	private static final float FIELD_SELECT_WIDTH = 200f;

	final ApplicationContext ctx;

	final I18N i18n;

	Map<String, SearchFieldComponentDescriptor> fieldDescriptors = new TreeMap<>();

	List<SearchFieldComponent> fieldComponents = new LinkedList<>();

	MCssLayout fieldLayout = new MCssLayout();

	ComboBox<SearchFieldComponentDescriptor> fieldSelect;

	MButton addButton;

	MButton searchButton;

	@Setter
	Runnable searchHandler;

	public CommonSearchForm(ApplicationContext ctx, I18N i18n) {
		super();
		this.ctx = ctx;
		this.i18n = i18n;

		setHeaderText(i18n.get("Search"));
		setHeaderMargin(true);
		setWidthFull();

		searchButton = new MButton(i18n.get("Search"), e -> {
			if (searchHandler != null) {
				searchHandler.run();
			}
		})
		.withStyleName(ValoTheme.BUTTON_PRIMARY)
		.withIcon(VaadinIcons.SEARCH);

		fieldSelect = new ComboBox<>();
		fieldSelect.setWidth(FIELD_SELECT_WIDTH, Unit.PIXELS);
		fieldSelect.setItemCaptionGenerator(fc -> fc.getFieldCaption());

		addButton = new MButton(i18n.get("Search.addField"), event -> {
			SearchFieldComponentDescriptor d = fieldSelect.getValue();
			if (d != null) {
				createFieldComponent(d, SearchFieldOperator.EQUAL, null);
			}
		})
		.withIcon(VaadinIcons.PLUS)
		.withStyleName(ValoTheme.BUTTON_PRIMARY);

		addHeaderComponent(new MHorizontalLayout(fieldSelect, addButton));
		setContent(new MVerticalLayout(fieldLayout, searchButton));

	}

	public SearchModel getModel() {
		SearchModel searchModel = new SearchModel();
		for (SearchFieldComponent fieldComponent : fieldComponents) {
			searchModel.getFields().add(
					new SearchField(fieldComponent.getName(), fieldComponent.getOperator(), fieldComponent.getValue()));
		}
		return searchModel;
	}

	private void createFieldComponent(SearchFieldComponentDescriptor descriptor, SearchFieldOperator option, Object value) {
		Component component = null;
		try {
			if (SearchFieldComponentLifecycle.NON_MANAGED.equals(descriptor.getComponentLifecycle())) {
				component = descriptor.getComponentClass().getDeclaredConstructor().newInstance();
			}
			else if (SearchFieldComponentLifecycle.MANAGED.equals(descriptor.getComponentLifecycle())) {
				component = ctx.getBean(descriptor.getComponentClass());
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return;
		}

		if (!(component instanceof HasValue)) {
			throw new RuntimeException("Search field component must implement HasValue interface!");
		}

		SearchFieldComponent fieldComponent = new SearchFieldComponent(i18n, descriptor.getFieldName(),
				descriptor.getFieldCaption(), option, component);
		if (value != null) {
			fieldComponent.setValue(value);
		}

		fieldComponent.setCloseHandler(() -> {
			fieldComponents.remove(fieldComponent);
			fieldLayout.removeComponent(fieldComponent);
		});

		fieldComponents.add(
			fieldComponent
		);
		log.debug("!!!! fieldComponents: {}", fieldComponents);

		fieldLayout.add(fieldComponent);
	}

	public void createFieldComponent(String fieldName, SearchFieldOperator operator, Object value) {
		SearchFieldComponentDescriptor d = fieldDescriptors.get(fieldName);
		if (d == null) {
			throw new RuntimeException(String.format("Descriptor for search field %s not found!", fieldName));
		}
		createFieldComponent(d, operator, value);
	}

	public void createFieldComponent(String  fieldName, SearchFieldOperator option) {
		createFieldComponent(fieldName, option, null);
	}

	public void registerFieldComponent(SearchFieldComponentDescriptor fieldComponent) {
		fieldDescriptors.put(fieldComponent.getFieldName(), fieldComponent);
		fieldSelect.setItems(fieldDescriptors.values());
	}
}
