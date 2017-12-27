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

package com.liferay.portal.search.admin.web.internal.portlet;

import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.OmniadminControlPanelEntry;
import com.liferay.portal.search.admin.web.constants.SearchAdminPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + SearchAdminPortletKeys.SEARCH_ADMIN},
	service = ControlPanelEntry.class
)
public class SearchAdminControlPanelEntry extends OmniadminControlPanelEntry {
}