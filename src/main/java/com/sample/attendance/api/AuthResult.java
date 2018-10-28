package com.sample.attendance.api;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class AuthResult {
	private String id;
	private String token;
	private String dateTime;

	private HttpStatus statusCode = HttpStatus.NOT_FOUND;

	public boolean isOk() {
		return statusCode.is2xxSuccessful();
	}
}
