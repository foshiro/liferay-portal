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
	taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
	taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects/>

<%
String synonymSetsRootElementId = renderResponse.getNamespace() + "-synonym-sets-root";

portletDisplay.setShowBackIcon(true);
%>

<div id="<%= synonymSetsRootElementId %>"></div>

<aui:script require='<%= npmResolvedPackageName + "/js/index-synonym-sets.es as SynonymSets" %>'>
	SynonymSets.default(
		'<%= synonymSetsRootElementId %>'
	);
</aui:script>