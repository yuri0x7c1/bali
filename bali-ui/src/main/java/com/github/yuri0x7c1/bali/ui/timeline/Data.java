package com.github.yuri0x7c1.bali.ui.timeline;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<Group> groups = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addGroup(Group group) {
		groups.add(group);
	}

	public void addItem(Item item) {
		items.add(item);
	}
}
