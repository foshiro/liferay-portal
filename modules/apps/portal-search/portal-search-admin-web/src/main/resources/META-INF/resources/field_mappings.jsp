<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
List<String> indexNames = new ArrayList<>();
indexNames.add("My Index");
indexNames.add("Other index");

Map<String, Object> context = new HashMap<>();
context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
context.put("indexNames", indexNames);
context.put("selectedIndexName", "My Index");
context.put("fieldMappingsJson",
	"{ \"LiferayDocumentType\": {\"date_detection\": false} }");
%>

<soy:component-renderer
	context="<%= context %>"
	module="js/FieldMappings.es"
	templateNamespace="com.liferay.portal.search.admin.web.FieldMappings.render"
/>