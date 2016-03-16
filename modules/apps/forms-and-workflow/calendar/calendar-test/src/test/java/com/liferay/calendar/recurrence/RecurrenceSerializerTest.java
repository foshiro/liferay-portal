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

package com.liferay.calendar.recurrence;

import com.liferay.portal.kernel.util.StringPool;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class RecurrenceSerializerTest {

	@Test
	public void testSerializeAndDeserializeYearlyRecurrence() {
		String recurrence = "RRULE:FREQ=YEARLY;INTERVAL=1;BYMONTH=2;BYDAY=-1SU";

		Recurrence recurrenceObj = RecurrenceSerializer.deserialize(
			recurrence, _utcTimeZone);

		Assert.assertEquals(
			recurrence, RecurrenceSerializer.serialize(recurrenceObj));
	}

	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);

}