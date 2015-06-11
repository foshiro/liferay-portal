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

package com.liferay.calendar.configuration.impl;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.calendar.configuration.CalendarConfiguration;
import com.liferay.calendar.configuration.CalendarConfigurationHolder;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid =
		"com.liferay.calendar.configuration.CalendarConfiguration",
	immediate = true, service = CalendarConfigurationHolder.class
)
public class CalendarConfigurationHolderImpl
	implements CalendarConfigurationHolder {

	public CalendarConfiguration getCalendarConfiguration() {
		return _calendarConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_calendarConfiguration = Configurable.createConfigurable(
			CalendarConfiguration.class, properties);
	}

	private CalendarConfiguration _calendarConfiguration;

}