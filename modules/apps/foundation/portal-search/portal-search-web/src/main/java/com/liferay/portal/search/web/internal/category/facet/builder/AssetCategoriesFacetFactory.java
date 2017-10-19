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

package com.liferay.portal.search.web.internal.category.facet.builder;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;

/**
 * @author André de Oliveira
 * @deprecated As of 1.4.0, replaced by {@link
 *             com.liferay.portal.search.facet.category.AssetCategoriesFacetFactory}
 */
@Deprecated
public class AssetCategoriesFacetFactory {

	public MultiValueFacet newInstance(SearchContext searchContext) {
		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		multiValueFacet.setFieldName(Field.ASSET_CATEGORY_IDS);

		return multiValueFacet;
	}

}