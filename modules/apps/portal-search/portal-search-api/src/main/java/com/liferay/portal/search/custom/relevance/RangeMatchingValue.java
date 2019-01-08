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
public class RangeMatchingValue implements MatchingValue {

	public RangeMatchingValue(float min, float max) {
		_min = min;
		_max = max;
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

		RangeMatchingValue other = (RangeMatchingValue)obj;

		if (closeEnough(_min, other._min) && closeEnough(_max, other._max)) {
			return true;
		}

		return false;
	}

	public float getMax() {
		return _max;
	}

	public float getMin() {
		return _min;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_min, _max);
	}

	protected boolean closeEnough(float float1, float float2) {
		if (Math.abs(float1-float2) < 0.001) {
			return true;
		}

		return false;
	}

	private final float _max;
	private final float _min;

}