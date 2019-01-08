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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.custom.relevance.CustomRelevance;
import com.liferay.portal.search.custom.relevance.MatchingValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Adam Brandizzi
 */
public class CustomRelevanceFactory {

	public CustomRelevance getCustomRelevance(String configuration) {
		String[] parts = configuration.split(":");

		String field = parts[0];
		Float increment = Float.valueOf(parts[2]);

		List<MatchingValue> values = Stream.of(
			parts[1].split(",")
		).map(
			_matchingValueParser::parse
		).collect(
			Collectors.toList()
		);

		return new CustomRelevance(values, increment, field);
	}

	public List<CustomRelevance> getCustomRelevances(String[] configurations) {
		List<CustomRelevance> customRelevances = new ArrayList<>();

		if (ArrayUtil.isEmpty(configurations)) {
			return customRelevances;
		}

		for (String configuration : configurations) {
			try {
				CustomRelevance customRelevance = getCustomRelevance(
					configuration);

				customRelevances.add(customRelevance);
			}
			catch (Exception e) {
				_log.error("Invalid relevance: " + configuration, e);

				continue;
			}
		}

		return customRelevances;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomRelevanceFactory.class);

	private final MatchingValueParser _matchingValueParser =
		new MatchingValueParser();

}