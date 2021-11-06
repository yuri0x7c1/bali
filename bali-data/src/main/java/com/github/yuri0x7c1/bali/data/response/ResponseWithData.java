package com.github.yuri0x7c1.bali.data.response;

import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseWithData<T> extends Response {

	protected T data;
	protected Long dataCount;
	protected String[] orderBy;
	protected String order;

	public ResponseWithData(Exception ex) {
		super(ex);
	}

	@JsonIgnore
	public Direction getDefaultOrder() {
		return Direction.ASC;
	}

	@JsonIgnore
	public String[] getDefaultOrderBy() {
		return new String[] {"id"};
	}
}
