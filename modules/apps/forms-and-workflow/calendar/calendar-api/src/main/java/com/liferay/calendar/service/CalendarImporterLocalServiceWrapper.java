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

package com.liferay.calendar.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalendarImporterLocalService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarImporterLocalService
 * @generated
 */
@ProviderType
public class CalendarImporterLocalServiceWrapper
	implements CalendarImporterLocalService,
		ServiceWrapper<CalendarImporterLocalService> {
	public CalendarImporterLocalServiceWrapper(
		CalendarImporterLocalService calendarImporterLocalService) {
		_calendarImporterLocalService = calendarImporterLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _calendarImporterLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public void importCalEvent(java.lang.String uuid, long eventId,
		long _groupId, long companyId, long userId, java.lang.String userName,
		java.sql.Timestamp createDate, java.sql.Timestamp modifiedDate,
		java.lang.String title, java.lang.String description,
		java.lang.String location, java.sql.Timestamp startDate,
		int durationHour, int durationMinute, boolean allDay,
		java.lang.String type, java.lang.String recurrence, int firstReminder,
		int secondReminder) {
		_calendarImporterLocalService.importCalEvent(uuid, eventId, _groupId,
			companyId, userId, userName, createDate, modifiedDate, title,
			description, location, startDate, durationHour, durationMinute,
			allDay, type, recurrence, firstReminder, secondReminder);
	}

	@Override
	public void importRolePermissions() {
		_calendarImporterLocalService.importRolePermissions();
	}

	@Override
	public CalendarImporterLocalService getWrappedService() {
		return _calendarImporterLocalService;
	}

	@Override
	public void setWrappedService(
		CalendarImporterLocalService calendarImporterLocalService) {
		_calendarImporterLocalService = calendarImporterLocalService;
	}

	private CalendarImporterLocalService _calendarImporterLocalService;
}