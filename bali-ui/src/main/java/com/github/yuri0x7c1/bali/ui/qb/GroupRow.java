package com.github.yuri0x7c1.bali.ui.qb;

import java.util.LinkedList;
import java.util.List;

import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.github.yuri0x7c1.bali.data.qb.model.QbCondition;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * The Class GroupRow.
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class GroupRow extends VVerticalLayout {

	QueryBuilder qb;

	GroupRow parentGroup;

	@Getter
	Integer level;

	@Getter
	List<GroupRow> groups = new LinkedList<>();

	@Getter
	List<RuleRow> rules = new LinkedList<>();

	RadioButtonGroup<QbCondition> conditions;
	VButton addRuleButton;
	VButton addGroupButton;
	VButton deleteButton;

	@Getter
	VButton searchButton;

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

		conditions = new RadioButtonGroup<>();
		conditions.setItems(QbCondition.AND, QbCondition.OR);
		conditions.setValue(QbCondition.AND);

		addRuleButton = new VButton("Add Rule", VaadinIcon.PLUS.create(), event -> addRule());

		addGroupButton = new VButton("Add Group", VaadinIcon.PLUS.create(), event -> addGroup());

		deleteButton = new VButton("Delete", VaadinIcon.CLOSE.create(), event ->  {
			if (parentGroup != null) {
				parentGroup.deleteGroup(this);
			}
		})
		.withThemeVariants(ButtonVariant.LUMO_ERROR);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.add(conditions);
		buttonLayout.add(addRuleButton);
		buttonLayout.add(addGroupButton);
		if (level == 0) {
			searchButton =  new VButton("Search")
				.withIcon(VaadinIcon.SEARCH.create())
				.withThemeVariants(ButtonVariant.LUMO_PRIMARY);
			buttonLayout.add(searchButton);
		}
		else if (level > 0) {
			buttonLayout.add(deleteButton);
		}

		add(buttonLayout);
	}

	/**
	 * Adds the rule.
	 *
	 * @return the rule row
	 */
	public RuleRow addRule() {
		RuleRow rule = new RuleRow(qb, this);
		rules.add(rule);
		add(rule);
		return rule;
	}

	/**
	 * Delete rule.
	 *
	 * @param rule the rule
	 */
	public void deleteRule(RuleRow rule) {
		rules.remove(rule);
		remove(rule);
	}

	/**
	 * Adds the group.
	 *
	 * @return the group row
	 */
	public GroupRow addGroup() {
		GroupRow groupRow = new GroupRow(qb, this);
		groups.add(groupRow);
		add(groupRow);
		return groupRow;
	}

	/**
	 * Delete group.
	 *
	 * @param group the group
	 */
	public void deleteGroup(GroupRow group) {
		groups.remove(group);
		remove(group);
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
