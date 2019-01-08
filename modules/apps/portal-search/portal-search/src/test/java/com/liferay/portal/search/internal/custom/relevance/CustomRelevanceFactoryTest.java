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

import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.search.custom.relevance.CustomRelevance;
import com.liferay.portal.search.custom.relevance.RangeMatchingValue;
import com.liferay.portal.search.custom.relevance.StringMatchingValue;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class CustomRelevanceFactoryTest {

	@Before
	public void setUp() {
		customRelevanceFactory = new CustomRelevanceFactory();
	}

	@Test
	public void testGetCustomRelevanceWithRange() {
		CustomRelevance customRelevance =
			customRelevanceFactory.getCustomRelevance(
				"exampleField:[1..2],[2.1..4.4]:3.14");

		Assert.assertEquals("exampleField", customRelevance.getField());
		AssertUtils.assertEquals(
			Arrays.asList(
				new RangeMatchingValue(1, 2),
				new RangeMatchingValue(2.1F, 4.4F)),
			customRelevance.getBoosterValues());
		Assert.assertEquals(customRelevance.getBoostIncrement(), 3.14, 0.01);
	}

	@Test
	public void testGetCustomRelevanceWithStrings() {
		CustomRelevance customRelevance =
			customRelevanceFactory.getCustomRelevance(
				"exampleField:test,example:3.14");

		Assert.assertEquals("exampleField", customRelevance.getField());
		AssertUtils.assertEquals(
			Arrays.asList(
				new StringMatchingValue("test"),
				new StringMatchingValue("example")),
			customRelevance.getBoosterValues());
		Assert.assertEquals(customRelevance.getBoostIncrement(), 3.14, 0.01);
	}

	protected CustomRelevanceFactory customRelevanceFactory;

}