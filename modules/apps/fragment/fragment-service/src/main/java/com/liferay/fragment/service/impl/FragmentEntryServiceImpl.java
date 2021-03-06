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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.base.FragmentEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"json.web.service.context.name=fragment",
		"json.web.service.context.path=FragmentEntry"
	},
	service = AopService.class
)
public class FragmentEntryServiceImpl extends FragmentEntryServiceBaseImpl {

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, int type,
			int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, null, name, 0, type,
			status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, null, name, 0,
			FragmentConstants.TYPE_SECTION, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, int type, int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			0, type, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			0, FragmentConstants.TYPE_SECTION, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, String css,
			String html, String js, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, name, css, html, js,
			type, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String name, String css,
			String html, String js, int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, name, css, html, js,
			status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, int type,
			int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			css, html, js, type, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			css, html, js, status, serviceContext);
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js,
			String configuration, long previewFileEntryId, int type, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			css, html, js, configuration, previewFileEntryId, type, status,
			serviceContext);
	}

	@Override
	public FragmentEntry copyFragmentEntry(
			long groupId, long fragmentEntryId, long fragmentCollectionId,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.copyFragmentEntry(
			getUserId(), groupId, fragmentEntryId, fragmentCollectionId,
			serviceContext);
	}

	@Override
	public void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws PortalException {

		for (long fragmentEntryId : fragmentEntriesIds) {
			FragmentEntry fragmentEntry =
				fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

			_portletResourcePermission.check(
				getPermissionChecker(), fragmentEntry.getGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

			fragmentEntryLocalService.deleteFragmentEntry(fragmentEntry);
		}
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), fragmentEntry.getGroupId(),
				ActionKeys.VIEW);
		}

		return fragmentEntry;
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId) {

		return fragmentEntryPersistence.countByG_FCI(
			groupId, fragmentCollectionId);
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, int status) {

		return fragmentEntryPersistence.countByG_FCI_S(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, String name) {

		return fragmentEntryPersistence.countByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public int getFragmentCollectionsCount(
		long groupId, long fragmentCollectionId, String name, int status) {

		return fragmentEntryPersistence.countByG_FCI_LikeN_S(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], status);
	}

	@Override
	public int getFragmentCollectionsCountByType(
		long groupId, long fragmentCollectionId, int type) {

		return fragmentEntryPersistence.countByG_FCI_T(
			groupId, fragmentCollectionId, type);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId) {
		return fragmentEntryLocalService.getFragmentEntries(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int status) {

		return fragmentEntryLocalService.getFragmentEntries(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end) {

		return fragmentEntryPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], status,
			start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByType(
		long groupId, long fragmentCollectionId, int type, int status) {

		return fragmentEntryPersistence.findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByType(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, orderByComparator);
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.getTempFileNames(
			getUserId(), groupId, folderName);
	}

	@Override
	public FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.moveFragmentEntry(
			fragmentEntryId, fragmentCollectionId);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, previewFileEntryId);
	}

	@Override
	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #updateFragmentEntry(long, String, String, String,
	 *             String, String, int)}
	 */
	@Deprecated
	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, int status)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, name, css, html, js, status);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #updateFragmentEntry(long, String, String, String,
	 *             String, String, long, int)}
	 */
	@Deprecated
	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, long previewFileEntryId, int status)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, name, css, html, js,
			previewFileEntryId, status);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, String configuration, int status)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, name, css, html, js, configuration,
			status);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, String configuration, long previewFileEntryId,
			int status)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, name, css, html, js, configuration,
			previewFileEntryId, status);
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}