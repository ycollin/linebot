package com.sample.attendance.api;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

// exclude from gradle test
@Slf4j
public class AttendanceClientTest {

	// 本物のIDとPASSなので・・・
	private static final String ID = "";
	private static final String PASS = "";


	@Test
	public void testAuth() {
		val client = new AttendanceClient();
		val auth = client.auth(ID, PASS);
        assertThat(auth.getToken(), is(notNullValue()));
        log.debug("testAuth() called.");

	}
	@Test
	public void testAuthFailure() {
		val client = new AttendanceClient();
		val auth = client.auth("999", "!!dummyPassword!!");
        assertThat(auth.getStatusCode().is2xxSuccessful(), is(false));
        log.debug("testAuth() called.");

	}

	@Test
	public void testGetBaseInfo() {
		val client = new AttendanceClient();
		val auth = client.auth(ID, PASS);
		val result = client.getBaseInfo(ID, auth.getToken());
	    assertThat(result.getStatus(), is("OK"));
	}

	@Test
	public void testSimpleAttendanceRegister() {
		val client = new AttendanceClient();
		val auth = client.auth(ID, PASS);
		val result = client.simpleAttendanceRegister(ID, auth.getToken());
	    assertThat(result.getStatus(), is("OK"));
	}

	@Test
	public void testSimpleLeaveRegister() {
		val client = new AttendanceClient();
		val auth = client.auth(ID, PASS);
		val result = client.simpleLeaveRegister(ID, auth.getToken());
	    assertThat(result.getStatus(), is("OK"));
	}

}
