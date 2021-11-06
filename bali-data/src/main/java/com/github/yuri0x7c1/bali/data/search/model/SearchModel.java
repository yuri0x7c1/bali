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
}
