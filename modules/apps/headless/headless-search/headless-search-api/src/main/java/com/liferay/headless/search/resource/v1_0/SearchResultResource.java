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

package com.liferay.headless.search.resource.v1_0;

import com.liferay.headless.search.dto.v1_0.SearchResult;
import com.liferay.portal.kernel.model.Company;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-search/v1.0
 *
 * @author Bryan Engler
 * @generated
 */
@Generated("")
public interface SearchResultResource {

	public SearchResult getSearchCompanyIdKeywordsHiddenFromSize(
			Long companyId, String keywords, String hidden, Long from,
			Long size)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}