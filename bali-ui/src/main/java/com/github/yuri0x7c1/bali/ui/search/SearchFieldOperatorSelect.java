package com.github.yuri0x7c1.bali.ui.search;

import org.vaadin.spring.i18n.I18N;

import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.vaadin.ui.ComboBox;

/**
 *
 * @author yuri0x7c1
 *
 */
public class SearchFieldOperatorSelect extends ComboBox<SearchFieldOperator> {
	public SearchFieldOperatorSelect(I18N i18n) {
		setItemCaptionGenerator(item -> i18n.get(SearchFieldOperator.class.getSimpleName() + "." + item.name()));
		setItems(SearchFieldOperator.values());
	}
}
