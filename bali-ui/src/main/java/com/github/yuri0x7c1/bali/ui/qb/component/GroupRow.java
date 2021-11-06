package com.github.yuri0x7c1.bali.ui.qb.component;

import java.util.LinkedList;
import java.util.List;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.qb.model.QbCondition;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * The Class GroupRow.
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class GroupRow extends MVerticalLayout {

	QueryBuilder qb;

	GroupRow parentGroup;

	@Getter
	Integer level;

	@Getter
	List<GroupRow> groups = new LinkedList<>();

	@Getter
	List<RuleRow> rules = new LinkedList<>();

	RadioButtonGroup<QbCondition> conditions;
	MButton addRuleButton;
	MButton addGroupButton;
	MButton deleteButton;

	@Getter
	MButton searchButton;

	/**
	 * Instantiates a new group row.
	 *
	 * @param qb the qb
	 * @param parentGroup the parent group
	 */
	public GroupRow(QueryBuilder qb, GroupRow parentGroup) {
		this.qb = qb;
		this.parentGroup = parentGroup;
		if (parentGroup == null) {
			level = 0;
		}
		else {
			level = parentGroup.getLevel() + 1;
		}

		addStyleName(ValoTheme.LAYOUT_WELL);
		conditions = new RadioButtonGroup<>();
		conditions.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		conditions.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		conditions.setItems(QbCondition.AND, QbCondition.OR);
		conditions.setValue(QbCondition.AND);

		addRuleButton = new MButton(VaadinIcons.PLUS, "Add Rule", event -> addRule())
			.withStyleName(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

		addGroupButton = new MButton(VaadinIcons.PLUS, "Add Group", event -> addGroup())
			.withStyleName(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

		deleteButton = new MButton(VaadinIcons.CLOSE, "Delete", event ->  {
			if (parentGroup != null) {
				parentGroup.deleteGroup(this);
			}
		})
		.withStyleName(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(conditions);
		buttonLayout.addComponent(addRuleButton);
		buttonLayout.addComponent(addGroupButton);
		if (level == 0) {
			searchButton =  new MButton("Search")
				.withIcon(VaadinIcons.SEARCH)
				.withStyleName(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);
			buttonLayout.addComponent(searchButton);
		}
		else if (level > 0) {
			buttonLayout.addComponent(deleteButton);
		}

		addComponent(buttonLayout);
	}

	/**
	 * Adds the rule.
	 *
	 * @return the rule row
	 */
	public RuleRow addRule() {
		RuleRow rule = new RuleRow(qb, this);
		rules.add(rule);
		addComponent(rule);
		return rule;
	}

	/**
	 * Delete rule.
	 *
	 * @param rule the rule
	 */
	public void deleteRule(RuleRow rule) {
		rules.remove(rule);
		removeComponent(rule);
	}

	/**
	 * Adds the group.
	 *
	 * @return the group row
	 */
	public GroupRow addGroup() {
		GroupRow groupRow = new GroupRow(qb, this);
		groups.add(groupRow);
		addComponent(groupRow);
		return groupRow;
	}

	/**
	 * Delete group.
	 *
	 * @param group the group
	 */
	public void deleteGroup(GroupRow group) {
		groups.remove(group);
		removeComponent(group);
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public QbCondition getCondition() {
		return conditions.getValue();
	}
}
