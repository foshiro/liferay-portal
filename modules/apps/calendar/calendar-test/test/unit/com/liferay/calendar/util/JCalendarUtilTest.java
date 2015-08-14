/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.calendar.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.util.CalendarFactoryImpl;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class JCalendarUtilTest {

	@BeforeClass
	public static void setUpClass() {
		CalendarFactoryUtil calendarFactoryUtil = new CalendarFactoryUtil();

		calendarFactoryUtil.setCalendarFactory(new CalendarFactoryImpl());
	}

	@Test
	public void testAtTimeZone() {
		Calendar losAngelesJCalendar = CalendarFactoryUtil.getCalendar(
			RandomTestUtil.randomInt(2000, 2100),
			RandomTestUtil.randomInt(0, 12), RandomTestUtil.randomInt(1, 29),
			RandomTestUtil.randomInt(0, 24), RandomTestUtil.randomInt(0, 60),
			RandomTestUtil.randomInt(0, 60), RandomTestUtil.randomInt(0, 60),
			_losAngelesTimeZone);

		Calendar madridJCalendar = JCalendarUtil.atTimeZone(
			losAngelesJCalendar, _madridTimeZone);

		Assert.assertEquals(
			losAngelesJCalendar.getTimeInMillis(),
			madridJCalendar.getTimeInMillis());
		Assert.assertEquals(_madridTimeZone, madridJCalendar.getTimeZone());
	}

	@Test
	public void testGetDSTShiftAtLosAngelesDuringDST() {
		Calendar jCalendar1 = JCalendarUtil.getJCalendar(
			2012, Calendar.MAY, 1, 12, 0, 0, 0, TimeZoneUtil.GMT);
		Calendar jCalendar2 = JCalendarUtil.getJCalendar(
			2013, Calendar.JULY, 2, 12, 0, 0, 0, TimeZoneUtil.GMT);

		int shift = JCalendarUtil.getDSTShift(
			jCalendar1, jCalendar2, _losAngelesTimeZone);

		Assert.assertEquals(0, shift);
	}

	@Test
	public void testGetDSTShiftAtLosAngelesDuringNoDST() {
		Calendar jCalendar1 = JCalendarUtil.getJCalendar(
			2013, Calendar.DECEMBER, 1, 12, 0, 0, 0, TimeZoneUtil.GMT);
		Calendar jCalendar2 = JCalendarUtil.getJCalendar(
			2013, Calendar.JANUARY, 2, 12, 0, 0, 0, TimeZoneUtil.GMT);

		int shift = JCalendarUtil.getDSTShift(
			jCalendar1, jCalendar2, _losAngelesTimeZone);

		Assert.assertEquals(0, shift);
	}

	@Test
	public void testGetDSTShiftAtLosAngelesFromDSTToNoDST() {
		Calendar jCalendar1 = JCalendarUtil.getJCalendar(
			2013, Calendar.JULY, 1, 12, 0, 0, 0, TimeZoneUtil.GMT);
		Calendar jCalendar2 = JCalendarUtil.getJCalendar(
			2013, Calendar.JANUARY, 1, 12, 0, 0, 0, TimeZoneUtil.GMT);

		int shift = JCalendarUtil.getDSTShift(
			jCalendar1, jCalendar2, _losAngelesTimeZone);

		Assert.assertEquals(JCalendarUtil.HOUR, shift);
	}

	@Test
	public void testGetDSTShiftAtLosAngelesFromNoDSTToDST() {
		Calendar jCalendar1 = JCalendarUtil.getJCalendar(
			2013, Calendar.JANUARY, 1, 12, 0, 0, 0, TimeZoneUtil.GMT);
		Calendar jCalendar2 = JCalendarUtil.getJCalendar(
			2013, Calendar.JULY, 1, 12, 0, 0, 0, TimeZoneUtil.GMT);

		int shift = JCalendarUtil.getDSTShift(
			jCalendar1, jCalendar2, _losAngelesTimeZone);

		Assert.assertEquals(-1 * JCalendarUtil.HOUR, shift);
	}

	@Test
	public void testGetWeekdayPosition2June2014() {
		Calendar jCalendar = CalendarFactoryUtil.getCalendar(
			2014, Calendar.JUNE, 2);

		Assert.assertEquals(1, JCalendarUtil.getWeekdayPosition(jCalendar));
		Assert.assertEquals(
			Calendar.MONDAY, jCalendar.get(Calendar.DAY_OF_WEEK));
	}

	@Test
	public void testGetWeekdayPosition4August2014() {
		Calendar jCalendar = CalendarFactoryUtil.getCalendar(
			2014, Calendar.AUGUST, 4);

		Assert.assertEquals(1, JCalendarUtil.getWeekdayPosition(jCalendar));
		Assert.assertEquals(
			Calendar.MONDAY, jCalendar.get(Calendar.DAY_OF_WEEK));
	}

	@Test
	public void testGetWeekdayPosition7August2014() {
		Calendar jCalendar = CalendarFactoryUtil.getCalendar(
			2014, Calendar.AUGUST, 7);

		Assert.assertEquals(1, JCalendarUtil.getWeekdayPosition(jCalendar));
		Assert.assertEquals(
			Calendar.THURSDAY, jCalendar.get(Calendar.DAY_OF_WEEK));
	}

	@Test
	public void testGetWeekdayPosition8August2014() {
		Calendar jCalendar = CalendarFactoryUtil.getCalendar(
			2014, Calendar.AUGUST, 8);

		Assert.assertEquals(2, JCalendarUtil.getWeekdayPosition(jCalendar));
		Assert.assertEquals(
			Calendar.FRIDAY, jCalendar.get(Calendar.DAY_OF_WEEK));
	}

	@Test
	public void testMergeDateTime() {
		Calendar dateJCalendar = CalendarFactoryUtil.getCalendar(
			RandomTestUtil.randomInt(2000, 2100),
			RandomTestUtil.randomInt(0, 12), RandomTestUtil.randomInt(1, 29),
			RandomTestUtil.randomInt(0, 24), RandomTestUtil.randomInt(0, 60),
			RandomTestUtil.randomInt(0, 60), RandomTestUtil.randomInt(0, 60),
			_losAngelesTimeZone);

		Calendar timeJCalendar = CalendarFactoryUtil.getCalendar(
			RandomTestUtil.randomInt(2000, 2100),
			RandomTestUtil.randomInt(0, 12), RandomTestUtil.randomInt(1, 29),
			RandomTestUtil.randomInt(0, 24), RandomTestUtil.randomInt(0, 60),
			RandomTestUtil.randomInt(0, 60), RandomTestUtil.randomInt(0, 60),
			_madridTimeZone);

		Calendar jCalendar = JCalendarUtil.mergeDateTime(
			dateJCalendar, timeJCalendar, _calcuttaTimeZone);

		Assert.assertEquals(
			dateJCalendar.get(Calendar.YEAR), jCalendar.get(Calendar.YEAR));
		Assert.assertEquals(
			dateJCalendar.get(Calendar.MONTH), jCalendar.get(Calendar.MONTH));
		Assert.assertEquals(
			dateJCalendar.get(Calendar.DAY_OF_MONTH),
			jCalendar.get(Calendar.DAY_OF_MONTH));

		Assert.assertEquals(
			timeJCalendar.get(Calendar.HOUR), jCalendar.get(Calendar.HOUR));
		Assert.assertEquals(
			timeJCalendar.get(Calendar.MINUTE), jCalendar.get(Calendar.MINUTE));
		Assert.assertEquals(
			timeJCalendar.get(Calendar.SECOND), jCalendar.get(Calendar.SECOND));
		Assert.assertEquals(
			timeJCalendar.get(Calendar.MILLISECOND),
			jCalendar.get(Calendar.MILLISECOND));
		Assert.assertEquals(
			timeJCalendar.get(Calendar.AM_PM), jCalendar.get(Calendar.AM_PM));

		Assert.assertEquals(_calcuttaTimeZone, jCalendar.getTimeZone());
	}

	private static final TimeZone _calcuttaTimeZone = TimeZone.getTimeZone(
		"Asia/Calcutta");
	private static final TimeZone _losAngelesTimeZone = TimeZone.getTimeZone(
		"America/Los_Angeles");
	private static final TimeZone _madridTimeZone = TimeZone.getTimeZone(
		"Europe/Madrid");

}