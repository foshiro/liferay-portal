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

package com.liferay.contacts.search.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.search.test.util.SearchContextTestUtil;

import java.util.Locale;

/**
 * @author Lucas Marques de Paula
 */
public class ContactIndexerFixture {

	public ContactIndexerFixture(Indexer<?> indexer) {
		_indexer = indexer;
	}

	public void deleteDocument(Document document)
		throws PortalException, SearchException {

		IndexWriterHelperUtil.deleteDocument(
			_indexer.getSearchEngineId(), TestPropsValues.getCompanyId(),
			document.getUID(), true);
	}

	public void searchNoOne(String keywords, Locale locale) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			getUserId(), keywords, locale);

		Hits hits = search(searchContext);

		HitsAssert.assertNoHits(hits);
	}

	public Document searchOnlyOne(String keywords, Locale locale)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			getUserId(), keywords, locale);

		Hits hits = search(searchContext);

		return HitsAssert.assertOnlyOne(hits);
	}

	public void setUser(User user) {
		_user = user;
	}

	protected long getUserId() throws Exception {
		return _user.getUserId();
	}

	protected void reindex(long companyId) throws Exception {
		_indexer.reindex(new String[] {String.valueOf(companyId)});
	}

	protected Hits search(SearchContext searchContext) throws Exception {
		return _indexer.search(searchContext);
	}

	private final Indexer<?> _indexer;
	private User _user;

}