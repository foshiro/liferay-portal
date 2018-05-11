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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDisplayBuilder {

	public SearchBarPortletDisplayBuilder(Http http) {
		_http = http;
	}

	public SearchBarPortletDisplayContext build() {
		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			new SearchBarPortletDisplayContext();

		searchBarPortletDisplayContext.setAvailableEverythingSearchScope(
			isAvailableEverythingSearchScope());
		searchBarPortletDisplayContext.setCurrentSiteSearchScopeParameterString(
			SearchScope.THIS_SITE.getParameterString());
		searchBarPortletDisplayContext.setEverythingSearchScopeParameterString(
			SearchScope.EVERYTHING.getParameterString());
		searchBarPortletDisplayContext.setKeywords(getKeywords());
		searchBarPortletDisplayContext.setKeywordsParameterName(
			_keywordsParameterName);

		if (_searchScopePreference ==
				SearchScopePreference.LET_THE_USER_CHOOSE) {

			searchBarPortletDisplayContext.setLetTheUserChooseTheSearchScope(
				true);
		}

		boolean destinationConfigured = false;

		if (Validator.isNotNull(_destination) && !_searchLayoutAvailable) {
			destinationConfigured = true;
		}

		searchBarPortletDisplayContext.setDestinationConfigured(
			destinationConfigured);

		searchBarPortletDisplayContext.setScopeParameterName(
			_scopeParameterName);
		searchBarPortletDisplayContext.setScopeParameterValue(
			getScopeParameterValue());

		setSelectedSearchScope(searchBarPortletDisplayContext);

		searchBarPortletDisplayContext.setSearchURL(getSearchURL());

		return searchBarPortletDisplayContext;
	}

	public void setDestination(String destination) {
		_destination = destination;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	public void setScopeParameterName(String scopeParameterName) {
		_scopeParameterName = scopeParameterName;
	}

	public void setScopeParameterValue(String scopeParameterValue) {
		_scopeParameterValue = scopeParameterValue;
	}

	public void setSearchLayoutAvailable(boolean searchLayoutAvailable) {
		_searchLayoutAvailable = searchLayoutAvailable;
	}

	public void setSearchLayoutURL(String searchLayoutURL) {
		_searchLayoutURL = searchLayoutURL;
	}

	public void setSearchScopePreference(
		SearchScopePreference searchScopePreference) {

		_searchScopePreference = searchScopePreference;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	protected String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		return StringPool.BLANK;
	}

	protected String getScopeParameterValue() {
		if (_scopeParameterValue != null) {
			return _scopeParameterValue;
		}

		return StringPool.BLANK;
	}

	protected SearchScope getSearchScope() {
		if (_scopeParameterValue != null) {
			return SearchScope.getSearchScope(_scopeParameterValue);
		}

		SearchScope searchScope = _searchScopePreference.getSearchScope();

		if (searchScope != null) {
			return searchScope;
		}

		return SearchScope.THIS_SITE;
	}

	protected String getSearchURL() {
		if (_searchLayoutAvailable) {
			return _searchLayoutURL;
		}

		return _http.getPath(_themeDisplay.getURLCurrent());
	}

	protected boolean isAvailableEverythingSearchScope() {
		Group group = _themeDisplay.getScopeGroup();

		if (group.isStagingGroup()) {
			return false;
		}

		return true;
	}

	protected void setSelectedSearchScope(
		SearchBarPortletDisplayContext searchBarPortletDisplayContext) {

		SearchScope searchScope = getSearchScope();

		if (searchScope == SearchScope.EVERYTHING) {
			searchBarPortletDisplayContext.setSelectedEverythingSearchScope(
				true);
		}

		if (searchScope == SearchScope.THIS_SITE) {
			searchBarPortletDisplayContext.setSelectedCurrentSiteSearchScope(
				true);
		}
	}

	private String _destination;
	private final Http _http;
	private String _keywords;
	private String _keywordsParameterName;
	private String _scopeParameterName;
	private String _scopeParameterValue;
	private boolean _searchLayoutAvailable;
	private String _searchLayoutURL;
	private SearchScopePreference _searchScopePreference;
	private ThemeDisplay _themeDisplay;

}