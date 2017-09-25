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

import com.liferay.calendar.exception.NoSuchBookingException;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.vulcan.identifier.LongIdentifier;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.function.BiFunction;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true)
public class CalendarBookingResource
	implements Resource<CalendarBooking, LongIdentifier> {

	@Override
	public void buildRepresentor(
		RepresentorBuilder<CalendarBooking, LongIdentifier>
			representorBuilder) {

//		Function<Date, Object> formatFunction = date -> {
//			if (date == null) {
//				return null;
//			}
//
//			DateFormat dateFormat = DateUtil.getISO8601Format();

//
//			return dateFormat.format(date);
//		};

		BiFunction<CalendarBooking, Integer, Integer> endTime =
			(calendarBooking, field) -> _getCalendarComponent(
				calendarBooking.getEndTime(), calendarBooking.getTimeZone(),
				field);
		BiFunction<CalendarBooking, Integer, Integer> startTime =
			(calendarBooking, field) -> _getCalendarComponent(
				calendarBooking.getStartTime(), calendarBooking.getTimeZone(),
				field);

		representorBuilder.identifier(
			calendarBooking -> calendarBooking::getCalendarBookingId
		).addField(
				"calendarBookingId", CalendarBooking::getCalendarBookingId
		).addField(
			"calendarId", CalendarBooking::getCalendarId
		).addField(
			"description", CalendarBooking::getDescription
		).addField(
			"endTime", CalendarBooking::getEndTime
		).addField(
			"endTimeDay",
			calendarBooking -> endTime.apply(calendarBooking, Calendar.DAY_OF_MONTH)
		).addField(
			"endTimeHour",
			calendarBooking -> endTime.apply(calendarBooking, Calendar.HOUR_OF_DAY)
		).addField(
			"endTimeMinute",
			calendarBooking -> endTime.apply(calendarBooking, Calendar.MINUTE)
		).addField(
			"endTimeMonth",
			calendarBooking -> endTime.apply(calendarBooking, Calendar.MONTH)
		).addField(
			"endTimeYear",
			calendarBooking -> endTime.apply(calendarBooking, Calendar.YEAR)
		).addField(
			"firstReminder", CalendarBooking::getFirstReminder
		).addField(
			"firstReminderType", CalendarBooking::getFirstReminderType
		).addField(
			"instanceIndex", CalendarBooking::getInstanceIndex
		).addField(
			"location", CalendarBooking::getLocation
		).addField(
			"parentCalendarBookingId", CalendarBooking::getParentCalendarBookingId
		).addField(
			"recurrence", CalendarBooking::getRecurrence
		).addField(
			"recurringCalendarBookingId", CalendarBooking::getRecurringCalendarBookingId
		).addField(
			"secondReminder", CalendarBooking::getSecondReminder
		).addField(
			"secondReminderType", CalendarBooking::getSecondReminderType
		).addField(
			"startTime", CalendarBooking::getStartTime
		).addField(
			"startTimeDay",
			calendarBooking -> startTime.apply(calendarBooking, Calendar.DAY_OF_MONTH)
		).addField(
			"startTimeHour",
			calendarBooking -> startTime.apply(calendarBooking, Calendar.HOUR_OF_DAY)
		).addField(
			"startTimeMinute",
			calendarBooking -> startTime.apply(calendarBooking, Calendar.MINUTE)
		).addField(
			"startTimeMonth",
			calendarBooking -> startTime.apply(calendarBooking, Calendar.MONTH)
		).addField(
			"startTimeYear",
			calendarBooking -> startTime.apply(calendarBooking, Calendar.YEAR)
		).addField(
			"status", CalendarBooking::getStatus
		).addField(
			"title", CalendarBooking::getTitle
		).addField(
			"childCalendarBookings", calendarBooking -> {
				List<CalendarBooking> childCalendarBookings =
					calendarBooking.getChildCalendarBookings();

				return childCalendarBookings.size() > 1;
			}
		).addField(
			"workflowInstanceLink", calendarBooking ->
				WorkflowInstanceLinkLocalServiceUtil.hasWorkflowInstanceLink(
					calendarBooking.getCompanyId(),
					calendarBooking.getGroupId(),
					CalendarBooking.class.getName(),
					calendarBooking.getCalendarBookingId())
		).addField(
			"allDay", CalendarBooking::isAllDay
		);
	}

//	@GET
//	@Path("/search")
//	@Produces("application/json")
//	public List<CalendarBookingModel> getCalendarBookings(
//		@Context Company company, @Context Locale locale,
//		@QueryParam("calendarIds") long[] calendarIds,
//		@QueryParam("startTime") String startTime,
//		@QueryParam("endTime") String endTime,
//		@QueryParam("statuses") int[] statuses,
//		@QueryParam("eventsperPage") int eventsPerPage) {
//
//		try {
//			DateFormat df = new SimpleDateFormat(
//				"yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

//
//			Date startDate = df.parse(startTime);
//			Date endDate = df.parse(endTime);

//
//			List<CalendarBooking> calendarBookings =
//				_calendarBookingHelper.search(
//					company.getCompanyId(), calendarIds, startDate.getTime(),
//					endDate.getTime(), statuses, eventsPerPage);
//
//			return _calendarBookingConverter.toCalendarBookingModels(
//				calendarBookings, locale);
//		}
//		catch (ParseException | PortalException e) {
//			throw new SystemException(e);
//		}
//	}

	@Override
	public String getPath() {
		return "calendar";
	}

	@Override
	public Routes<CalendarBooking> routes(
		RoutesBuilder<CalendarBooking, LongIdentifier> routesBuilder) {

		return routesBuilder.collectionItem(
			this::_getCalendarBooking
		).build();
	}

	private CalendarBooking _getCalendarBooking(
		LongIdentifier calendarBookingLongIdentifier) {

		try {
			return _calendarBookingService.getCalendarBooking(
				calendarBookingLongIdentifier.getIdAsLong());
		}
		catch (NoSuchBookingException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get calendar booking " +
					calendarBookingLongIdentifier.getIdAsLong(),
				e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private int _getCalendarComponent(long time, TimeZone timeZone, int field) {
		Calendar jCalendar = CalendarFactoryUtil.getCalendar(time, timeZone);

		return jCalendar.get(field);
	}

	@Reference
	private CalendarBookingService _calendarBookingService;

}