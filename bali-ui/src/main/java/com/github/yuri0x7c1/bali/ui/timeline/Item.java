package com.github.yuri0x7c1.bali.ui.timeline;

public class Item {
	/**
	 * The contents of the item. This can be plain text or html code.
	 */
	private String content;

	/**
	 * The end date of the item. The end date is optional, and can be left null. If
	 * end date is provided, the item is displayed as a range. If not, the item is
	 * displayed as a box.
	 */
	private String end;

	/**
	 * This field is optional. When the group column is provided, all items with the
	 * same group are placed on one line. A vertical axis is displayed showing the
	 * groups. Grouping items can be useful for example when showing availability of
	 * multiple people, rooms, or other resources next to each other.
	 */
	private String group;

	/**
	 * An id for the item. Using an id is not required but highly recommended. An id
	 * is needed when dynamically adding, updating, and removing items in a DataSet.
	 */
	private String id;

	/**
	 * The start date of the item, for example new Date(2010,9,23).
	 */
	private String start;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "Item [content=" + content + ", end=" + end + ", group=" + group + ", id=" + id + ", start=" + start + "]";
	}
}