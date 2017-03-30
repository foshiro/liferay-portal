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

package com.liferay.marketplace.internal.upgrade.v2_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adam Brandizzi
 */
public class UpgradeApp extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateMarketplaceApp();
	}

	protected void updateMarketplaceApp() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select moduleId, appId from Marketplace_Module where " +
					"contextName = 'drools-web'");
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				long appId = rs.getLong("appId");

				runSQL(
					"update Marketplace_App set title = 'Rules Engine' where " +
						"appId = " + appId);

				long moduleId = rs.getLong("moduleId");

				StringBundler sb = new StringBundler(7);

				sb.append("update Marketplace_Module set ");
				sb.append("bundleSymbolicName = ");
				sb.append("'com.liferay.portal.rules.engine.drools', ");
				sb.append("contextName = ");
				sb.append("'com.liferay.portal.rules.engine.drools' where ");
				sb.append("moduleId = ");
				sb.append(moduleId);

				runSQL(sb.toString());
			}
		}
	}

}