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

package com.liferay.dynamic.data.mapping.web.lar;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.adapter.StagedDDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.adapter.impl.StagedDDMStructureLayoutImpl;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING},
	service = StagedModelDataHandler.class
)
public class StagedDDMStructureLayoutStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedDDMStructureLayout> {

	public static final String[] CLASS_NAMES = {DDMStructure.class.getName()};

	@Override
	public void deleteStagedModel(
			StagedDDMStructureLayout stagedStructureLayout)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public StagedDDMStructureLayout fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		DDMStructureLayout structureLayout =
			DDMStructureLayoutLocalServiceUtil.
				fetchDDMStructureLayoutByUuidAndGroupId(uuid, groupId);

		return new StagedDDMStructureLayoutImpl(structureLayout);
	}

	@Override
	public List<StagedDDMStructureLayout> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DDMStructureLayout> structureLayouts =
			DDMStructureLayoutLocalServiceUtil.
				getDDMStructureLayoutsByUuidAndCompanyId(uuid, companyId);

		List<StagedDDMStructureLayout> stagedStructureLayouts =
			new ArrayList<>();

		for (DDMStructureLayout structureLayout : structureLayouts) {
			stagedStructureLayouts.add(
				new StagedDDMStructureLayoutImpl(structureLayout));
		}

		return stagedStructureLayouts;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		StagedDDMStructureLayout stagedStructureLayout) {

		return stagedStructureLayout.getDefinition();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedDDMStructureLayout stagedStructureLayout)
		throws Exception {
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedDDMStructureLayout stagedStructureLayout)
		throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedDDMStructureLayoutStagedModelDataHandler.class);

}