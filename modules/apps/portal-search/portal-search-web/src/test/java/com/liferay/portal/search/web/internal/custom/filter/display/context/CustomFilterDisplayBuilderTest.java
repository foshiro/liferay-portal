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

package com.liferay.portal.search.web.internal.custom.filter.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.AssertUtils;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class CustomFilterDisplayBuilderTest {

	@Test
	public void testSetParameterValue() {
		CustomFilterDisplayBuilder customFilterDisplayBuilder =
			new CustomFilterDisplayBuilder();

		customFilterDisplayBuilder.setParameterValue("test");

		CustomFilterDisplayContext customFilterDisplayContext =
			customFilterDisplayBuilder.build();

		Assert.assertEquals(
			"test", customFilterDisplayContext.getParameterValue());
		AssertUtils.assertEquals(
			Collections.singletonList("test"),
			customFilterDisplayContext.getParameterValues());
	}

	@Test
	public void testSetParameterValueNotTrimmed() {
		CustomFilterDisplayBuilder customFilterDisplayBuilder =
			new CustomFilterDisplayBuilder();

		customFilterDisplayBuilder.setParameterValue(" test ");

		CustomFilterDisplayContext customFilterDisplayContext =
			customFilterDisplayBuilder.build();

		Assert.assertEquals(
			"test", customFilterDisplayContext.getParameterValue());
		AssertUtils.assertEquals(
			Collections.singletonList("test"),
			customFilterDisplayContext.getParameterValues());
	}

	@Test
	public void testSetParameterValueNull() {
		CustomFilterDisplayBuilder customFilterDisplayBuilder =
			new CustomFilterDisplayBuilder();

		customFilterDisplayBuilder.setParameterValue(null);

		CustomFilterDisplayContext customFilterDisplayContext =
			customFilterDisplayBuilder.build();

		Assert.assertEquals(
			StringPool.BLANK, customFilterDisplayContext.getParameterValue());
		AssertUtils.assertEquals(
			Collections.emptyList(),
			customFilterDisplayContext.getParameterValues());
	}

}