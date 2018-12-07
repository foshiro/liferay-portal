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

package com.liferay.document.library.search.test;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.test.util.search.FileEntryBlueprint;
import com.liferay.document.library.test.util.search.FileEntrySearchFixture;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Igor Fabiano Nazar
 * @author Lucas Marques de Paula
 */
public class DLFileEntryMetadataDDMStructureFixture {

	public DLFileEntryMetadataDDMStructureFixture(
		DLFixture dlFixture, DLAppLocalService dlAppLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeService) {

		_dlFixture = dlFixture;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeService;

		_fileEntrySearchFixture = new FileEntrySearchFixture(dlAppLocalService);

		_fileEntrySearchFixture.setUp();
	}

	public void tearDown() throws Exception {
		_fileEntrySearchFixture.tearDown();

		deleteAllDDMStructures();

		deleteAllDLFileEntryTypes();
	}

	protected DLFileEntryType addDLFileEntryType(
			Long groupId, DDMStructure ddmStructure)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		long[] ddmStructureIds = {ddmStructure.getStructureId()};

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.addFileEntryType(
				_dlFixture.getUserId(), groupId, RandomTestUtil.randomString(),
				StringPool.BLANK, ddmStructureIds, serviceContext);

		_dlFileEntryTypes.add(dlFileEntryType);

		return dlFileEntryType;
	}

	protected FileEntry addFileEntry(
			String resourceFileName, long fileEntryTypeId)
		throws IOException, PortalException {

		Class<?> clazz = getClass();

		try (InputStream resourceInputStream = clazz.getResourceAsStream(
				"dependencies/" + resourceFileName)) {

			Map<String, Serializable> fileAttributes = new HashMap<>();

			fileAttributes.put("fileEntryTypeId", fileEntryTypeId);

			FileEntry fileEntry = _fileEntrySearchFixture.addFileEntry(
				new FileEntryBlueprint() {
					{
						attributes = fileAttributes;
						fileName = resourceFileName;
						groupId = _dlFixture.getGroupId();
						inputStream = resourceInputStream;
						title = resourceFileName;
						userId = _dlFixture.getUserId();
					}
				});

			return fileEntry;
		}
	}

	protected DDMStructure createDDMStructureWithDLFileEntry(
			String fileName, Locale locale)
		throws Exception {

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			new Locale[] {locale}, locale);

		long groupId = _dlFixture.getGroupId();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, DLFileEntryMetadata.class.getName(), ddmForm, locale);

		_ddmStructures.add(ddmStructure);

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			groupId, ddmStructure);

		addFileEntry(fileName, dlFileEntryType.getFileEntryTypeId());

		return ddmStructure;
	}

	protected void deleteAllDDMStructures() throws PortalException {
		for (DDMStructure ddmStructure : _ddmStructures) {
			_ddmStructureLocalService.deleteDDMStructure(ddmStructure);
		}

		_ddmStructures.clear();
	}

	protected void deleteAllDLFileEntryTypes() throws PortalException {
		for (DLFileEntryType dlFileEntryType : _dlFileEntryTypes) {
			_dlFileEntryTypeLocalService.deleteDLFileEntryType(dlFileEntryType);
		}

		_dlFileEntryTypes.clear();
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final List<DDMStructure> _ddmStructures = new ArrayList<>();
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final List<DLFileEntryType> _dlFileEntryTypes = new ArrayList<>();
	private final DLFixture _dlFixture;
	private final FileEntrySearchFixture _fileEntrySearchFixture;

}