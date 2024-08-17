package com.github.yuri0x7c1.bali.ui.timeline;

public class Group {
	/**
	 * The contents of the group. This can be plain text, html code or an htm
	 */
	private String content;

	/**
	 * An id for the group. The group will display all items having a property group which matches the id of the group.
	 */
	private String id;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
