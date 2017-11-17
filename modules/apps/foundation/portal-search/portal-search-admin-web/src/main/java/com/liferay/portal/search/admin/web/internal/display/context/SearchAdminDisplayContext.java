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
import com.liferay.portal.search.admin.web.internal.configuration.accessor.ElasticsearchConfigurationAccessor;
import com.liferay.portal.search.elasticsearch.configuration.OperationMode;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContext {

	public SearchAdminDisplayContext(
		SearchEngineHelper searchEngineHelper,
		ElasticsearchConfigurationAccessor elasticsearchConfigurationAccessor) {

		_searchEngineHelper = searchEngineHelper;
		_elasticsearchConfigurationAccessor =
			elasticsearchConfigurationAccessor;
	}

	public String getSearchEngineDescription() {
		String vendor = getVendor();

		String description = vendor;

		if ("Elasticsearch".equals(vendor)) {
			OperationMode operationMode =
				_elasticsearchConfigurationAccessor.getOperationMode();

			if (operationMode == OperationMode.EMBEDDED) {
				description += " (Embedded)";
			}
		}

		return description;
	}

	public String getVendor() {
		String searchEngineId = _searchEngineHelper.getDefaultSearchEngineId();

		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			searchEngineId);

		return searchEngine.getVendor();
	}

	private final ElasticsearchConfigurationAccessor
		_elasticsearchConfigurationAccessor;
	private final SearchEngineHelper _searchEngineHelper;

}