package com.sample.attendance.api;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleTest {
	@Test
	public void sampleTest() {
        assertThat("sample test.", is(notNullValue()));
        log.debug("sampleTest() called.");

	}
}
