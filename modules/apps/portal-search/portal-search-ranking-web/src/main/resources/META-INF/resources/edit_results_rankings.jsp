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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");

String resultsRankingsRootElementId = renderResponse.getNamespace() + "-results-rankings-root";
%>

<div id="<%= resultsRankingsRootElementId %>"></div>

<aui:script require='<%= npmResolvedPackageName + "/js/index.es as ResultsRankings" %>'>
	ResultsRankings.default(
		'<%= resultsRankingsRootElementId %>',
		{
			cancelUrl: '<%= HtmlUtil.escape(redirect) %>',
			searchTerm: 'example'
		},
		{
			companyId: '<%= themeDisplay.getCompanyId() %>',
			searchIndex: '',
			spritemap: '<%= themeDisplay.getPathThemeImages() + "/lexicon/icons.svg" %>'
		}
	);
</aui:script>