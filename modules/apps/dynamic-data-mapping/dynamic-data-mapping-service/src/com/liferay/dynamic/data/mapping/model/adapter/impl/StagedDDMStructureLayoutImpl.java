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

package com.liferay.dynamic.data.mapping.model.adapter.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.adapter.StagedDDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutImpl;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;

/**
 * @author Adam Brandizzi
 */
public class StagedDDMStructureLayoutImpl extends DDMStructureLayoutImpl
		implements StagedDDMStructureLayout {

	public StagedDDMStructureLayoutImpl(DDMStructureLayout structureLayout) {
		this._structureLayout = structureLayout;
	}

	@Override
	public Date getLastPublishDate() {
		return _structureLayout.getCreateDate();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedDDMStructureLayout.class);
	}

	@Override
	public void setLastPublishDate(Date date) {
		throw new UnsupportedOperationException();
	}

	private final DDMStructureLayout _structureLayout;

}