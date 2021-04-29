package com.github.yuri0x7c1.bali.qb.ui.component;

import org.apache.commons.collections4.CollectionUtils;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel.QbGroup;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel.QbRule;
import com.vaadin.flow.component.ComponentEventListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * The Class QueryBuilder.
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class QueryBuilder extends VVerticalLayout {

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	@Getter
	QbConfig config;

	/**
	 * Gets the root group row.
	 *
	 * @return the root group row
	 */
	@Getter
	GroupRow rootGroupRow;

	/**
	 * Instantiates a new query builder.
	 *
	 * @param config the config
	 */
	public QueryBuilder(QbConfig config) {
		this.config = config;

		setMargin(false);

		rootGroupRow = new GroupRow(this, null);
		add(rootGroupRow);
	}

	/**
	 * Gets the group.
	 *
	 * @param groupRow the group row
	 * @return the group
	 */
	public QbGroup getGroup(GroupRow groupRow) {
		QbGroup group = new QbGroup();
		group.setCondition(groupRow.getCondition());

		// get nested groups
		if (CollectionUtils.isNotEmpty(groupRow.getGroups())) {
			for (GroupRow row : groupRow.getGroups()) {
				group.getGroups().add(getGroup(row));
			}
		}

		// get nested rules
		if (CollectionUtils.isNotEmpty(groupRow.getRules())) {
			for (RuleRow row : groupRow.getRules()) {
				group.getRules().add(new QbRule(
					row.getSelectedField().getName(),
					row.getSelectedField().getType(),
					row.getSelectedOperator(),
					row.getValue()
				));
			}
		}

		return group;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public QbModel getModel() {
		QbModel model = new QbModel();
		model.setRootGroup(getGroup(rootGroupRow));

		return model;
	}

	/**
	 * Adds the search click listener.
	 *
	 * @param listener the listener
	 */
	public void addSearchClickListener(ComponentEventListener listener) {
		rootGroupRow.getSearchButton().addClickListener(listener);
	}
}
