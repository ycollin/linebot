package com.sample.attendance.api;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class DateInfo {
	private Integer day;
	private Integer dayOfTheWeek;
	private Integer reasonCd;
	private String reason;
	private String start;
	private String end;
	private String remarks;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
