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

package com.liferay.calendar.web.display.context;

import com.liferay.calendar.constants.CalendarWebKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.calendar.web.display.context.util.CalendarRequestHelper;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adam Brandizzi
 */
public class CalendarDisplayContext {

	private HttpServletRequest _request;
	private CalendarBooking _calendarBooking;
	private CalendarRequestHelper _requestHelper;
	private CalendarService _calendarService;

	public CalendarDisplayContext(
		HttpServletRequest request,
		CalendarService calendarService) {

		_request = request;
		_calendarService = calendarService;
		_requestHelper = new CalendarRequestHelper(_request);
	}

	public CalendarBooking getCalendarBooking() {
		if (_calendarBooking == null) {
			_calendarBooking = (CalendarBooking)_request.getAttribute(
				CalendarWebKeys.CALENDAR_BOOKING);
		}

		return _calendarBooking;
	}

	public CalendarResource getGroupCalendarResource() {
		if (_groupCalendarResource == null) {
			_groupCalendarResource =
				CalendarResourceUtil.getScopeGroupCalendarResource(
					_requestHelper.getLiferayPortletRequest(),
					_requestHelper.getScopeGroupId());
		}

		return _groupCalendarResource;
	}

	public CalendarResource getUserCalendarResource() {
		if (_userCalendarResource == null) {
			_userCalendarResource =
				CalendarResourceUtil.getUserCalendarResource(
					_requestHelper.getLiferayPortletRequest(),
					_requestHelper.getUserId());
		}

		return _userCalendarResource;
	}
	
	public Calendar getUserDefaultCalendar() {
		CalendarResource userCalendarResource = getUserCalendarResource();

		if ((_userDefaultCalendar == null) && (userCalendarResource == null)) {
			long defaultCalendarId =
					userCalendarResource.getDefaultCalendarId();

			if (defaultCalendarId > 0) {
				_userDefaultCalendar = _calendarService.getCalendar(
					defaultCalendarId);
			}
		}

		return _userDefaultCalendar;
	}
	
	private CalendarResource _groupCalendarResource;
	private CalendarResource _userCalendarResource;
	private Calendar _userDefaultCalendar;

	public int getDefaultDuration() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue("defaultDuration", null), 60);
	}

	public String getDefaultView() {
		return _portletPreferences.getValue("defaultView", "week");
	}
	
	public String getTimeFormat() {
		return _portletPreferences.getValue("timeFormat", "locale");
	}

	public TimeZone getTimeZone() {
		if (_timeZone == null) {
			User user = _requestHelper.getUser();
			String timeZoneId = portletPreferences.getValue(
				"timeZoneId", user.getTimeZoneId());

			if (isUsePortalTimeZone()) {
				timeZoneId = user.getTimeZoneId();
			}
		}
	}

	public boolean isUsePortalTimeZone() {
		return GetterUtil.getBoolean(
			portletPreferences.getValue("usePortalTimeZone",
				Boolean.TRUE.toString()));
	}

	public int getWeekStartsOn() {
		return GetterUtil.getInteger(
			portletPreferences.getValue("weekStartsOn", null), 0);
	}

	public String getSessionClicksDefaultView() {
		if (_sessionClicksDefaultView == null) {
			_sessionClicksDefaultView = SessionClicks.get(
				request, "com.liferay.calendar.web_defaultView",
				getDefaultView());
		}

		return _sessionClicksDefaultView;
	}

	public boolean isEnableRSS() {
		if(PortalUtil.isRSSFeedsEnabled()) {
			return GetterUtil.getBoolean(
				portletPreferences.getValue("enableRss", null), true);
		}
		else {
			return false;
		}
	}
}


int rssDelta = GetterUtil.getInteger(portletPreferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = portletPreferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
String rssFeedType = portletPreferences.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
long rssTimeInterval = GetterUtil.getLong(portletPreferences.getValue("rssTimeInterval", StringPool.BLANK), Time.WEEK);

TimeZone userTimeZone = CalendarUtil.getCalendarBookingDisplayTimeZone(calendarBooking, TimeZone.getTimeZone(timeZoneId));
TimeZone utcTimeZone = TimeZone.getTimeZone(StringPool.UTC);

Format dateFormatLongDate = FastDateFormatFactoryUtil.getDate(FastDateFormatConstants.LONG, locale, userTimeZone);

Format dateFormatTime = null;

boolean useIsoTimeFormat = timeFormat.equals("24-hour") || (timeFormat.equals("locale") && !DateUtil.isFormatAmPm(locale));

if (useIsoTimeFormat) {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat("HH:mm", locale, userTimeZone);
}
else {
	dateFormatTime = FastDateFormatFactoryUtil.getSimpleDateFormat("hh:mm a", locale, userTimeZone);
}
