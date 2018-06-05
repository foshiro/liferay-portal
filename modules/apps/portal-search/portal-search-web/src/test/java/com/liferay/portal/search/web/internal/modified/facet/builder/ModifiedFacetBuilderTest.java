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

package com.liferay.portal.search.web.internal.modified.facet.builder;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.internal.facet.ModifiedFacetImpl;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ModifiedFacetBuilderTest {

	@Before
	public void setUp() {
		calendarFactory = createCalendarFactory();
		dateFormatFactory = new DateFormatFactoryImpl();
		jsonFactory = new JSONFactoryImpl();

		setUpJSONFactoryUtil();
	}

	@Test
	public void testBoundedRange() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			createModifiedFacetBuilder();

		modifiedFacetBuilder.setCustomRangeFrom("20180131");
		modifiedFacetBuilder.setCustomRangeTo("20180228");

		assertRange(
			"20180131000000", "20180228235959", modifiedFacetBuilder.build());
	}

	@Test
	public void testNamedRange() {
		Mockito.doReturn(
			new GregorianCalendar(2018, Calendar.MARCH, 1, 15, 19, 23)
		).when(
			calendarFactory
		).getCalendar();

		ModifiedFacetBuilder modifiedFacetBuilder =
			createModifiedFacetBuilder();

		modifiedFacetBuilder.setSelectedRanges("past-24-hours");

		assertRange(
			"20180228151923", "20180301151923", modifiedFacetBuilder.build());
	}

	@Test
	public void testUseSetRanges() {
		ModifiedFacetBuilder modifiedFacetBuilder =
			createModifiedFacetBuilder();

		JSONArray rangesJSONArray = createRangesJSONArray();

		modifiedFacetBuilder.setRangesJSONArray(rangesJSONArray);

		assertRangesJSONArray(rangesJSONArray, modifiedFacetBuilder.build());
	}

	protected void addRangeJSONObject(
		JSONArray jsonArray, String label, String range) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put("label", label);
		jsonObject.put("range", range);

		jsonArray.put(jsonObject);
	}

	protected void assertRange(String from, String to, Facet facet) {
		List<String> calendars = getRangeBounds(facet);

		Assert.assertEquals(from, calendars.get(0));
		Assert.assertEquals(to, calendars.get(1));
	}

	protected void assertRangeJSONObjectEquals(
		JSONObject jsonObject1, JSONObject jsonObject2) {

		Assert.assertEquals(
			jsonObject1.getString("label"), jsonObject2.getString("label"));
		Assert.assertEquals(
			jsonObject1.getString("range"), jsonObject2.getString("range"));
	}

	protected void assertRangesJSONArray(
		JSONArray rangesJSONArray, Facet facet) {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject data = facetConfiguration.getData();

		JSONArray facetRangesJSONArray = data.getJSONArray("ranges");

		Assert.assertEquals(
			rangesJSONArray.length(), facetRangesJSONArray.length());

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			assertRangeJSONObjectEquals(
				rangesJSONArray.getJSONObject(i),
				facetRangesJSONArray.getJSONObject(i));
		}
	}

	protected CalendarFactory createCalendarFactory() {
		CalendarFactory calendarFactory = Mockito.mock(CalendarFactory.class);

		Mockito.doReturn(
			Calendar.getInstance()
		).when(
			calendarFactory
		).getCalendar();

		return calendarFactory;
	}

	protected ModifiedFacetBuilder createModifiedFacetBuilder() {
		SearchContext searchContext = new SearchContext();

		ModifiedFacetFactory modifiedFacetFactory = createModifiedFacetFactory(
			searchContext);

		ModifiedFacetBuilder modifiedFacetBuilder = new ModifiedFacetBuilder(
			modifiedFacetFactory, calendarFactory, dateFormatFactory);

		modifiedFacetBuilder.setSearchContext(searchContext);

		return modifiedFacetBuilder;
	}

	protected ModifiedFacetFactory createModifiedFacetFactory(
		SearchContext searchContext) {

		ModifiedFacetFactory modifiedFacetFactory = Mockito.mock(
			ModifiedFacetFactory.class);

		Mockito.doReturn(
			new ModifiedFacetImpl(Field.MODIFIED_DATE, searchContext)
		).when(
			modifiedFacetFactory
		).newInstance(
			Mockito.anyObject()
		);

		return modifiedFacetFactory;
	}

	protected JSONArray createRangesJSONArray() {
		return createRangesJSONArray(
			"past-hour", "[20180215120000 TO 20180215140000]", "past-24-hours",
			"[20180214130000 TO 20180215140000]", "past-week",
			"[20180208130000 TO 20180215140000]", "past-month",
			"[20180115130000 TO 20180215140000]", "past-year",
			"[20170215130000 TO 20180215140000]");
	}

	protected JSONArray createRangesJSONArray(String... labelsAndRanges) {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (int i = 0; i < labelsAndRanges.length; i += 2) {
			addRangeJSONObject(
				jsonArray, labelsAndRanges[i], labelsAndRanges[i + 1]);
		}

		return jsonArray;
	}

	protected List<String> getRangeBounds(Facet facet) {
		String range = facet.getSelections()[0];

		String[] dateStrings = RangeParserUtil.parserRange(range);

		return Arrays.asList(dateStrings);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected CalendarFactory calendarFactory;
	protected DateFormatFactory dateFormatFactory;
	protected JSONFactoryImpl jsonFactory;

}