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

package com.liferay.calendar.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Adam Brandizzi
 */
@Meta.OCD(id = "com.liferay.calendar.configuration.CalendarConfiguration")
public interface CalendarConfiguration {

	@Meta.AD(deflt = "" + 0xD96666, required = false)
	public int defaultCalendarColor();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_invite_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingInviteBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_invite_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingInviteSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_moved_to_trash_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingMovedToTrashBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_moved_to_trash_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingMovedToTrashSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_reminder_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingReminderBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_reminder_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingReminderSubject();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_update_body.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingUpdateBody();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/email/booking_update_subject.tmpl}",
		required = false
	)
	public LocalizedValuesMap emailBookingUpdateSubject();

	@Meta.AD(deflt = "true", required = false)
	public boolean forceAutogenerateCode();

	@Meta.AD(deflt = "1", required = false)
	public int notificationCheckInterval();

	@Meta.AD(deflt = "email", required = false)
	public String notificationDefaultType();

	@Meta.AD(
		deflt = "${resource:com/liferay/calendar/configuration/dependencies/rss/booking_entry.tmpl}",
		required = false
	)
	public LocalizedValuesMap rssBookingEntry();

	@Meta.AD(deflt = "true", required = false)
	public boolean syncCalEventsOnStartup();

}