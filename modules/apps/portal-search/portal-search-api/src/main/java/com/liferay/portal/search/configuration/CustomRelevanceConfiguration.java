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

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.ProviderType;
import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adam Brandizzi
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.CustomRelevanceConfiguration",
	localization = "content/Language",
	name = "search-custom-relevance-configuration-name"
)
@ProviderType
public interface CustomRelevanceConfiguration {

	@Meta.AD(name = "field", required = false)
	public String field();

	@Meta.AD(name = "booster-values", required = false)
	public String[] boosterValues();

	@Meta.AD(name = "boost-increment", required = false)
	public float boostIncrement();

}