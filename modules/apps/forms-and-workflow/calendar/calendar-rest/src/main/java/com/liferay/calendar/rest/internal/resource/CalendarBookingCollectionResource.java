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

package com.liferay.calendar.rest.internal.resource;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarBookingService;
import com.liferay.calendar.util.comparator.CalendarBookingStartTimeComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RepresentorBuilder.FirstStep;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.RootIdentifier;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Function;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides all the necessary information to expose <a
 * href="http://schema.org/Person">Person</a> resources through a web API.
 *
 * The resources are mapped from the internal {@link User} model.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true)
public class CalendarBookingCollectionResource
	implements CollectionResource<CalendarBooking, LongIdentifier> {

	@Override
	public Representor<CalendarBooking, LongIdentifier> buildRepresentor(
		RepresentorBuilder<CalendarBooking, LongIdentifier> representorBuilder) {

		FirstStep<CalendarBooking, LongIdentifier> stuff =
			representorBuilder.identifier(
				calendarBooking -> calendarBooking::getCalendarBookingId
			).addBoolean(
				"allDay", CalendarBooking::isAllDay
			).addNumber(
				"calendarId", CalendarBooking::getCalendarId
			).addString(
				"description", CalendarBooking::getDescription
			).addNumber(
				"firstReminder", CalendarBooking::getFirstReminder
			).addString(
				"firstReminderType", CalendarBooking::getFirstReminderType
			).addBoolean(
				"hasChildCalendarBookings", this::hasChildCalendarBookings
			).addBoolean(
				"hasWorkflowInstanceLink", this::hasWorkflowInstanceLink
			).addNumber(
				"instanceIndex", CalendarBooking::getInstanceIndex
			).addString(
				"location", CalendarBooking::getLocation
			).addNumber(
				"parentCalendarBookingId",
				CalendarBooking::getParentCalendarBookingId
			).addString( // TODO buggy, needs user TZ. TODO to schedule
				"recurrence", CalendarBooking::getRecurrence
			).addNumber(
				"recurringCalendarBookingId",
				CalendarBooking::getRecurringCalendarBookingId
			).addNumber(
				"secondReminder", CalendarBooking::getSecondReminder
			).addString(
				"secondReminderType", CalendarBooking::getSecondReminderType
			).addNumber(
				"status", CalendarBooking::getStatus
			).addString(
				"title", CalendarBooking::getTitle
			);

		_addTimeFields(stuff, "startTime", CalendarBooking::getStartTime);
		_addTimeFields(stuff, "endTime", CalendarBooking::getEndTime);
		
		return stuff.build();
	}

	private void _addTimeFields(
		FirstStep<CalendarBooking, LongIdentifier> stuff, String key,
		Function<CalendarBooking, Long> timeMethod) {


		stuff.addNumber(
			key + "Day",
			(calendarBooking) ->
				getCalendarField(
					calendarBooking, timeMethod, Calendar.DAY_OF_MONTH)
		).addNumber(
			key + "Hour",
			(calendarBooking) ->
				getCalendarField(
					calendarBooking, timeMethod, Calendar.HOUR_OF_DAY)
		).addNumber(
			key + "Minute",
			(calendarBooking) ->
				getCalendarField(calendarBooking, timeMethod, Calendar.MINUTE)
		).addNumber(
			key + "Month",
			(calendarBooking) ->
				getCalendarField(calendarBooking, timeMethod, Calendar.MONTH)
		).addNumber(
			key + "Year",
			(calendarBooking) ->
				getCalendarField(calendarBooking, timeMethod, Calendar.YEAR)
		);
	}

	protected Number getCalendarField(
			CalendarBooking calendarBooking,
			Function<CalendarBooking, Long> timeMethod, int calendarField) {

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			timeMethod.apply(calendarBooking),
			calendarBooking.getTimeZone());

		return calendar.get(calendarField);
	}

	protected boolean hasChildCalendarBookings(
		CalendarBooking calendarBooking) {

		List<CalendarBooking> childCalendarBookings =
			calendarBooking.getChildCalendarBookings();

		return childCalendarBookings.size() > 1;
	}

	protected Boolean hasWorkflowInstanceLink(CalendarBooking calendarBooking) {
		return WorkflowInstanceLinkLocalServiceUtil.hasWorkflowInstanceLink(
			calendarBooking.getCompanyId(), calendarBooking.getGroupId(),
			CalendarBooking.class.getName(),
			calendarBooking.getCalendarBookingId());
	}

	@Override
	public String getName() {
		return "calendarBooking";
	}

	@Override
	public Routes<CalendarBooking> routes(
			RoutesBuilder<CalendarBooking, LongIdentifier> routesBuilder) {

		routesBuilder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class, Company.class,
			TimeZone.class
		);
	}

	private PageItems<CalendarBooking> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier, Company company,
		TimeZone timeZone) {

		List<CalendarBooking> calendarBookings =
				Collections.<CalendarBooking>emptyList();

		if (!ArrayUtil.isEmpty(calendarIds)) {
			calendarBookings = _calendarBookingService.search(
				company.getCompanyId(), new long[0], calendarIds,
				new long[0], -1, null, startTimeJCalendar.getTimeInMillis(),
				endTimeJCalendar.getTimeInMillis(), true, statuses,
				pagination.getStartPosition(), pagination.getEndPosition(),
				new CalendarBookingStartTimeComparator(true));

			int eventsPerPage = ParamUtil.getInteger(
				resourceRequest, "eventsPerPage");

			if ((eventsPerPage > 0) &&
				(eventsPerPage < calendarBookings.size())) {

				calendarBookings = calendarBookings.subList(0, eventsPerPage);
			}
		}

		List<CalendarBooking> calendarBookings = CalendarBookingLocalServiceUtil.getBlogPosts(
			pagination.getStartPosition(), pagination.getEndPosition());

		int count = BlogPost.getBlogPostsCount();

		return new PageItems<>(blogsEntries, count);
	}
	
	@Reference
	private CalendarBookingService _calendarBookingService; 

	
}