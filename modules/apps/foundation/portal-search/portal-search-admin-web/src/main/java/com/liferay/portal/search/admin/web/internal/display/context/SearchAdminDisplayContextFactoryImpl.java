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

import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.search.web.information.SearchEngineInformation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

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

		String searchEngineId = _searchEngineHelper.getDefaultSearchEngineId();

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchEngineId);

		SearchEngineInformation searchEngineInformation =
			_searchEngineInformationMap.get(searchEngine.getVendor());

		return new SearchAdminDisplayContext(
			searchEngine, searchEngineInformation);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "unregisterSearchEngineInformation"
	)
	protected void registerSearchEngineInformation(
		SearchEngineInformation searchEngineInformation) {

		_searchEngineInformationMap.put(
			searchEngineInformation.getVendor(), searchEngineInformation);
	}

	protected void unregisterSearchEngineInformation(
		SearchEngineInformation searchEngineInformation) {

		_searchEngineInformationMap.remove(searchEngineInformation.getVendor());
	}

	@Reference
	private SearchEngineHelper _searchEngineHelper;

	private final Map<String, SearchEngineInformation>
		_searchEngineInformationMap = new ConcurrentHashMap<>();

}