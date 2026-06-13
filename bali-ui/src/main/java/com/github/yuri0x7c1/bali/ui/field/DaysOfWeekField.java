package com.github.yuri0x7c1.bali.ui.field;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.themes.ValoTheme;

public class DaysOfWeekField extends CustomField<List<DayOfWeek>> {

	private MCssLayout layout = new MCssLayout();

	private Map<DayOfWeek, MButton> buttons = new HashMap<>();

	private List<DayOfWeek> value = new ArrayList<>();

	public DaysOfWeekField() {
		for (DayOfWeek d : DayOfWeek.values()) {
			MButton b = new MButton(d.name(), event -> {
				List<DayOfWeek> newValue = new ArrayList<>(value);
				if (value.contains(d)) {
					newValue.remove(d);
				}
				else {
					newValue.add(d);
				}
				doSetValue(newValue);
			}).withStyleName(ValoTheme.BUTTON_SMALL, BaliStyle.BUTTON_PRIMARY_FIX);
			buttons.put(d, b);
			layout.addComponent(b);
		}
	}

	public DaysOfWeekField(String caption) {
		this();
		setCaption(caption);
	}

	@Override
	public List<DayOfWeek> getValue() {
		return Collections.unmodifiableList(value);
	}

	@Override
	protected Component initContent() {
		return layout;
	}

	@Override
	protected void doSetValue(List<DayOfWeek> value) {
		if (value == null) {
			this.value = new ArrayList<>();
		}
		else {
			this.value = new ArrayList<>(value);
		}

		for (DayOfWeek d : DayOfWeek.values()) {
			if (this.value.contains(d)) {
				buttons.get(d).addStyleName(ValoTheme.BUTTON_PRIMARY);
			}
			else {
				if (buttons.get(d).getStyleName().contains(ValoTheme.BUTTON_PRIMARY)) {
					buttons.get(d).removeStyleName(ValoTheme.BUTTON_PRIMARY);
				}
			}
		}
	}
}
