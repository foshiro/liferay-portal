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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Adam Brandizzi
 */
public class CalendarBookingStatusUpdateFactoryImpl
	implements CalendarBookingStatusUpdateFactory {

	@Override
	public CalendarBookingStatusUpdate getChildCalendarBookingStatusUpdate(
		int newParentStatus, int oldParentStatus, int oldChildStatus,
		boolean stagingParent) {

		return new CalendarBookingStatusUpdateImpl(
			getNewChildStatus(
				newParentStatus, oldParentStatus, oldChildStatus,
				stagingParent),
			isChildStatusUpdateNeeded(
				newParentStatus, oldParentStatus, oldChildStatus));
	}

	protected int getNewChildStatus(
		int newParentStatus, int oldParentStatus, int oldChildStatus,
		boolean stagingParent) {

		if (newParentStatus == WorkflowConstants.STATUS_IN_TRASH) {
			return CalendarBookingWorkflowConstants.STATUS_IN_TRASH;
		}

		if (oldParentStatus == WorkflowConstants.STATUS_IN_TRASH) {
			return CalendarBookingWorkflowConstants.STATUS_PENDING;
		}

		if (newParentStatus != WorkflowConstants.STATUS_APPROVED) {
			return CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING;
		}

		if (oldChildStatus ==
				CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING) {

			if (stagingParent) {
				return CalendarBookingWorkflowConstants.STATUS_MASTER_STAGING;
			}
			else {
				return CalendarBookingWorkflowConstants.STATUS_PENDING;
			}
		}

		return CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING;
	}

	protected boolean isChildStatusUpdateNeeded(
		int newParentStatus, int oldParentStatus, int oldChildStatus) {

		if (newParentStatus != WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		if (oldParentStatus == WorkflowConstants.STATUS_IN_TRASH) {
			return true;
		}

		if (oldChildStatus ==
				CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING) {

			return true;
		}

		return false;
	}

}