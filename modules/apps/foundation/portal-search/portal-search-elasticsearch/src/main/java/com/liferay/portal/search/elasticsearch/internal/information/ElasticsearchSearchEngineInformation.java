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

package com.liferay.portal.search.elasticsearch.internal.information;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.connection.OperationMode;
import com.liferay.portal.search.web.information.SearchEngineInformation;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = SearchEngineInformation.class
)
public class ElasticsearchSearchEngineInformation
	implements SearchEngineInformation {

	@Override
	public String getOperationMode() {
		return _operationMode.name();
	}

	@Override
	public String getStatusString() {
		String operationMode = StringPool.BLANK;

		if (isEmbedded()) {
			operationMode = "(Embedded mode)";
		}

		return String.join(
			StringPool.SPACE, getVendor(), getVersion(), operationMode);
}

	@Override
	public String getVendor() {
		return _elasticsearchSearchEngine.getVendor();
	}

	@Override
	public String getVersion() {
		return "2.4";
	}

	@Override
	public boolean isEmbedded() {
		return "EMBEDDED".equals(getOperationMode());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		_operationMode = _elasticsearchConfiguration.operationMode();
	}

	private ElasticsearchConfiguration _elasticsearchConfiguration;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SearchEngine _elasticsearchSearchEngine;

	private OperationMode _operationMode;

}