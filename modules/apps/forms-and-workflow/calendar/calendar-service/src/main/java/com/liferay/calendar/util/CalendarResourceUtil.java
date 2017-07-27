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

package com.liferay.calendar.util;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.search.CalendarSearcher;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceServiceUtil;
import com.liferay.calendar.service.permission.CalendarPermission;
import com.liferay.calendar.util.comparator.CalendarResourceCodeComparator;
import com.liferay.calendar.util.comparator.CalendarResourceNameComparator;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 * @author Marcellus Tavares
 */
public class CalendarResourceUtil {

	public static CalendarResource fetchGuestCalendarResource(long companyId)
		throws PortalException {

		return CalendarResourceLocalServiceUtil.fetchCalendarResource(
			PortalUtil.getClassNameId(User.class),
			UserLocalServiceUtil.getDefaultUserId(companyId));
	}

	public static CalendarResource getCalendarResource(
			long classNameId, long classPK, ServiceContext serviceContext)
		throws PortalException {

		long groupClassNameId = PortalUtil.getClassNameId(Group.class);

		if (classNameId == groupClassNameId) {
			return getGroupCalendarResource(classPK, serviceContext);
		}

		long userClassNameId = PortalUtil.getClassNameId(User.class);

		if (classNameId == userClassNameId) {
			serviceContext.setUserId(classPK);

			return getUserCalendarResource(classPK, serviceContext);
		}

		return CalendarResourceServiceUtil.fetchCalendarResource(
			classNameId, classPK);
	}

	public static CalendarResource getCalendarResource(
			PortletRequest portletRequest, long classNameId, long classPK)
		throws PortalException {

		long groupClassNameId = PortalUtil.getClassNameId(Group.class);

		if (classNameId == groupClassNameId) {
			return getGroupCalendarResource(portletRequest, classPK);
		}

		long userClassNameId = PortalUtil.getClassNameId(User.class);

		if (classNameId == userClassNameId) {
			return getUserCalendarResource(portletRequest, classPK);
		}

		return CalendarResourceServiceUtil.fetchCalendarResource(
			classNameId, classPK);
	}

