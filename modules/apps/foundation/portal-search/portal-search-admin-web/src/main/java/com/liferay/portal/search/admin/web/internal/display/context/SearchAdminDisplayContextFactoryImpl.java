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

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.search.admin.web.internal.configuration.accessor.ElasticsearchConfigurationAccessor;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = SearchAdminDisplayContextFactory.class)
public class SearchAdminDisplayContextFactoryImpl
	implements SearchAdminDisplayContextFactory {

	@Override
	public SearchAdminDisplayContext create(
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletPreferences portletPreferences)
		throws PortletException {

		return new SearchAdminDisplayContext(
			_searchEngineHelper, _elasticsearchConfigurationAccessor);
	}

	@Reference
	private ElasticsearchConfigurationAccessor
		_elasticsearchConfigurationAccessor;

	@Reference
	private SearchEngineHelper _searchEngineHelper;

}