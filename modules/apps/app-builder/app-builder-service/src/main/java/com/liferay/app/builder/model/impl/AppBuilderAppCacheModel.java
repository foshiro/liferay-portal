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

package com.liferay.app.builder.model.impl;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing AppBuilderApp in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AppBuilderAppCacheModel
	implements CacheModel<AppBuilderApp>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AppBuilderAppCacheModel)) {
			return false;
		}

		AppBuilderAppCacheModel appBuilderAppCacheModel =
			(AppBuilderAppCacheModel)obj;

		if (appBuilderAppId == appBuilderAppCacheModel.appBuilderAppId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, appBuilderAppId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", appBuilderAppId=");
		sb.append(appBuilderAppId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);
		sb.append(", deDataLayoutId=");
		sb.append(deDataLayoutId);
		sb.append(", deDataListViewId=");
		sb.append(deDataListViewId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", settings=");
		sb.append(settings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AppBuilderApp toEntityModel() {
		AppBuilderAppImpl appBuilderAppImpl = new AppBuilderAppImpl();

		if (uuid == null) {
			appBuilderAppImpl.setUuid("");
		}
		else {
			appBuilderAppImpl.setUuid(uuid);
		}

		appBuilderAppImpl.setAppBuilderAppId(appBuilderAppId);
		appBuilderAppImpl.setGroupId(groupId);
		appBuilderAppImpl.setCompanyId(companyId);
		appBuilderAppImpl.setUserId(userId);

		if (userName == null) {
			appBuilderAppImpl.setUserName("");
		}
		else {
			appBuilderAppImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			appBuilderAppImpl.setCreateDate(null);
		}
		else {
			appBuilderAppImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			appBuilderAppImpl.setModifiedDate(null);
		}
		else {
			appBuilderAppImpl.setModifiedDate(new Date(modifiedDate));
		}

		appBuilderAppImpl.setDdmStructureId(ddmStructureId);
		appBuilderAppImpl.setDeDataLayoutId(deDataLayoutId);
		appBuilderAppImpl.setDeDataListViewId(deDataListViewId);

		if (name == null) {
			appBuilderAppImpl.setName("");
		}
		else {
			appBuilderAppImpl.setName(name);
		}

		if (settings == null) {
			appBuilderAppImpl.setSettings("");
		}
		else {
			appBuilderAppImpl.setSettings(settings);
		}

		appBuilderAppImpl.resetOriginalValues();

		return appBuilderAppImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		appBuilderAppId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		ddmStructureId = objectInput.readLong();

		deDataLayoutId = objectInput.readLong();

		deDataListViewId = objectInput.readLong();
		name = objectInput.readUTF();
		settings = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(appBuilderAppId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(ddmStructureId);

		objectOutput.writeLong(deDataLayoutId);

		objectOutput.writeLong(deDataListViewId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}
	}

	public String uuid;
	public long appBuilderAppId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long ddmStructureId;
	public long deDataLayoutId;
	public long deDataListViewId;
	public String name;
	public String settings;

}