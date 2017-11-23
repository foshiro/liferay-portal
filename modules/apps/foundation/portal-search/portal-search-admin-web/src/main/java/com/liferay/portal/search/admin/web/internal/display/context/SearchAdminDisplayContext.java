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
import com.liferay.portal.search.web.information.SearchEngineInformation;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContext {

	public SearchAdminDisplayContext(
		SearchEngine searchEngine,
		SearchEngineInformation searchEngineInformation) {

		_searchEngine = searchEngine;
		_searchEngineInformation = searchEngineInformation;
	}

	public String getSearchEngineVersion() {
		return _searchEngineInformation.getVersion();
	}

	public String getVendor() {
		return _searchEngine.getVendor();
	}

	public boolean isSearchEngineEmbedded() {
		String operationMode = _searchEngineInformation.getOperationMode();

		return "EMBEDDED".equals(operationMode);
	}

	private final SearchEngine _searchEngine;
	private final SearchEngineInformation _searchEngineInformation;

}