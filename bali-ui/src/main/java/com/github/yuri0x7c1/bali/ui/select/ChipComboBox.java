package com.github.yuri0x7c1.bali.ui.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
import lombok.Setter;

public class ChipComboBox<T> extends CustomField<List<T>> {

	public class Chip extends MHorizontalLayout {
		private T item;
		private final MLabel textLabel;
		private final MButton closeButton;

		@Getter
		private final boolean isClearAll;

		public Chip(String text, boolean isCleanAll) {
			super();
			this.isClearAll = isCleanAll;
			addStyleNames(BaliStyle.CHIP, ValoTheme.LAYOUT_WELL);
			setWidthUndefined();

			textLabel = new MLabel(text).withStyleName(BaliStyle.CHIP_LABEL, ValoTheme.LABEL_SMALL);
			closeButton = new MButton(isCleanAll ? FontAwesome.TRASH :  VaadinIcons.CLOSE, event -> {
				if (!isCleanAll) {
					chipLayout.removeComponent(this);
					List<T> newValue = new ArrayList<T>(value);
					newValue.remove(item);
					setValue(newValue);
				}
				else {
					setValue(Collections.emptyList());
				}
			})
			.withStyleName(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_TINY, BaliStyle.BORDERLESS_BUTTON_LIGHT, BaliStyle.CHIP_BUTTON);

			if (isCleanAll) {
				addStyleName(BaliStyle.CHIP_CLEAR_ALL);
			}

			if (!isCleanAll) {
				addComponent(textLabel);
			}
			addComponent(closeButton);
		}

		public Chip(boolean isCleanAll) {
			this("", true);
		}

		public Chip(String text, T item) {
			this(text, false);
			this.item = item;
		}
	}

	private final ComboBox<T> comboBox;
	private final MCssLayout chipLayout;

	private List<T> value = Collections.emptyList();

	@Getter
	@Setter
	private String clearAllButtonLabel = "Clear All";

	public ChipComboBox() {
		addStyleName(BaliStyle.CHIP_COMBO_BOX);

		chipLayout = new MCssLayout()
				.withStyleName(BaliStyle.CHIP_LAYOUT)
				.withVisible(false);

		comboBox = new ComboBox<T>();
		comboBox.addValueChangeListener(event -> {
			if (event.isUserOriginated()) {
				if (event.getValue() != null && !value.contains(event.getValue())) {
					List<T> newValue = new ArrayList<T>(value);
					newValue.add(event.getValue());
					setValue(newValue);
					comboBox.setValue(null);
				}
			}
		});
	}

    public void setItems(Collection<T> items) {
    	comboBox.setItems(items);
    }

    public void setDataProvider(DataProvider<T, String> dataProvider) {
    	comboBox.setDataProvider(dataProvider);
    }

    public DataProvider<T, ?> getDataProvider() {
    	return comboBox.getDataProvider();
    }

	public ItemCaptionGenerator<T> getItemCaptionGenerator() {
		return comboBox.getItemCaptionGenerator();
	}

	public void setItemCaptionGenerator(ItemCaptionGenerator<T> itemCaptionGenerator) {
		comboBox.setItemCaptionGenerator(itemCaptionGenerator);
	}

	@Override
	public List<T> getValue() {
		return value;
	}

	@Override
	protected Component initContent() {
		return new MCssLayout(comboBox, chipLayout);
	}

	@Override
	protected void doSetValue(List<T> value) {
		chipLayout.removeAllComponents();
		if (value == null) value = Collections.emptyList();

		if (value.isEmpty()) {
			chipLayout.setVisible(false);
		}
		else {
			chipLayout.setVisible(true);
		}

		this.value = value;
		for (T v : value) {
			chipLayout.add(new Chip(getItemCaptionGenerator() == null ? v.toString() : getItemCaptionGenerator().apply(v), v));
		}
		if (value.size() > 1) {
			chipLayout.add(new Chip(true));
		}
	}
}
