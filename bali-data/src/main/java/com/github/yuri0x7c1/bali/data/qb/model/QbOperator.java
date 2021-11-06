package com.github.yuri0x7c1.bali.data.qb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum QbOperator {
	@JsonProperty("equal") EQUAL,
	@JsonProperty("not_equal") NOT_EQUAL,

	@JsonProperty("in") IN,
	@JsonProperty("not_in") NOT_IN,

	@JsonProperty("less") LESS,
	@JsonProperty("less_or_equal") LESS_OR_EQUAL,

	@JsonProperty("greater") GREATER,
	@JsonProperty("greater_or_equal") GREATER_OR_EQUAL,

	@JsonProperty("between") BETWEEN,
	@JsonProperty("not_between") NOT_BETWEEN,

	@JsonProperty("begins_with") BEGINS_WITH,
	@JsonProperty("not_begins_with") NOT_BEGINS_WITH,

	@JsonProperty("contains") CONTAINS,
	@JsonProperty("not_contains") NOT_CONTAINS,

	@JsonProperty("ends_with") ENDS_WITH,
	@JsonProperty("not_ends_with") NOT_ENDS_WITH,

	@JsonProperty("is_empty") IS_EMPTY,
	@JsonProperty("is_not_empty") IS_NOT_EMPTY,

	@JsonProperty("is_null") IS_NULL,
	@JsonProperty("is_not_null") IS_NOT_NULL;
}