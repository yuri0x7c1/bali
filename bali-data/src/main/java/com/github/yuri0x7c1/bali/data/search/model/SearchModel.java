package com.github.yuri0x7c1.bali.data.search.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchModel {
	List<SearchField> fields = new ArrayList<>();

	public void removeFieldsByName(String name) {
		List<SearchField> newFields = new ArrayList<>();
		for (SearchField f : fields) {
			if (!name.equals(f.getName())) {
				newFields.add(f);
			}
		}
		fields = newFields;
	}
}
