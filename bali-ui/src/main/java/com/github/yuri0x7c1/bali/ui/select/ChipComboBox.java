package com.github.yuri0x7c1.bali.ui.select;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.themes.ValoTheme;

public class ChipComboBox<T> extends CustomField<Set<T>> {

	public class Chip extends MHorizontalLayout {
		private T item;
		private final MLabel textLabel;
		private final MButton closeButton;

		public Chip(String text) {
			super();
			addStyleName(ValoTheme.LAYOUT_WELL);
			setWidthUndefined();

			textLabel = new MLabel(text);
			closeButton = new MButton(VaadinIcons.CLOSE, event -> {
				chipLayout.removeComponent(this);
				Set<T> newValue = new HashSet<T>(value);
				newValue.remove(item);
				doSetValue(newValue);
			})
			.withStyleName("close-button", ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_TINY, BaliStyle.BORDERLESS_BUTTON_LIGHT);
		}

		public Chip(String text, T item) {
			this(text);
			this.item = item;
			addComponents(textLabel, closeButton);
		}
	}

	private final ComboBox<T> comboBox;
	private final MCssLayout chipLayout;

	private Set<T> value = Collections.emptySet();

	public ChipComboBox() {
		chipLayout = new MCssLayout();
		comboBox = new ComboBox<T>();
		comboBox.addValueChangeListener(event -> {
			if (event.isUserOriginated()) {
				if (event.getValue() != null) {
					Set<T> newValue = new HashSet<T>(value);
					newValue.add(event.getValue());
					doSetValue(newValue);
					comboBox.setValue(null);
				}
			}
		});
	}

    public void setItems(Collection<T> items) {
    	comboBox.setItems(items);
    }

	@Override
	public Set<T> getValue() {
		return value;
	}

	@Override
	protected Component initContent() {
		return new MVerticalLayout(comboBox, chipLayout);
	}

	@Override
	protected void doSetValue(Set<T> value) {
		chipLayout.removeAllComponents();
		if (value == null) value = Collections.emptySet();
		this.value = value;
		for (T v : value) {
			chipLayout.add(new Chip(v.toString(), v));
		}
	}
}