	public static CalendarResource getGroupCalendarResource(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isUser()) {
			return null;
		}

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				PortalUtil.getClassNameId(Group.class), groupId);

		if (calendarResource != null) {
			return calendarResource;
		}
		else if (group.hasStagingGroup()) {
			return null;
		}

		long userId = group.getCreatorUserId();

		User user = UserLocalServiceUtil.fetchUserById(userId);

		if ((user == null) || user.isDefaultUser()) {
			Role role = RoleLocalServiceUtil.getRole(
				group.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] userIds = UserLocalServiceUtil.getRoleUserIds(
				role.getRoleId());

			userId = userIds[0];
		}

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), group.getDescriptiveName());

		Map<Locale, String> descriptionMap = new HashMap<>();

		return CalendarResourceLocalServiceUtil.addCalendarResource(
			userId, groupId, PortalUtil.getClassNameId(Group.class), groupId,
			null, null, nameMap, descriptionMap, true, serviceContext);
	}

	public static CalendarResource getGroupCalendarResource(
			PortletRequest portletRequest, long groupId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		return getGroupCalendarResource(groupId, serviceContext);
	}

	public static OrderByComparator<CalendarResource> getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CalendarResource> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CalendarResourceNameComparator(orderByAsc);
		}
		else {
			orderByComparator = new CalendarResourceCodeComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static CalendarResource getScopeGroupCalendarResource(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isUser()) {
			return getUserCalendarResource(group.getClassPK(), serviceContext);
		}
		else {
			return getGroupCalendarResource(groupId, serviceContext);
		}
	}

	public static CalendarResource getScopeGroupCalendarResource(
			PortletRequest portletRequest, long groupId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		return getScopeGroupCalendarResource(groupId, serviceContext);
	}

	public static CalendarResource getUserCalendarResource(
			long userId, ServiceContext serviceContext)
		throws PortalException {

		long classNameId = PortalUtil.getClassNameId(User.class);

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				classNameId, userId);

		if (calendarResource != null) {
			return calendarResource;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		Group userGroup = null;

		String userName = user.getFullName();

		if (user.isDefaultUser()) {
			userGroup = GroupLocalServiceUtil.getGroup(
				serviceContext.getCompanyId(), GroupConstants.GUEST);

			userName = GroupConstants.GUEST;
		}
		else {
			userGroup = GroupLocalServiceUtil.getUserGroup(
				serviceContext.getCompanyId(), userId);
		}

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), userName);

		Map<Locale, String> descriptionMap = new HashMap<>();

		return CalendarResourceLocalServiceUtil.addCalendarResource(
			userId, userGroup.getGroupId(),
			PortalUtil.getClassNameId(User.class), userId, null, null, nameMap,
			descriptionMap, true, serviceContext);
	}

	public static CalendarResource getUserCalendarResource(
			PortletRequest portletRequest, long userId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		serviceContext.setUserId(userId);

		return getUserCalendarResource(userId, serviceContext);
	}

	public static Set<Calendar> searchAndCreateCalendars(
			PortletRequest portletRequest, String keywords)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set<Calendar> calendarsSet = new LinkedHashSet<>();

		Hits hits = search(themeDisplay, keywords);

		for (Document document : hits.getDocs()) {
			long calendarId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			Calendar calendar = CalendarLocalServiceUtil.getCalendar(
				calendarId);

			CalendarResource calendarResource = calendar.getCalendarResource();

			if (calendarResource.isActive()) {
				Group group = GroupLocalServiceUtil.getGroup(
					calendar.getGroupId());

				if (group.hasStagingGroup()) {
					Group stagingGroup = group.getStagingGroup();

					long stagingGroupId = stagingGroup.getGroupId();

					if (stagingGroupId == themeDisplay.getScopeGroupId()) {
						calendar =
							CalendarLocalServiceUtil.
								fetchCalendarByUuidAndGroupId(
									calendar.getUuid(), stagingGroupId);
					}
				}

				calendarsSet.add(calendar);
			}
		}

		String name = StringUtil.merge(
			CustomSQLUtil.keywords(keywords), StringPool.BLANK);

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersGroups", themeDisplay.getUserId());

		List<Group> groups = GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), name, null, params, true, 0,
			SearchContainer.DEFAULT_DELTA);

		for (Group group : groups) {
			long groupClassNameId = PortalUtil.getClassNameId(Group.class);

			addCalendar(
				portletRequest, calendarsSet, groupClassNameId,
				group.getGroupId());
		}

		long userClassNameId = PortalUtil.getClassNameId(User.class);

		List<User> users = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), keywords, 0, null, 0,
			SearchContainer.DEFAULT_DELTA, new UserFirstNameComparator());

		for (User user : users) {
			addCalendar(
				portletRequest, calendarsSet, userClassNameId,
				user.getUserId());
		}

		return calendarsSet;
	}

	protected static void addCalendar(
			PortletRequest portletRequest, Set<Calendar> calendarsSet,
			long classNameId, long classPK)
		throws PortalException {

		CalendarResource calendarResource = getCalendarResource(
			portletRequest, classNameId, classPK);

		if (calendarResource == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		List<Calendar> calendars =
			CalendarLocalServiceUtil.getCalendarResourceCalendars(
				calendarResource.getGroupId(),
				calendarResource.getCalendarResourceId());

		for (Calendar calendar : calendars) {
			if (!CalendarPermission.contains(
					permissionChecker, calendar, ActionKeys.VIEW)) {

				continue;
			}

			calendarsSet.add(calendar);
		}
	}

	protected static Hits search(ThemeDisplay themeDisplay, String keywords)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		keywords = StringUtil.toLowerCase(keywords);

		searchContext.setAttribute(Field.NAME, keywords);
		searchContext.setAttribute("resourceName", keywords);

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setEnd(SearchContainer.DEFAULT_DELTA);
		searchContext.setGroupIds(new long[0]);

		Group group = themeDisplay.getScopeGroup();

		searchContext.setIncludeStagingGroups(group.isStagingGroup());

		searchContext.setStart(0);
		searchContext.setUserId(themeDisplay.getUserId());

		Indexer<?> indexer = CalendarSearcher.getInstance();

		return indexer.search(searchContext);
	}

}