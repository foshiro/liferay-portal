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

package com.liferay.portal.search.custom.relevance;

import java.util.Objects;

/**
 * @author Adam Brandizzi
 */
public class StringMatchingValue implements MatchingValue {

	public StringMatchingValue(String value) {
		_value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		StringMatchingValue other = (StringMatchingValue)obj;

		if (Objects.equals(_value, other._value)) {
			return true;
		}

		return false;
	}

	public String getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_value);
	}

	private final String _value;

}