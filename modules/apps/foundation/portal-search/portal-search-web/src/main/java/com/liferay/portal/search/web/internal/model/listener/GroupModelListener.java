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

package com.liferay.portal.search.web.internal.model.listener;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.search.web.internal.configuration.SearchPageConfiguration;
import com.liferay.portal.search.web.internal.page.creator.SearchPageCreator;
import com.liferay.portal.search.web.layout.prototype.SearchLayoutPrototypeCustomizer;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.web.internal.configuration.SearchPageConfiguration",
	immediate = true, service = ModelListener.class
)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		_searchPageCreator.createPage(group);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SearchPageConfiguration searchPageConfiguration =
			ConfigurableUtil.createConfigurable(
				SearchPageConfiguration.class, properties);

		_searchPageCreator = new SearchPageCreator(
			layoutLocalService, layoutPrototypeLocalService,
			searchLayoutPrototypeCustomizer, searchPageConfiguration);
	}

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPrototypeLocalService layoutPrototypeLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile SearchLayoutPrototypeCustomizer
		searchLayoutPrototypeCustomizer;

	private SearchPageCreator _searchPageCreator;

}