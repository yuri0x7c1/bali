package com.github.yuri0x7c1.bali.data.qb.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Instantiates a new model.
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
@Data
public class QbModel {

	@ToString
	@Data
	@FieldDefaults(level=AccessLevel.PRIVATE)
	public static class QbGroup {
		QbCondition condition;

		List<QbGroup> groups = new ArrayList<>();

		List<QbRule> rules = new ArrayList<>();
	}

	@ToString
	@Data
	@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
	public static class QbRule {
		String fieldName;

		QbType fieldType;

		QbOperator operator;

		Object value;
	}

	QbGroup rootGroup;

	/**
	 * Group is empty.
	 *
	 * @param group the group
	 * @return true, if successful
	 */
	private boolean groupIsEmpty(QbGroup group) {
		if (CollectionUtils.isNotEmpty(group.getRules())) return false;

		if (CollectionUtils.isNotEmpty(group.getGroups())) {
			for (QbGroup g : group.getGroups()) {
				if (!groupIsEmpty(g)) return false;
			}
		}

		return true;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty () {
		return groupIsEmpty(rootGroup);
	}

	/**
	 * Checks if is not empty.
	 *
	 * @return true, if is not empty
	 */
	public boolean isNotEmpty() {
		return !groupIsEmpty(rootGroup);
	}
}
