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

package com.liferay.calendar.internal.status.update;

import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class CalendarStatusUpdateFactoryTest {

	@Before
	public void setUp() {
		_statusUpdateFactory = new CalendarBookingStatusUpdateFactoryImpl();
	}

	@Test
	public void testChildStatusInTrashIfParentInTrash() {
		CalendarBookingStatusUpdate statusUpdate =
			getStatusUpdateForParentGettingIntoTrash();

		assertNewStatus(
			statusUpdate, CalendarBookingWorkflowConstants.STATUS_IN_TRASH);
	}

	@Test
	public void testChildStatusMasterPendingIfParentNotApproved() {
		CalendarBookingStatusUpdate statusUpdate =
			getStatusUpdateForParentNotApproved();

		assertNewStatus(
			statusUpdate,
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING);
	}

	@Test
	public void testChildStatusMasterStagingIfParentApprovedAndStagedAndChildMasterPending() {
		CalendarBookingStatusUpdate statusUpdate =
			getStatusUpdateForApprovedStagedParent();

		assertNewStatus(
			statusUpdate,
			CalendarBookingWorkflowConstants.STATUS_MASTER_STAGING);
	}

	@Test
	public void testChildStatusPendingIfParentApprovedAndChildMasterPending() {
		CalendarBookingStatusUpdate statusUpdate =
			getStatusUpdateForApprovedParent();

		assertNewStatus(
			statusUpdate, CalendarBookingWorkflowConstants.STATUS_PENDING);
	}

	@Test
	public void testChildStatusPendingIfParentFromTrash() {
		CalendarBookingStatusUpdate statusUpdate =
			getStatusUpdateForParentComingFromTrash();

		assertNewStatus(
			statusUpdate, CalendarBookingWorkflowConstants.STATUS_PENDING);
	}

	@Test
	public void testIsChildStatusUpdatedNeededIfParentApprovedAndChildMasterPending() {
		CalendarBookingStatusUpdate statusUpdate =
			_statusUpdateFactory.getChildCalendarBookingStatusUpdate(
				CalendarBookingWorkflowConstants.STATUS_APPROVED, _anyStatus(),
				CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
				RandomTestUtil.randomBoolean());

		assertUpdateNeeded(statusUpdate);
	}

	@Test
	public void testIsChildStatusUpdatedNeededIfParentApprovedFromTrash() {
		CalendarBookingStatusUpdate statusUpdate =
			_statusUpdateFactory.getChildCalendarBookingStatusUpdate(
				CalendarBookingWorkflowConstants.STATUS_APPROVED,
				CalendarBookingWorkflowConstants.STATUS_IN_TRASH, _anyStatus(),
				RandomTestUtil.randomBoolean());

		assertUpdateNeeded(statusUpdate);
	}

	@Test
	public void testIsChildStatusUpdatedNeededIfParentNotApproved() {
		CalendarBookingStatusUpdate statusUpdate =
			_statusUpdateFactory.getChildCalendarBookingStatusUpdate(
				_anyStatusExcept(
					CalendarBookingWorkflowConstants.STATUS_APPROVED),
				_anyStatus(), _anyStatus(), RandomTestUtil.randomBoolean());

		assertUpdateNeeded(statusUpdate);
	}

	@Test
	public void testIsChildStatusUpdatedNotNeededIfParentApprovedAndChildNotMasterPending() {
		CalendarBookingStatusUpdate statusUpdate =
			_statusUpdateFactory.getChildCalendarBookingStatusUpdate(
				CalendarBookingWorkflowConstants.STATUS_APPROVED,
				_anyStatusExcept(
					CalendarBookingWorkflowConstants.STATUS_IN_TRASH),
				_anyStatusExcept(
					CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING),
				RandomTestUtil.randomBoolean());

		assertUpdateNotNeeded(statusUpdate);
	}

	protected void assertNewStatus(
		CalendarBookingStatusUpdate statusUpdate, int expectedStatus) {

		Assert.assertEquals(expectedStatus, statusUpdate.getNewStatus());
	}

	protected void assertUpdateNeeded(
		CalendarBookingStatusUpdate statusUpdate) {

		Assert.assertTrue(statusUpdate.isUpdateNeeded());
	}

	protected void assertUpdateNotNeeded(
		CalendarBookingStatusUpdate statusUpdate) {

		Assert.assertFalse(statusUpdate.isUpdateNeeded());
	}

	protected CalendarBookingStatusUpdate getStatusUpdateForApprovedParent() {
		return _statusUpdateFactory.getChildCalendarBookingStatusUpdate(
			CalendarBookingWorkflowConstants.STATUS_APPROVED,
			_anyStatusExcept(CalendarBookingWorkflowConstants.STATUS_IN_TRASH),
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING, false);
	}

	protected CalendarBookingStatusUpdate
			getStatusUpdateForApprovedStagedParent() {

		return _statusUpdateFactory.getChildCalendarBookingStatusUpdate(
			CalendarBookingWorkflowConstants.STATUS_APPROVED,
			_anyStatusExcept(CalendarBookingWorkflowConstants.STATUS_IN_TRASH),
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING, true);
	}

	protected CalendarBookingStatusUpdate getStatusUpdateForParentComingFromTrash() {
		return _statusUpdateFactory.getChildCalendarBookingStatusUpdate(
			_anyStatusExcept(CalendarBookingWorkflowConstants.STATUS_IN_TRASH),
			CalendarBookingWorkflowConstants.STATUS_IN_TRASH, _anyStatus(),
			RandomTestUtil.randomBoolean());
	}

	protected CalendarBookingStatusUpdate getStatusUpdateForParentGettingIntoTrash() {
		return _statusUpdateFactory.getChildCalendarBookingStatusUpdate(
			CalendarBookingWorkflowConstants.STATUS_IN_TRASH, _anyStatus(),
			_anyStatus(), RandomTestUtil.randomBoolean());
	}

	protected CalendarBookingStatusUpdate getStatusUpdateForParentNotApproved() {
		return _statusUpdateFactory.getChildCalendarBookingStatusUpdate(
			_anyStatusExcept(
				CalendarBookingWorkflowConstants.STATUS_APPROVED,
				CalendarBookingWorkflowConstants.STATUS_IN_TRASH),
			_anyStatusExcept(CalendarBookingWorkflowConstants.STATUS_IN_TRASH),
			_anyStatus(), RandomTestUtil.randomBoolean());
	}

	private int _anyStatus() {
		int index =
			RandomTestUtil.randomInt() %
				_calendarBookingWorkflowConstants.length;

		return _calendarBookingWorkflowConstants[index];
	}

	private int _anyStatusExcept(int... exceptions) {
		int status = 0;

		do {
			status = _anyStatus();
		}
		while (ArrayUtil.contains(exceptions, status));

		return status;
	}

	private static final int[] _calendarBookingWorkflowConstants = {
		CalendarBookingWorkflowConstants.STATUS_APPROVED,
		CalendarBookingWorkflowConstants.STATUS_DENIED,
		CalendarBookingWorkflowConstants.STATUS_DRAFT,
		CalendarBookingWorkflowConstants.STATUS_IN_TRASH,
		CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
		CalendarBookingWorkflowConstants.STATUS_MASTER_STAGING,
		CalendarBookingWorkflowConstants.STATUS_MAYBE,
		CalendarBookingWorkflowConstants.STATUS_PENDING
	};

	private CalendarBookingStatusUpdateFactory _statusUpdateFactory;

}