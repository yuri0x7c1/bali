package com.github.yuri0x7c1.bali.data.response;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseWithPaging<T> extends ResponseWithData<T> {
	protected Integer pageNumber;

	protected Integer pageSize;

	protected Integer pageCount;

	public ResponseWithPaging(Exception ex) {
		super(ex);
	}

	public ResponseWithPaging(Page page) {
		data = (T) page.getContent();
		dataCount = page.getTotalElements();
		pageNumber = page.getNumber();
		pageCount = page.getTotalPages();
	}
}
