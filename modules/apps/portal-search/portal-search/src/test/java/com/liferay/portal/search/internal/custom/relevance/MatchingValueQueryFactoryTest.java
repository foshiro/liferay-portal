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

package com.liferay.portal.search.internal.custom.relevance;

import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.search.custom.relevance.StringMatchingValue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class MatchingValueQueryFactoryTest {

	@Before
	public void setUp() {
		matchingValueQueryFactory = new MatchingValueQueryFactory(
			"exampleField");
	}

	@Test
	public void testGetCustomRelevanceWithStrings() {
		TermQuery query = (TermQuery)matchingValueQueryFactory.getQuery(
			new StringMatchingValue("test"));

		QueryTerm queryTerm = query.getQueryTerm();

		Assert.assertEquals("exampleField", queryTerm.getField());
	}

	protected MatchingValueQueryFactory matchingValueQueryFactory;

}