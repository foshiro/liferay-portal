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

package com.liferay.calendar.calevent.upgrade.v1_0_0;

import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.recurrence.Weekday;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceBlockConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceBlockLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class UpgradeCalEvent extends UpgradeProcess {

	public UpgradeCalEvent(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetEntryLocalService assetEntryLocalService,
		AssetLinkLocalService assetLinkLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		CalendarBookingLocalService calendarBookingLocalService,
		CalendarResourceLocalService calendarResourceLocalService,
		ClassNameLocalService classNameLocalService,
		CounterLocalService counterLocalService,
		GroupLocalService groupLocalService,
		MBDiscussionLocalService mbDiscussionLocalService,
		MBMessageLocalService mbMessageLocalService,
		MBThreadLocalService mbThreadLocalService,
		RatingsEntryLocalService ratingsEntryLocalService,
		RatingsStatsLocalService ratingsStatsLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceBlockLocalService resourceBlockLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService,
		SocialActivityLocalService socialActivityLocalService,
		SubscriptionLocalService subscriptionLocalService,
		UserLocalService userLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetEntryLocalService = assetEntryLocalService;
		_assetLinkLocalService = assetLinkLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_calendarBookingLocalService = calendarBookingLocalService;
		_calendarResourceLocalService = calendarResourceLocalService;
		_classNameLocalService = classNameLocalService;
		_counterLocalService = counterLocalService;
		_groupLocalService = groupLocalService;
		_mbDiscussionLocalService = mbDiscussionLocalService;
		_mbMessageLocalService = mbMessageLocalService;
		_mbThreadLocalService = mbThreadLocalService;
		_ratingsEntryLocalService = ratingsEntryLocalService;
		_ratingsStatsLocalService = ratingsStatsLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceBlockLocalService = resourceBlockLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_socialActivityLocalService = socialActivityLocalService;
		_subscriptionLocalService = subscriptionLocalService;
		_userLocalService = userLocalService;

		_userClassNameId = _classNameLocalService.getClassNameId(User.class);
		_groupClassNameId = _classNameLocalService.getClassNameId(Group.class);
	}

	protected void addAssetEntry(
		long entryId, long groupId, long companyId, long userId,
		String userName, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String classUuid, boolean visible, Date startDate,
		Date endDate, Date publishDate, Date expirationDate, String mimeType,
		String title, String description, String summary, String url,
		String layoutUuid, int height, int width, double priority,
		int viewCount) {

		AssetEntry assetEntry = _assetEntryLocalService.createAssetEntry(
			entryId);

		assetEntry.setGroupId(groupId);
		assetEntry.setCompanyId(companyId);
		assetEntry.setUserId(userId);
		assetEntry.setUserName(userName);
		assetEntry.setCreateDate(createDate);
		assetEntry.setModifiedDate(modifiedDate);
		assetEntry.setClassNameId(classNameId);
		assetEntry.setClassPK(classPK);
		assetEntry.setClassUuid(classUuid);
		assetEntry.setVisible(visible);
		assetEntry.setStartDate(startDate);
		assetEntry.setEndDate(endDate);
		assetEntry.setPublishDate(publishDate);
		assetEntry.setExpirationDate(expirationDate);
		assetEntry.setMimeType(mimeType);
		assetEntry.setTitle(title);
		assetEntry.setDescription(description);
		assetEntry.setSummary(summary);
		assetEntry.setUrl(url);
		assetEntry.setLayoutUuid(layoutUuid);
		assetEntry.setHeight(height);
		assetEntry.setWidth(width);
		assetEntry.setPriority(priority);
		assetEntry.setViewCount(viewCount);

		_assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	protected void addAssetLink(
		long linkId, long companyId, long userId, String userName,
		Date createDate, long entryId1, long entryId2, int type, int weight) {

		AssetLink assetLink = _assetLinkLocalService.createAssetLink(linkId);

		assetLink.setCompanyId(companyId);
		assetLink.setUserId(userId);
		assetLink.setUserName(userName);
		assetLink.setCreateDate(createDate);
		assetLink.setEntryId1(entryId1);
		assetLink.setEntryId2(entryId2);
		assetLink.setType(type);
		assetLink.setWeight(weight);

		_assetLinkLocalService.updateAssetLink(assetLink);
	}

	protected CalendarBooking addCalendarBooking(
		String uuid, long calendarBookingId, long companyId, long groupId,
		long userId, String userName, Timestamp createDate,
		Timestamp modifiedDate, long calendarId, long calendarResourceId,
		String title, String description, String location, long startTime,
		long endTime, boolean allDay, String recurrence, int firstReminder,
		NotificationType firstReminderType, int secondReminder,
		NotificationType secondReminderType) {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.createCalendarBooking(
			calendarBookingId);

		calendarBooking.setUuid(uuid);
		calendarBooking.setCompanyId(companyId);
		calendarBooking.setGroupId(groupId);
		calendarBooking.setUserId(userId);
		calendarBooking.setUserName(userName);
		calendarBooking.setCreateDate(createDate);
		calendarBooking.setModifiedDate(modifiedDate);
		calendarBooking.setCalendarId(calendarId);
		calendarBooking.setCalendarResourceId(calendarResourceId);
		calendarBooking.setParentCalendarBookingId(calendarBookingId);
		calendarBooking.setVEventUid(uuid);
		calendarBooking.setTitle(title);
		calendarBooking.setDescription(description);
		calendarBooking.setLocation(location);
		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setAllDay(allDay);
		calendarBooking.setRecurrence(recurrence);
		calendarBooking.setFirstReminder(firstReminder);
		calendarBooking.setFirstReminderType(firstReminderType.toString());
		calendarBooking.setSecondReminder(secondReminder);
		calendarBooking.setSecondReminderType(secondReminderType.toString());
		calendarBooking.setStatus(WorkflowConstants.STATUS_APPROVED);
		calendarBooking.setStatusByUserId(userId);
		calendarBooking.setStatusByUserName(userName);
		calendarBooking.setStatusDate(createDate);

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	protected void addMBDiscussion(
		String uuid, long discussionId, long groupId, long companyId,
		long userId, String userName, Date createDate, Date modifiedDate,
		long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion =
			_mbDiscussionLocalService.createMBDiscussion(discussionId);

		mbDiscussion.setUuid(uuid);
		mbDiscussion.setGroupId(groupId);
		mbDiscussion.setCompanyId(companyId);
		mbDiscussion.setUserId(userId);
		mbDiscussion.setUserName(userName);
		mbDiscussion.setCreateDate(createDate);
		mbDiscussion.setModifiedDate(modifiedDate);
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);
		mbDiscussion.setThreadId(threadId);

		_mbDiscussionLocalService.updateMBDiscussion(mbDiscussion);
	}

	protected void addMBMessage(
			String uuid, long messageId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			long classNameId, long classPK, long categoryId, long threadId,
			long rootMessageId, long parentMessageId, String subject,
			String body, String format, boolean anonymous, double priority,
			boolean allowPingbacks, boolean answer, int status,
			long statusByUserId, String statusByUserName, Date statusDate,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		if (parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			rootMessageId = messageId;
		}
		else {
			rootMessageId = importMBMessage(
				rootMessageId, threadId, classPK, mbMessageIds);

			parentMessageId = importMBMessage(
				parentMessageId, threadId, classPK, mbMessageIds);
		}

		MBMessage mbMessage = _mbMessageLocalService.createMBMessage(messageId);

		mbMessage.setUuid(uuid);
		mbMessage.setGroupId(groupId);
		mbMessage.setCompanyId(companyId);
		mbMessage.setUserId(userId);
		mbMessage.setUserName(userName);
		mbMessage.setCreateDate(createDate);
		mbMessage.setModifiedDate(modifiedDate);
		mbMessage.setClassNameId(classNameId);
		mbMessage.setClassPK(classPK);
		mbMessage.setCategoryId(categoryId);
		mbMessage.setThreadId(threadId);
		mbMessage.setRootMessageId(rootMessageId);
		mbMessage.setParentMessageId(parentMessageId);
		mbMessage.setSubject(subject);
		mbMessage.setBody(body);
		mbMessage.setFormat(format);
		mbMessage.setAnonymous(anonymous);
		mbMessage.setPriority(priority);
		mbMessage.setAllowPingbacks(allowPingbacks);
		mbMessage.setAnswer(answer);
		mbMessage.setStatus(status);
		mbMessage.setStatusByUserId(statusByUserId);
		mbMessage.setStatusByUserName(statusByUserName);
		mbMessage.setStatusDate(statusDate);

		_mbMessageLocalService.updateMBMessage(mbMessage);
	}

	protected void addMBThread(
		String uuid, long threadId, long groupId, long companyId, long userId,
		String userName, Date createDate, Date modifiedDate, long categoryId,
		long rootMessageId, long rootMessageUserId, int messageCount,
		int viewCount, long lastPostByUserId, Date lastPostDate,
		double priority, boolean question, int status, long statusByUserId,
		String statusByUserName, Date statusDate) {

		MBThread mbThread = _mbThreadLocalService.createMBThread(threadId);

		mbThread.setUuid(uuid);
		mbThread.setGroupId(groupId);
		mbThread.setCompanyId(companyId);
		mbThread.setUserId(userId);
		mbThread.setUserName(userName);
		mbThread.setCreateDate(createDate);
		mbThread.setModifiedDate(modifiedDate);
		mbThread.setCategoryId(categoryId);
		mbThread.setRootMessageId(rootMessageId);
		mbThread.setRootMessageUserId(rootMessageUserId);
		mbThread.setMessageCount(messageCount);
		mbThread.setViewCount(viewCount);
		mbThread.setLastPostByUserId(lastPostByUserId);
		mbThread.setLastPostDate(lastPostDate);
		mbThread.setPriority(priority);
		mbThread.setQuestion(question);
		mbThread.setStatus(status);
		mbThread.setStatusByUserId(statusByUserId);
		mbThread.setStatusByUserName(statusByUserName);
		mbThread.setStatusDate(statusDate);

		_mbThreadLocalService.updateMBThread(mbThread);
	}

	protected RatingsEntry addRatingsEntry(
		long entryId, long companyId, long userId, String userName,
		Date createDate, Date modifiedDate, String className, long classPK,
		double score) {

		long classNameId = _classNameLocalService.getClassNameId(className);

		RatingsEntry ratingsEntry =
			_ratingsEntryLocalService.createRatingsEntry(entryId);

		ratingsEntry.setCompanyId(companyId);
		ratingsEntry.setUserId(userId);
		ratingsEntry.setUserName(userName);
		ratingsEntry.setCreateDate(createDate);
		ratingsEntry.setModifiedDate(modifiedDate);
		ratingsEntry.setClassNameId(classNameId);
		ratingsEntry.setClassPK(classPK);
		ratingsEntry.setScore(score);

		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);
	}

	protected RatingsStats addRatingsStats(
		long statsId, String className, long classPK, int totalEntries,
		double totalScore, double averageScore) {

		RatingsStats ratingsStats =
			_ratingsStatsLocalService.createRatingsStats(statsId);

		long classNameId = _classNameLocalService.getClassNameId(className);
		ratingsStats.setClassNameId(classNameId);

		ratingsStats.setClassPK(classPK);
		ratingsStats.setTotalEntries(totalEntries);
		ratingsStats.setTotalScore(totalScore);
		ratingsStats.setAverageScore(averageScore);

		return _ratingsStatsLocalService.updateRatingsStats(ratingsStats);
	}

	protected void addSocialActivity(
		long activityId, long groupId, long companyId, long userId,
		long createDate, long mirrorActivityId, long classNameId, long classPK,
		int type, String extraData, long receiverUserId) {

		SocialActivity socialActivity =
			_socialActivityLocalService.createSocialActivity(activityId);

		socialActivity.setGroupId(groupId);
		socialActivity.setCompanyId(companyId);
		socialActivity.setUserId(userId);
		socialActivity.setCreateDate(createDate);
		socialActivity.setMirrorActivityId(mirrorActivityId);
		socialActivity.setClassNameId(classNameId);
		socialActivity.setClassPK(classPK);
		socialActivity.setType(type);
		socialActivity.setExtraData(extraData);
		socialActivity.setReceiverUserId(receiverUserId);

		_socialActivityLocalService.updateSocialActivity(socialActivity);
	}

	protected void addSubscription(
		long subscriptionId, long companyId, long userId, String userName,
		Date createDate, Date modifiedDate, long classNameId, long classPK,
		String frequency) {

		Subscription subscription =
			_subscriptionLocalService.createSubscription(subscriptionId);

		subscription.setCompanyId(companyId);
		subscription.setUserId(userId);
		subscription.setUserName(userName);
		subscription.setCreateDate(createDate);
		subscription.setModifiedDate(modifiedDate);
		subscription.setClassNameId(classNameId);
		subscription.setClassPK(classPK);
		subscription.setFrequency(frequency);

		_subscriptionLocalService.updateSubscription(subscription);
	}

	protected String convertRecurrence(String originalRecurrence) {
		if (Validator.isNull(originalRecurrence)) {
			return null;
		}

		TZSRecurrence tzsRecurrence =
			(TZSRecurrence)JSONFactoryUtil.deserialize(originalRecurrence);

		if (tzsRecurrence == null) {
			return null;
		}

		Recurrence recurrence = new Recurrence();

		Frequency frequency = _frequencyMap.get(tzsRecurrence.getFrequency());

		int interval = tzsRecurrence.getInterval();

		List<PositionalWeekday> positionalWeekdays = new ArrayList<>();

		if ((frequency == Frequency.DAILY) && (interval == 0)) {
			frequency = Frequency.WEEKLY;

			interval = 1;

			positionalWeekdays.add(new PositionalWeekday(Weekday.MONDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.TUESDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.WEDNESDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.THURSDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.FRIDAY, 0));
		}
		else {
			DayAndPosition[] dayAndPositions = tzsRecurrence.getByDay();

			if (dayAndPositions != null) {
				for (DayAndPosition dayAndPosition : dayAndPositions) {
					Weekday weekday = _weekdayMap.get(
						dayAndPosition.getDayOfWeek());

					PositionalWeekday positionalWeekday = new PositionalWeekday(
						weekday, dayAndPosition.getDayPosition());

					positionalWeekdays.add(positionalWeekday);
				}
			}

			int[] months = tzsRecurrence.getByMonth();

			if (ArrayUtil.isNotEmpty(months)) {
				List<Integer> monthsList = new ArrayList<>();

				for (int month : months) {
					monthsList.add(month);
				}

				recurrence.setMonths(monthsList);
			}
		}

		recurrence.setInterval(interval);
		recurrence.setFrequency(frequency);
		recurrence.setPositionalWeekdays(positionalWeekdays);

		java.util.Calendar untilJCalendar = tzsRecurrence.getUntil();

		int ocurrence = tzsRecurrence.getOccurrence();

		if (untilJCalendar != null) {
			recurrence.setUntilJCalendar(untilJCalendar);
		}
		else if (ocurrence > 0) {
			recurrence.setCount(ocurrence);
		}

		return RecurrenceSerializer.serialize(recurrence);
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("CalEvent")) {
			importCalEvents();
		}
	}

	protected CalendarBooking fetchCalendarBooking(String uuid, long groupId)
		throws PortalException {

		return _calendarBookingLocalService
			.fetchCalendarBookingByUuidAndGroupId(uuid, groupId);
	}

	protected long getActionId(
		ResourceAction oldResourceAction, String newClassName) {

		ResourceAction newResourceAction =
			_resourceActionLocalService.fetchResourceAction(
				newClassName, oldResourceAction.getActionId());

		if (newResourceAction == null) {
			return 0;
		}

		return newResourceAction.getBitwiseValue();
	}

	protected long getActionIds(
		ResourcePermission resourcePermission, String oldClassName,
		String newClassName) {

		long actionIds = 0;

		List<ResourceAction> oldResourceActions =
			_resourceActionLocalService.getResourceActions(oldClassName);

		for (ResourceAction oldResourceAction : oldResourceActions) {
			boolean hasActionId = _resourcePermissionLocalService.hasActionId(
				resourcePermission, oldResourceAction);

			if (!hasActionId) {
				continue;
			}

			actionIds = actionIds | getActionId(
				oldResourceAction, newClassName);
		}

		return actionIds;
	}

	protected AssetCategory getAssetCategory(
			long userId, long companyId, long groupId, String name)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		User user = null;

		try {
			user = _userLocalService.getUserById(companyId, userId);
		}
		catch (NoSuchUserException nsue) {
			user = _userLocalService.getDefaultUser(companyId);

			userId = user.getUserId();
		}

		serviceContext.setUserId(userId);

		AssetVocabulary assetVocabulary = null;

		try {
			assetVocabulary = _assetVocabularyLocalService.getGroupVocabulary(
				groupId, _ASSET_VOCABULARY_NAME);
		}
		catch (NoSuchVocabularyException nsve) {
			assetVocabulary = _assetVocabularyLocalService.addVocabulary(
				userId, groupId, _ASSET_VOCABULARY_NAME, serviceContext);
		}

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getVocabularyRootCategories(
				assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (AssetCategory assetCategory : assetCategories) {
			if (name.equals(assetCategory.getName())) {
				return assetCategory;
			}
		}

		return _assetCategoryLocalService.addCategory(
			userId, groupId, name, assetVocabulary.getVocabularyId(),
			serviceContext);
	}

	protected CalendarResource getCalendarResource(long companyId, long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		long userId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(userId);

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isUser()) {
			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_userClassNameId, userId);

			if (calendarResource != null) {
				return calendarResource;
			}

			User user = _userLocalService.getUser(userId);

			Group userGroup = null;

			String userName = user.getFullName();

			if (user.isDefaultUser()) {
				userGroup = _groupLocalService.getGroup(
					serviceContext.getCompanyId(), GroupConstants.GUEST);

				userName = GroupConstants.GUEST;
			}
			else {
				userGroup = _groupLocalService.getUserGroup(
					serviceContext.getCompanyId(), userId);
			}

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), userName);

			Map<Locale, String> descriptionMap = new HashMap<>();

			return _calendarResourceLocalService.addCalendarResource(
				userId, userGroup.getGroupId(), _userClassNameId, userId, null,
				null, nameMap, descriptionMap, true, serviceContext);
		}
		else {
			if (group.isUser()) {
				return null;
			}

			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_groupClassNameId, groupId);

			if (calendarResource != null) {
				return calendarResource;
			}

			userId = group.getCreatorUserId();

			User user = _userLocalService.fetchUserById(userId);

			if ((user == null) || user.isDefaultUser()) {
				Role role = _roleLocalService.getRole(
					group.getCompanyId(), RoleConstants.ADMINISTRATOR);

				long[] userIds = _userLocalService.getRoleUserIds(
					role.getRoleId());

				userId = userIds[0];
			}

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), group.getDescriptiveName());

			Map<Locale, String> descriptionMap = new HashMap<>();

			return _calendarResourceLocalService.addCalendarResource(
				userId, groupId, _groupClassNameId, groupId, null, null,
				nameMap, descriptionMap, true, serviceContext);
		}
	}

	protected void importAssetLink(
			AssetLink assetLink, long oldEntryId, long newEntryId)
		throws Exception {

		long entryId1 = 0;
		long entryId2 = 0;

		AssetEntry linkedAssetEntry;

		if (assetLink.getEntryId1() == oldEntryId) {
			entryId1 = newEntryId;
			entryId2 = assetLink.getEntryId2();

			linkedAssetEntry = _assetEntryLocalService.fetchAssetEntry(
				entryId2);
		}
		else {
			entryId1 = assetLink.getEntryId1();
			entryId2 = newEntryId;

			linkedAssetEntry = _assetEntryLocalService.fetchAssetEntry(
				entryId2);
		}

		if (linkedAssetEntry.getClassNameId() ==
				_classNameLocalService.getClassNameId(_CAL_EVENT_CLASS_NAME)) {

			List<CalendarBooking> calendarBookings = importCalEvent(
				linkedAssetEntry.getClassPK());

			CalendarBooking calendarBooking = calendarBookings.get(0);

			CalendarResource calendarResource = getCalendarResource(
				calendarBooking.getCompanyId(), calendarBooking.getGroupId());

			linkedAssetEntry = _assetEntryLocalService.getEntry(
				calendarResource.getGroupId(), calendarBooking.getUuid());

			if (assetLink.getEntryId1() == oldEntryId) {
				entryId2 = linkedAssetEntry.getEntryId();
			}
			else {
				entryId1 = linkedAssetEntry.getEntryId();
			}

			if (isAssetLinkImported(assetLink, entryId1, entryId2)) {
				return;
			}
		}

		long linkId = _counterLocalService.increment();

		addAssetLink(
			linkId, assetLink.getCompanyId(), assetLink.getUserId(),
			assetLink.getUserName(), assetLink.getCreateDate(), entryId1,
			entryId2, assetLink.getType(), assetLink.getWeight());
	}

	protected void importAssets(
			String uuid, long companyId, long groupId, long userId, String type,
			long eventId, long calendarBookingId)
		throws Exception {

		// Asset entry

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_CAL_EVENT_CLASS_NAME, eventId);

		if (assetEntry == null) {
			return;
		}

		long entryId = _counterLocalService.increment();

		addAssetEntry(
			entryId, assetEntry.getGroupId(), assetEntry.getCompanyId(),
			assetEntry.getUserId(), assetEntry.getUserName(),
			assetEntry.getCreateDate(), assetEntry.getModifiedDate(),
			_classNameLocalService.getClassNameId(
				CalendarBooking.class.getName()),
			calendarBookingId, uuid, assetEntry.isVisible(),
			assetEntry.getStartDate(), assetEntry.getEndDate(),
			assetEntry.getPublishDate(), assetEntry.getExpirationDate(),
			assetEntry.getMimeType(), assetEntry.getTitle(),
			assetEntry.getDescription(), assetEntry.getSummary(),
			assetEntry.getUrl(), assetEntry.getLayoutUuid(),
			assetEntry.getHeight(), assetEntry.getWidth(),
			assetEntry.getPriority(), assetEntry.getViewCount());

		// Asset categories

		List<AssetCategory> assetCategories = new ArrayList<>();

		assetCategories.addAll(assetEntry.getCategories());

		if (Validator.isNotNull(type)) {
			assetCategories.add(
				getAssetCategory(userId, companyId, groupId, type));
		}

		for (AssetCategory assetCategory : assetCategories) {
			_assetEntryLocalService.addAssetCategoryAssetEntry(
				assetCategory.getCategoryId(), entryId);
		}

		// Asset links

		List<AssetLink> assetLinks = _assetLinkLocalService.getLinks(
			assetEntry.getEntryId());

		for (AssetLink assetLink : assetLinks) {
			importAssetLink(assetLink, assetEntry.getEntryId(), entryId);
		}

		// Asset tags

		List<AssetTag> assetTags = assetEntry.getTags();

		for (AssetTag assetTag : assetTags) {
			_assetEntryLocalService.addAssetTagAssetEntry(
				assetTag.getTagId(), entryId);
		}
	}

	protected void importCalendarBookingResourcePermission(
		ResourcePermission resourcePermission,
		long calendarBookingId) throws PortalException {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(calendarBookingId);

		long actionIds = getActionIds(
			resourcePermission, _CAL_EVENT_CLASS_NAME,
			CalendarBooking.class.getName());

		_resourceBlockLocalService.updateIndividualScopePermissions(
			calendarBooking.getCompanyId(), calendarBooking.getGroupId(),
			CalendarBooking.class.getName(), calendarBooking,
			resourcePermission.getRoleId(), actionIds,
			ResourceBlockConstants.OPERATOR_SET);
	}

	protected void importCalendarBookingResourcePermissions(
			long companyId, long eventId, long calendarBookingId)
		throws PortalException {

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				companyId, _CAL_EVENT_CLASS_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(eventId));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			importCalendarBookingResourcePermission(
				resourcePermission, calendarBookingId);
		}
	}

	protected List<CalendarBooking> importCalEvent(long importingEventId)
		throws Exception {

		List<CalendarBooking> calendarBookings = new ArrayList<>();

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(5);

			sb.append("select uuid_, eventId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, title, ");
			sb.append("description, location, startDate, endDate, ");
			sb.append("durationHour, durationMinute, allDay, type_, ");
			sb.append("repeating, recurrence, firstReminder, secondReminder ");
			sb.append("from CalEvent ");

			if (importingEventId > 0) {
				sb.append("where eventId = ?");
			}

			try (PreparedStatement ps =
					connection.prepareStatement(sb.toString())) {

				if (importingEventId > 0) {
					ps.setLong(1, importingEventId);
				}

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String uuid = rs.getString("uuid_");
					long eventId = rs.getLong("eventId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String location = rs.getString("location");
					Timestamp startDate = rs.getTimestamp("startDate");
					int durationHour = rs.getInt("durationHour");
					int durationMinute = rs.getInt("durationMinute");
					boolean allDay = rs.getBoolean("allDay");
					String type = rs.getString("type_");

					// boolean repeatiing = rs.getBoolean("repeating");

					String recurrence = rs.getString("recurrence");
					int firstReminder = rs.getInt("firstReminder");
					int secondReminder = rs.getInt("secondReminder");

					CalendarBooking calendarBooking = importCalEvent(
						uuid, eventId, groupId, companyId, userId, userName,
						createDate, modifiedDate, title, description, location,
						startDate, durationHour, durationMinute, allDay, type,
						recurrence, firstReminder, secondReminder);

					calendarBookings.add(calendarBooking);
				}
			}
		}

		return calendarBookings;
	}

	protected CalendarBooking importCalEvent(
			String uuid, long eventId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, String title, String description,
			String location, Timestamp startDate, int durationHour,
			int durationMinute, boolean allDay, String type, String recurrence,
			int firstReminder, int secondReminder)
		throws Exception {

		CalendarResource calendarResource = getCalendarResource(
			companyId, groupId);

		CalendarBooking calendarBooking = fetchCalendarBooking(
			uuid, calendarResource.getGroupId());

		if (calendarBooking != null) {
			return calendarBooking;
		}

		long calendarBookingId = _counterLocalService.increment();

		long startTime = startDate.getTime();
		long endTime =
			startTime + durationHour * Time.HOUR + durationMinute * Time.MINUTE;

		if (allDay) {
			endTime = endTime - 1;
		}

		calendarBooking = addCalendarBooking(
			uuid, calendarBookingId, companyId, groupId, userId, userName,
			createDate, modifiedDate, calendarResource.getDefaultCalendarId(),
			calendarResource.getCalendarResourceId(), title, description,
			location, startTime, endTime, allDay, convertRecurrence(recurrence),
			firstReminder, NotificationType.EMAIL, secondReminder,
			NotificationType.EMAIL);

		// Resources

		importCalendarBookingResourcePermissions(
			companyId, eventId, calendarBookingId);

		// Subscriptions

		importSubscriptions(companyId, eventId, calendarBookingId);

		// Asset

		importAssets(
			uuid, companyId, groupId, userId, type, eventId, calendarBookingId);

		// Message boards

		importMBDiscussion(eventId, calendarBookingId);

		// Social

		importSocialActivities(eventId, calendarBookingId);

		// Ratings

		importRatings(
			_CAL_EVENT_CLASS_NAME, eventId, CalendarBooking.class.getName(),
			calendarBookingId);

		return calendarBooking;
	}

	protected void importCalEvents() throws Exception {
		importCalEvent(0);
	}

	protected void importMBDiscussion(long eventId, long calendarBookingId)
		throws PortalException {

		MBDiscussion mbDiscussion = _mbDiscussionLocalService.fetchDiscussion(
			_CAL_EVENT_CLASS_NAME, eventId);

		if (mbDiscussion == null) {
			return;
		}

		long threadId = importMBThread(
			mbDiscussion.getThreadId(), calendarBookingId);

		addMBDiscussion(
			PortalUUIDUtil.generate(), _counterLocalService.increment(),
			mbDiscussion.getGroupId(), mbDiscussion.getCompanyId(),
			mbDiscussion.getUserId(), mbDiscussion.getUserName(),
			mbDiscussion.getCreateDate(), mbDiscussion.getModifiedDate(),
			_classNameLocalService.getClassNameId(
				CalendarBooking.class.getName()),
			calendarBookingId, threadId);
	}

	protected long importMBMessage(
			long messageId, long threadId, long calendarBookingId,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		MBMessage mbMessage = _mbMessageLocalService.getMBMessage(messageId);

		return importMBMessage(
			mbMessage, threadId, calendarBookingId, mbMessageIds);
	}

	protected long importMBMessage(
			MBMessage mbMessage, long threadId, long calendarBookingId,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		Long messageId = mbMessageIds.get(mbMessage.getMessageId());

		if (messageId != null) {
			return messageId;
		}

		messageId = _counterLocalService.increment();

		addMBMessage(
			PortalUUIDUtil.generate(), messageId, mbMessage.getGroupId(),
			mbMessage.getCompanyId(), mbMessage.getUserId(),
			mbMessage.getUserName(), mbMessage.getCreateDate(),
			mbMessage.getModifiedDate(),
			_classNameLocalService.getClassNameId(
				CalendarBooking.class.getName()),
			calendarBookingId, mbMessage.getCategoryId(), threadId,
			mbMessage.getRootMessageId(), mbMessage.getParentMessageId(),
			mbMessage.getSubject(), mbMessage.getBody(), mbMessage.getFormat(),
			mbMessage.isAnonymous(), mbMessage.getPriority(),
			mbMessage.getAllowPingbacks(), mbMessage.isAnswer(),
			mbMessage.getStatus(), mbMessage.getStatusByUserId(),
			mbMessage.getStatusByUserName(), mbMessage.getStatusDate(),
			mbMessageIds);

		importRatings(
			MBDiscussion.class.getName(), mbMessage.getMessageId(),
			MBDiscussion.class.getName(), messageId);

		mbMessageIds.put(mbMessage.getMessageId(), messageId);

		return messageId;
	}

	protected long importMBThread(long threadId, long calendarBookingId)
		throws PortalException {

		MBThread mbThread = _mbThreadLocalService.fetchMBThread(threadId);

		return importMBThread(mbThread, calendarBookingId);
	}

	protected long importMBThread(MBThread mbThread, long calendarBookingId)
		throws PortalException {

		long threadId = _counterLocalService.increment();

		addMBThread(
			PortalUUIDUtil.generate(), threadId, mbThread.getGroupId(),
			mbThread.getCompanyId(), mbThread.getUserId(),
			mbThread.getUserName(), mbThread.getCreateDate(),
			mbThread.getModifiedDate(), mbThread.getCategoryId(), 0,
			mbThread.getRootMessageUserId(), mbThread.getMessageCount(),
			mbThread.getViewCount(), mbThread.getLastPostByUserId(),
			mbThread.getLastPostDate(), mbThread.getPriority(),
			mbThread.isQuestion(), mbThread.getStatus(),
			mbThread.getStatusByUserId(), mbThread.getStatusByUserName(),
			mbThread.getStatusDate());

		Map<Long, Long> mbMessageIds = new HashMap<>();

		List<MBMessage> mbMessages = _mbMessageLocalService.getThreadMessages(
			mbThread.getThreadId(), WorkflowConstants.STATUS_ANY);

		for (MBMessage mbMessage : mbMessages) {
			importMBMessage(
				mbMessage, threadId, calendarBookingId, mbMessageIds);
		}

		updateMBThreadRootMessageId(
			threadId, mbMessageIds.get(mbThread.getRootMessageId()));

		return threadId;
	}

	protected void importRatings(
		String oldClassName, long oldClassPK, String className, long classPK) {

		List<RatingsEntry> ratingsEntries =
			_ratingsEntryLocalService.getEntries(oldClassName, oldClassPK);

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			addRatingsEntry(
				_counterLocalService.increment(), ratingsEntry.getCompanyId(),
				ratingsEntry.getUserId(), ratingsEntry.getUserName(),
				ratingsEntry.getCreateDate(), ratingsEntry.getModifiedDate(),
				className, classPK, ratingsEntry.getScore());
		}

		List<Long> oldClassPKs = new ArrayList<>();
		oldClassPKs.add(oldClassPK);

		List<RatingsStats> ratingsStatsList =
			_ratingsStatsLocalService.getStats(oldClassName, oldClassPKs);

		if (ratingsStatsList.isEmpty()) {
			return;
		}

		RatingsStats ratingsStats = ratingsStatsList.get(0);

		addRatingsStats(
			_counterLocalService.increment(), className, classPK,
			ratingsStats.getTotalEntries(), ratingsStats.getTotalScore(),
			ratingsStats.getAverageScore());
	}

	protected void importSocialActivities(
		long eventId, long calendarBookingId) {

		List<SocialActivity> socialActivities =
			_socialActivityLocalService.getActivities(
				_CAL_EVENT_CLASS_NAME, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity socialActivity : socialActivities) {
			if (socialActivity.getClassPK() == eventId) {
				importSocialActivity(socialActivity, calendarBookingId);
			}
		}
	}

	protected void importSocialActivity(
		SocialActivity socialActivity, long calendarBookingId) {

		addSocialActivity(
			_counterLocalService.increment(SocialActivity.class.getName()),
			socialActivity.getGroupId(), socialActivity.getCompanyId(),
			socialActivity.getUserId(), socialActivity.getCreateDate(),
			socialActivity.getMirrorActivityId(),
			_classNameLocalService.getClassNameId(CalendarBooking.class),
			calendarBookingId, socialActivity.getType(),
			socialActivity.getExtraData(), socialActivity.getReceiverUserId());
	}

	protected void importSubscription(
		Subscription subscription, long calendarBookingId) {

		addSubscription(
			_counterLocalService.increment(), subscription.getCompanyId(),
			subscription.getUserId(), subscription.getUserName(),
			subscription.getCreateDate(), subscription.getModifiedDate(),
			_classNameLocalService.getClassNameId(CalendarBooking.class),
			calendarBookingId, subscription.getFrequency());
	}

	protected void importSubscriptions(
		long companyId, long eventId, long calendarBookingId) {

		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				companyId, _CAL_EVENT_CLASS_NAME, eventId);

		for (Subscription subscription : subscriptions) {
			importSubscription(subscription, calendarBookingId);
		}
	}

	protected boolean isAssetLinkImported(
		AssetLink assetLink, long entryId1, long entryId2) {

		List<AssetLink> links = _assetLinkLocalService.getLinks(entryId1);

		for (AssetLink link : links) {
			if ((link.getEntryId1() != entryId2) &&
				(link.getEntryId2() != entryId2)) {

				continue;
			}

			if (link.getType() == assetLink.getType()) {
				return true;
			}
		}

		return false;
	}

	protected void updateMBThreadRootMessageId(
			long threadId, long rootMessageId)
		throws PortalException {

		MBThread mbThread = _mbThreadLocalService.getMBThread(threadId);

		mbThread.setRootMessageId(rootMessageId);

		_mbThreadLocalService.updateMBThread(mbThread);
	}

	private static final String _ASSET_VOCABULARY_NAME = "Calendar Event Types";

	private static final String _CAL_EVENT_CLASS_NAME =
		"com.liferay.portlet.calendar.model.CalEvent";

	private static final Map<Integer, Frequency> _frequencyMap =
		new HashMap<>();
	private static final Map<Integer, Weekday> _weekdayMap = new HashMap<>();

	static {
		_frequencyMap.put(TZSRecurrence.DAILY, Frequency.DAILY);
		_frequencyMap.put(TZSRecurrence.WEEKLY, Frequency.WEEKLY);
		_frequencyMap.put(TZSRecurrence.MONTHLY, Frequency.MONTHLY);
		_frequencyMap.put(TZSRecurrence.YEARLY, Frequency.YEARLY);

		_weekdayMap.put(java.util.Calendar.SUNDAY, Weekday.SUNDAY);
		_weekdayMap.put(java.util.Calendar.MONDAY, Weekday.MONDAY);
		_weekdayMap.put(java.util.Calendar.TUESDAY, Weekday.TUESDAY);
		_weekdayMap.put(java.util.Calendar.WEDNESDAY, Weekday.WEDNESDAY);
		_weekdayMap.put(java.util.Calendar.THURSDAY, Weekday.THURSDAY);
		_weekdayMap.put(java.util.Calendar.FRIDAY, Weekday.FRIDAY);
		_weekdayMap.put(java.util.Calendar.SATURDAY, Weekday.SATURDAY);
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetLinkLocalService _assetLinkLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CalendarBookingLocalService _calendarBookingLocalService;
	private final CalendarResourceLocalService _calendarResourceLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final CounterLocalService _counterLocalService;
	private final long _groupClassNameId;
	private final GroupLocalService _groupLocalService;
	private final MBDiscussionLocalService _mbDiscussionLocalService;
	private final MBMessageLocalService _mbMessageLocalService;
	private final MBThreadLocalService _mbThreadLocalService;
	private final RatingsEntryLocalService _ratingsEntryLocalService;
	private final RatingsStatsLocalService _ratingsStatsLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceBlockLocalService _resourceBlockLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final SocialActivityLocalService _socialActivityLocalService;
	private final SubscriptionLocalService _subscriptionLocalService;
	private final long _userClassNameId;
	private final UserLocalService _userLocalService;

}