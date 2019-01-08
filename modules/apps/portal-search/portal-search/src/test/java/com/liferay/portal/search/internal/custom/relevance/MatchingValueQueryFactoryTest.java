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
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.search.custom.relevance.RangeMatchingValue;
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
	public void testGetCustomRelevanceWithRange() {
		TermRangeQuery query =
			(TermRangeQuery)matchingValueQueryFactory.getQuery(
				new RangeMatchingValue(2.71F, 3.14F));

		Assert.assertEquals("exampleField", query.getField());
		Assert.assertEquals(2.71, Float.valueOf(query.getLowerTerm()), 0.01);
		Assert.assertEquals(3.14, Float.valueOf(query.getUpperTerm()), 0.01);
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