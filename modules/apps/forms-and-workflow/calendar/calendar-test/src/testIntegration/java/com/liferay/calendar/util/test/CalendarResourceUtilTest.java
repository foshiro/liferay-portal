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

package com.liferay.calendar.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarStagingTestUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarResourceUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_company = CompanyLocalServiceUtil.getCompany(_group.getCompanyId());

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_groupClassNameId = PortalUtil.getClassNameId(Group.class);
		_userClassNameId = PortalUtil.getClassNameId(User.class);
	}

	@After
	public void tearDown() {
		CalendarStagingTestUtil.cleanUp();

		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	@Test
	public void testGetGroupCalendarFetchesSameResource()
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource createdCalendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Assert.assertNotNull(createdCalendarResource);

		CalendarResource fetchedCalendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Assert.assertNotNull(fetchedCalendarResource);
		Assert.assertEquals(
			createdCalendarResource.getCalendarResourceId(),
			fetchedCalendarResource.getCalendarResourceId());
	}

	@Test
	public void testGetGroupCalendarResourceCreatesResource()
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Assert.assertNotNull(calendarResource);
	}

	@Test
	public void testGetGroupCalendarResourceCreatesStagingCalendarResource()
		throws Exception {

		GroupTestUtil.enableLocalStaging(_group);

		Group stagingGroup = _group.getStagingGroup();

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				stagingGroup.getGroupId(), serviceContext);

		Assert.assertNotNull(calendarResource);
	}

	@Test
	public void testGetGroupCalendarResourceDoesNotCreateLiveCalendarResource()
		throws Exception {

		GroupTestUtil.enableLocalStaging(_group);

		ServiceContext serviceContext = new ServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				_group.getGroupId(), serviceContext);

		Assert.assertNull(calendarResource);
	}

	@Test
	public void testSearchAndCreateCalendarsReturnsGroupCalendar()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		String keywords = _group.getNameCurrentValue();

		Set<Calendar> calendars = CalendarResourceUtil.searchAndCreateCalendars(
			_company, _user, _group, keywords, permissionChecker,
			serviceContext);

		Group group = _group;

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				_groupClassNameId, group.getGroupId());

		Calendar calendar = calendarResource.getDefaultCalendar();

		Assert.assertTrue(calendars.contains(calendar));
	}

	private Company _company;

	@DeleteAfterTestRun
	private Group _group;

	private long _groupClassNameId;
	private PermissionChecker _permissionChecker;

	@DeleteAfterTestRun
	private User _user;

	private long _userClassNameId;

}