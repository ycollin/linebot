package com.sample.attendance.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MonthInfo {

	private String yymm;
	private List<DateInfo> dayData = null;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
