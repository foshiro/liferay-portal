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

import com.liferay.portal.search.custom.relevance.MatchingValue;
import com.liferay.portal.search.custom.relevance.RangeMatchingValue;
import com.liferay.portal.search.custom.relevance.StringMatchingValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adam Brandizzi
 */
public class MatchingValueParser {

	public MatchingValue parse(String value) {
		Matcher matcher = _rangePattern.matcher(value);

		if (matcher.matches()) {
			float min = Float.valueOf(matcher.group(1));
			float max = Float.valueOf(matcher.group(2));

			return new RangeMatchingValue(min, max);
		}

		return new StringMatchingValue(value);
	}

	private static final Pattern _rangePattern = Pattern.compile(
		"\\[([^]]+)\\.\\.([^]]+)\\]");

}