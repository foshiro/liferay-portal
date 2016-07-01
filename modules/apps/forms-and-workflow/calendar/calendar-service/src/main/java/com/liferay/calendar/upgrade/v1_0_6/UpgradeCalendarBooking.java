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

package com.liferay.calendar.upgrade.v1_0_6;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adam Brandizzi
 */
public class UpgradeCalendarBooking extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		updateCalendarBooking();
	}

	protected void updateCalendarBooking() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("CalendarBooking", "recurringCalendarBookingId")) {
				runSQL(
					"alter table CalendarBooking add " +
						"recurringCalendarBookingId LONG null");
			}

			runSQL(
				"update CalendarBooking set recurringCalendarBookingId = " +
					"calendarBookingId");
		}
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeCalendarBooking.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false, false);
	}

}