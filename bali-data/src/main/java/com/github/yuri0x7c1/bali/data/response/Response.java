package com.github.yuri0x7c1.bali.data.response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class Response {
	protected String serverTime;

	protected Integer httpCode;

	protected String errorMessage;

	/**
	 * Instantiates a new response.
	 */
	public Response() {
		setHttpCode(200);
		setServerTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param ex the ex
	 */
	public Response(Exception ex) {
		setHttpCode(500);
		setErrorMessage(ex.getMessage());
		setServerTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
}
