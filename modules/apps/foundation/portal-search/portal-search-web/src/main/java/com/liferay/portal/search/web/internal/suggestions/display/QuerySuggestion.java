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

package com.liferay.portal.search.web.internal.suggestions.display;

/**
 * @author Adam Brandizzi
 */
public class QuerySuggestion {

	public String getFormattedQuery() {
		return _formattedQuery;
	}

	public String getSearchURL() {
		return _searchURL;
	}

	public void setFormattedQuery(String formattedQuery) {
		_formattedQuery = formattedQuery;
	}

	public void setSearchURL(String searchURL) {
		_searchURL = searchURL;
	}

	private String _formattedQuery;
	private String _searchURL;

}