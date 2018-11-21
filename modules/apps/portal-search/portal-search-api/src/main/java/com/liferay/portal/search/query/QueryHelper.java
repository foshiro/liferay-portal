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

package com.liferay.portal.search.query;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Set;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface QueryHelper {

	public void addBoosterTerm(
		BooleanQuery booleanQuery, String _field, Set<String> boosterValues,
		float _boostIncrement);

	public void addSearchLocalizedTerm(
		BooleanQuery searchQuery, SearchContext searchContext, String field,
		boolean like);

	public Query addSearchTerm(
		BooleanQuery searchQuery, SearchContext searchContext, String field,
		boolean like);

}