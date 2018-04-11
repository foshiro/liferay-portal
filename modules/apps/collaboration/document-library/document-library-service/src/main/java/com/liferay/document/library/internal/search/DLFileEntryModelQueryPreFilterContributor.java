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

package com.liferay.document.library.internal.search;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.search.query.ModelQueryPreFilterContributorHelper;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = QueryPreFilterContributor.class
)
public class DLFileEntryModelQueryPreFilterContributor
	implements QueryPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		addHelperFilters(fullQueryBooleanFilter, searchContext);
	}

	protected void addHelperFilters(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		modelQueryPreFilterContributorHelper.addClassTypeIdsFilter(
			fullQueryBooleanFilter, searchContext);
		modelQueryPreFilterContributorHelper.addStagingFilter(
			fullQueryBooleanFilter, searchContext);
		modelQueryPreFilterContributorHelper.addWorkflowStatusesFilter(
			fullQueryBooleanFilter, searchContext);
	}

	@Reference
	protected ModelQueryPreFilterContributorHelper
		modelQueryPreFilterContributorHelper;

}