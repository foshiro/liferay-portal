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

import com.liferay.portal.search.custom.relevance.RangeMatchingValue;
import com.liferay.portal.search.custom.relevance.StringMatchingValue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class MatchingValueParserTest {

	@Before
	public void setUp() {
		matchingValueParser = new MatchingValueParser();
	}

	@Test
	public void testParseWithRange() {
		RangeMatchingValue rangeMatchingValue =
			(RangeMatchingValue)matchingValueParser.parse("[2.71..3.14]");

		Assert.assertEquals(2.71, rangeMatchingValue.getMin(), 0.01);
		Assert.assertEquals(3.14, rangeMatchingValue.getMax(), 0.01);
	}

	@Test
	public void testParseWithString() {
		StringMatchingValue stringMatchingValue =
			(StringMatchingValue)matchingValueParser.parse("test");

		Assert.assertEquals("test", stringMatchingValue.getValue());
	}

	protected MatchingValueParser matchingValueParser;

}