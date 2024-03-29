/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.ui.qb.component;

import org.apache.commons.collections4.CollectionUtils;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.qb.config.QbConfig;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel.QbGroup;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel.QbRule;
import com.vaadin.ui.Button.ClickListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryBuilder.
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class QueryBuilder extends MVerticalLayout {

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
		addComponent(rootGroupRow);
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
	public void addSearchClickListener(ClickListener listener) {
		rootGroupRow.getSearchButton().addClickListener(listener);
	}
}
