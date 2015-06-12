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

package com.liferay.calendar.service.util;

import com.liferay.calendar.configuration.CalendarResourceServiceConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true)
public class CalendarServiceComponentProvider {

	public static CalendarServiceComponentProvider
		getCalendarServiceComponentProvider() {

		return _calendarServiceComponentProvider;
	}

	@Activate
	public void activate() {
		_calendarServiceComponentProvider = this;
	}

	@Deactivate
	public void deactivate() {
		_calendarServiceComponentProvider = null;
	}

	public CalendarResourceServiceConfiguration getCalendarResourceServiceConfiguration(
	) {

		return _calendarResourceServiceConfiguration;
	}

	@Reference
	public void setCalendarResourceServiceConfiguration(
		CalendarResourceServiceConfiguration calendarResourceServiceConfiguration) {

		_calendarResourceServiceConfiguration =
			calendarResourceServiceConfiguration;
	}

	protected void unsetCalendarResourceServiceConfiguration(
		CalendarResourceServiceConfiguration CalendarResourceServiceConfiguration) {

		_calendarResourceServiceConfiguration = null;
	}

	private static CalendarServiceComponentProvider _calendarServiceComponentProvider;

	private CalendarResourceServiceConfiguration _calendarResourceServiceConfiguration;

}