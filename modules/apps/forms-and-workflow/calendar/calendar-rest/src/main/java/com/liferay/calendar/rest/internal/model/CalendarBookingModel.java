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

package com.liferay.calendar.rest.internal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class CalendarBookingModel {

	public CalendarBookingModel() {
		_allDay = false;
		_calendarBookingId = 0;
		_calendarId = 0;
		_description = null;
		_endTime = 0;
		_endTimeDay = 0;
		_endTimeHour = 0;
		_endTimeMinute = 0;
		_endTimeMonth = 0;
		_endTimeYear = 0;
		_firstReminder = 0;
		_firstReminderType = null;
		_childCalendarBookings = false;
		_workflowInstanceLink = false;
		_instanceIndex = 0;
		_location = null;
		_parentCalendarBookingId = 0;
		_recurrence = null;
		_recurringCalendarBookingId = 0;
		_secondReminder = 0;
		_secondReminderType = null;
		_title = null;
	}

	public CalendarBookingModel(
		boolean allDay, long calendarBookingId, long calendarId,
		String description, long endTime, int endTimeDay, int endTimeHour,
		int endTimeMinute, int endTimeMonth, int endTimeYear,
		long firstReminder, String firstReminderType,
		boolean childCalendarBookings, boolean workflowInstanceLink,
		int instanceIndex, String location, long parentCalendarBookingId,
		String recurrence, long recurringCalendarBookingId, long secondReminder,
		String secondReminderType, int status, long startTime, int startTimeDay,
		int startTimeHour, int startTimeMinute, int startTimeMonth,
		int startTimeYear, String title) {

		_allDay = allDay;
		_calendarBookingId = calendarBookingId;
		_calendarId = calendarId;
		_description = description;
		_endTime = endTime;
		_endTimeDay = endTimeDay;
		_endTimeHour = endTimeHour;
		_endTimeMinute = endTimeMinute;
		_endTimeMonth = endTimeMonth;
		_endTimeYear = endTimeYear;
		_firstReminder = firstReminder;
		_firstReminderType = firstReminderType;
		_childCalendarBookings = childCalendarBookings;
		_workflowInstanceLink = workflowInstanceLink;
		_instanceIndex = instanceIndex;
		_location = location;
		_parentCalendarBookingId = parentCalendarBookingId;
		_recurrence = recurrence;
		_recurringCalendarBookingId = recurringCalendarBookingId;
		_secondReminder = secondReminder;
		_secondReminderType = secondReminderType;
		_status = status;
		_startTime = startTime;
		_startTimeDay = startTimeDay;
		_startTimeHour = startTimeHour;
		_startTimeMinute = startTimeMinute;
		_startTimeMonth = startTimeMonth;
		_startTimeYear = startTimeYear;
		_title = title;
	}

	@XmlElement
	public long getCalendarBookingId() {
		return _calendarBookingId;
	}

	@XmlElement
	public long getCalendarId() {
		return _calendarId;
	}

	@XmlElement
	public String getDescription() {
		return _description;
	}

	@XmlElement
	public long getEndTime() {
		return _endTime;
	}

	@XmlElement
	public int getEndTimeDay() {
		return _endTimeDay;
	}

	@XmlElement
	public int getEndTimeHour() {
		return _endTimeHour;
	}

	@XmlElement
	public int getEndTimeMinute() {
		return _endTimeMinute;
	}

	@XmlElement
	public int getEndTimeMonth() {
		return _endTimeMonth;
	}

	@XmlElement
	public int getEndTimeYear() {
		return _endTimeYear;
	}

	@XmlElement
	public long getFirstReminder() {
		return _firstReminder;
	}

	@XmlElement
	public String getFirstReminderType() {
		return _firstReminderType;
	}

	@XmlElement
	public int getInstanceIndex() {
		return _instanceIndex;
	}

	@XmlElement
	public String getLocation() {
		return _location;
	}

	@XmlElement
	public long getParentCalendarBookingId() {
		return _parentCalendarBookingId;
	}

	@XmlElement
	public String getRecurrence() {
		return _recurrence;
	}

	@XmlElement
	public long getRecurringCalendarBookingId() {
		return _recurringCalendarBookingId;
	}

	@XmlElement
	public long getSecondReminder() {
		return _secondReminder;
	}

	@XmlElement
	public String getSecondReminderType() {
		return _secondReminderType;
	}

	@XmlElement
	public long getStartTime() {
		return _startTime;
	}

	@XmlElement
	public int getStartTimeDay() {
		return _startTimeDay;
	}

	@XmlElement
	public int getStartTimeHour() {
		return _startTimeHour;
	}

	@XmlElement
	public int getStartTimeMinute() {
		return _startTimeMinute;
	}

	@XmlElement
	public int getStartTimeMonth() {
		return _startTimeMonth;
	}

	@XmlElement
	public int getStartTimeYear() {
		return _startTimeYear;
	}

	@XmlElement
	public int getStatus() {
		return _status;
	}

	@XmlElement
	public String getTitle() {
		return _title;
	}

	@XmlElement
	public boolean hasChildCalendarBookings() {
		return _childCalendarBookings;
	}

	@XmlElement
	public boolean hasWorkflowInstanceLink() {
		return _workflowInstanceLink;
	}

	@XmlElement
	public boolean isAllDay() {
		return _allDay;
	}

	private final boolean _allDay;
	private final long _calendarBookingId;
	private final long _calendarId;
	private final boolean _childCalendarBookings;
	private final String _description;
	private final long _endTime;
	private final int _endTimeDay;
	private final int _endTimeHour;
	private final int _endTimeMinute;
	private final int _endTimeMonth;
	private final int _endTimeYear;
	private final long _firstReminder;
	private final String _firstReminderType;
	private final int _instanceIndex;
	private final String _location;
	private final long _parentCalendarBookingId;
	private final String _recurrence;
	private final long _recurringCalendarBookingId;
	private final long _secondReminder;
	private final String _secondReminderType;
	private long _startTime;
	private int _startTimeDay;
	private int _startTimeHour;
	private int _startTimeMinute;
	private int _startTimeMonth;
	private int _startTimeYear;
	private int _status;
	private final String _title;
	private final boolean _workflowInstanceLink;

}