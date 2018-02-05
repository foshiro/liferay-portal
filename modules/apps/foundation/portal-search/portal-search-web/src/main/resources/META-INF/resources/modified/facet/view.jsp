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

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetCalendarDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetTermDisplayContext" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:defineObjects />

<style>
	.facet-checkbox-label {
		display: block;
	}
</style>

<%
ModifiedFacetDisplayContext modifiedFacetDisplayContext = (ModifiedFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

ModifiedFacetTermDisplayContext customRangeTermDisplayContext = modifiedFacetDisplayContext.getCustomRangeTermDisplayContext();

ModifiedFacetCalendarDisplayContext modifiedFacetCalendarDisplayContext = modifiedFacetDisplayContext.getModifiedFacetCalendarDisplayContext();

// Because of JavaScript?!?!?

int i = 0;
%>

<liferay-ui:panel-container extended="<%= true %>" id='<%= renderResponse.getNamespace() + "facetModifiedPanelContainer" %>' markupView="lexicon" persistState="<%= true %>">
	<liferay-ui:panel collapsible="<%= true %>" cssClass="search-facet" id='<%= renderResponse.getNamespace() + "facetModifiedPanel" %>' markupView="lexicon" persistState="<%= true %>" title="last-modified">
		<aui:form method="get" name="modifiedFacetForm">
			<aui:input autocomplete="off" name="inputFacetName" type="hidden" value="modified" />
			<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= modifiedFacetDisplayContext.getParameterName() %>" />

			<aui:field-wrapper cssClass='<%= renderResponse.getNamespace() + "calendar calendar_" %>' label="" name="<%= HtmlUtil.escapeAttribute(modifiedFacetDisplayContext.getParameterName()) %>">
				<ul class="list-unstyled modified nav-stacked">

					<%
					for (ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext : modifiedFacetDisplayContext.getTermDisplayContexts()) {
						if (modifiedFacetTermDisplayContext.getFrequency() == 0) {
							continue;
						}
					%>

						<li class="facet-value nav-item" name="<%= renderResponse.getNamespace() + "range_" + modifiedFacetTermDisplayContext.getLabel() %>">
							<a
								class="<%= modifiedFacetTermDisplayContext.isActive() ? "active" : StringPool.BLANK %> nav-link"
								href="<%= modifiedFacetTermDisplayContext.getRangeURL() %>"
							>

							<span class="term-name">
								<liferay-ui:message key="<%= modifiedFacetTermDisplayContext.getLabel() %>" />
							</span>

							<small class="term-count">
								<span class="badge badge-info frequency"><%= modifiedFacetTermDisplayContext.getFrequency() %></span>
							</small>
							</a>
						</li>

					<%
					}
					%>

					<li class="facet-value nav-item" name="<%= renderResponse.getNamespace() + "range_" + customRangeTermDisplayContext.getLabel() %>">
						<a
							class="<%= customRangeTermDisplayContext.isActive() ? "active" : StringPool.BLANK %> nav-link"
							href="<%= customRangeTermDisplayContext.getRangeURL() %>"
							id="<portlet:namespace /><%= customRangeTermDisplayContext.getLabel() + "-toggleLink" %>"
						>
							<span class="term-name"><liferay-ui:message key="<%= customRangeTermDisplayContext.getLabel() %>" />&hellip;</span>

							<c:if test="<%= customRangeTermDisplayContext.isSelected() %>">
								<span class="<%= customRangeTermDisplayContext.getLabel() %>-frequency frequency">(<%= customRangeTermDisplayContext.getFrequency() %>)</span>
							</c:if>
						</a>
					</li>

					<div class="<%= !modifiedFacetCalendarDisplayContext.isSelected() ? "hide" : StringPool.BLANK %> modified-custom-range" id="<portlet:namespace />customRange">
						<div class="col-md-6" id="<portlet:namespace />customRangeFrom">
							<aui:field-wrapper label="from">
								<liferay-ui:input-date
									dayParam="fromDay"
									dayValue="<%= modifiedFacetCalendarDisplayContext.getFromDayValue() %>"
									disabled="<%= false %>"
									firstDayOfWeek="<%= modifiedFacetCalendarDisplayContext.getFromFirstDayOfWeek() %>"
									monthParam="fromMonth"
									monthValue="<%= modifiedFacetCalendarDisplayContext.getFromMonthValue() %>"
									name="fromInput"
									yearParam="fromYear"
									yearValue="<%= modifiedFacetCalendarDisplayContext.getFromYearValue() %>"
								/>
							</aui:field-wrapper>
						</div>

						<div class="col-md-6" id="<portlet:namespace />customRangeTo">
							<aui:field-wrapper label="to">
								<liferay-ui:input-date
									dayParam="toDay"
									dayValue="<%= modifiedFacetCalendarDisplayContext.getToDayValue() %>"
									disabled="<%= false %>"
									firstDayOfWeek="<%= modifiedFacetCalendarDisplayContext.getToFirstDayOfWeek() %>"
									monthParam="toMonth"
									monthValue="<%= modifiedFacetCalendarDisplayContext.getToMonthValue() %>"
									name="toInput"
									yearParam="toYear"
									yearValue="<%= modifiedFacetCalendarDisplayContext.getToYearValue() %>"
								/>
							</aui:field-wrapper>
						</div>

						<%
						String taglibSearchCustomRange = "window['" + renderResponse.getNamespace() + HtmlUtil.escapeJS(modifiedFacetDisplayContext.getParameterName()) + "customRangeFilter'].filter(event);";
						%>

						<aui:button disabled="<%= (!modifiedFacetCalendarDisplayContext.isFromBeforeTo()) %>" name="searchCustomRangeButton" onClick="<%= taglibSearchCustomRange %>" value="search" />
					</div>
				</ul>
			</aui:field-wrapper>

			<c:if test="<%= !modifiedFacetDisplayContext.isNothingSelected() %>">
				<aui:a
					cssClass="text-default"
					href="javascript:;"
					onClick="Liferay.Search.FacetUtil.clearSelections(event);"
				>
					<small><liferay-ui:message key="clear" /></small>
				</aui:a>
			</c:if>
		</aui:form>
	</liferay-ui:panel>
</liferay-ui:panel-container>

<aui:script use="liferay-search-modified-facet">
	var fromInputDatePicker = Liferay.component('<portlet:namespace />fromInputDatePicker');

	var toInputDatePicker = Liferay.component('<portlet:namespace />toInputDatePicker');

	var customRangeFilter = new Liferay.Search.ModifiedFacetFilter(fromInputDatePicker, toInputDatePicker);

	window.<portlet:namespace /><%= HtmlUtil.escapeJS(modifiedFacetDisplayContext.getParameterName()) %>customRangeFilter = customRangeFilter;
</aui:script>