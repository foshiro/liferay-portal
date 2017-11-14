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

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContext {

	public SearchAdminDisplayContext(SearchEngineHelper searchEngineHelper) {
		_searchEngineHelper = searchEngineHelper;
	}

	public String getVendor() {
		String searchEngineId = _searchEngineHelper.getDefaultSearchEngineId();

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchEngineId);

		return searchEngine.getVendor();
	}

	private final SearchEngineHelper _searchEngineHelper;

}