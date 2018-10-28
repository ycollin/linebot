package com.sample.attendance.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AttendanceClient {

	private static final String API_KEY = "xxxxx";
	private static final String API_BASE_URL = "http://xxxxxxxxxxxxx";

	public AuthResult auth(final String id, final String pass) {
		val map = new LinkedMultiValueMap<String, String>();
		map.add("apiKey", API_KEY);
		map.add("id", id);
		map.add("pass", pass);
		val request = createRequest(map);
		log.info("auth API request=" + request.toString());

		try {
			val response = new RestTemplate().postForEntity(API_BASE_URL + "auth", request, AuthResult.class);
			log.info("auth API response=" + response.toString());

			val body = response.getBody();
			body.setStatusCode(response.getStatusCode());
			return body;

		} catch (RestClientException e) {
			return new AuthResult();
		}
	}

	public BasicInfo getBaseInfo(final String id, final String token) {
		val map = new LinkedMultiValueMap<String, String>();
		map.add("apiKey", API_KEY);
		map.add("id", id);
		map.add("token", token);
		val request = createRequest(map);
		log.info("getBaseInfo API request=" + request.toString());

		try {
			val response = new RestTemplate().postForEntity(API_BASE_URL + "getBaseInfo", request, BasicInfo.class);
			log.info("getBaseInfo API response=" + response.toString());

			val body = response.getBody();
			body.setStatusCode(response.getStatusCode());
			return body;
		} catch (RestClientException e) {
			return new BasicInfo();
		}
	}

	public RegisterResult simpleAttendanceRegister(final String id, final String token) {
		val map = new LinkedMultiValueMap<String, String>();
		map.add("apiKey", API_KEY);
		map.add("id", id);
		map.add("token", token);
		val request = createRequest(map);
		log.info("simpleAttendanceRegister API request=" + request.toString());

		try {
			val response = new RestTemplate().postForEntity(API_BASE_URL + "simpleAttendanceRegister", request,
					RegisterResult.class);
			log.info("simpleAttendanceRegister API response=" + response.toString());

			val body = response.getBody();
			body.setStatusCode(response.getStatusCode());
			return body;
		} catch (RestClientException e) {
			return new RegisterResult();
		}
	}

	public RegisterResult simpleLeaveRegister(final String id, final String token) {

		val map = new LinkedMultiValueMap<String, String>();
		map.add("apiKey", API_KEY);
		map.add("id", id);
		map.add("token", token);
		val request = createRequest(map);

		log.info("simpleLeaveRegister API request=" + request.toString());

		try {
			val response = new RestTemplate().postForEntity(API_BASE_URL + "simpleLeaveRegister", request,
					RegisterResult.class);
			log.info("simpleLeaveRegister API response=" + response.toString());

			val body = response.getBody();
			body.setStatusCode(response.getStatusCode());
			return body;
		} catch (RestClientException e) {
			return new RegisterResult();
		}
	}

	private HttpEntity<MultiValueMap<String, String>> createRequest(LinkedMultiValueMap<String, String> values) {
		val headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		val request = new HttpEntity<MultiValueMap<String, String>>(values, headers);
		return request;
	}

}