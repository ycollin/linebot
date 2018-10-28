package com.sample.attendance.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class RegisterResult {

	private String status;
	private String message;
	private String id;
	private List<MonthInfo> monthData = null;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	private HttpStatus statusCode = HttpStatus.NOT_FOUND;

	public boolean isOk() {
		return statusCode.is2xxSuccessful();
	}
}
