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
package com.liferay.calendar.rest.internal.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.test.util.CalendarResourceTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarResourceResourceTest {

	@Before
	public void setUp() throws MalformedURLException {
		_url = new URL("http://localhost:8080");
	}

	@Test
	public void testWebServiceInvocation() throws Exception {
		_group = GroupTestUtil.addGroup();

		CalendarResource calendarResource =
			CalendarResourceTestUtil.addCalendarResource(_group);

		Locale locale = LocaleUtil.fromLanguageId(
			calendarResource.getNameCurrentLanguageId());

		String string = getResult(
			"/o/calendar-rest/calendar-resource/" +
				calendarResource.getCalendarResourceId());

		assertCalendarResourceResult(calendarResource, string, locale);
	}

	protected void assertCalendarResourceResult(
			CalendarResource calendarResource, String jsonResult, Locale locale)
		throws JSONException, PortalException {

		JSONObject object = JSONFactoryUtil.createJSONObject(jsonResult);

		Assert.assertEquals(
			calendarResource.getCalendarResourceId(),
			object.getLong("calendarResourceId"));
		Assert.assertEquals(
			calendarResource.getClassNameId(), object.getLong("classNameId"));
		Assert.assertEquals(
			calendarResource.getClassPK(), object.getLong("classPK"));
		Assert.assertEquals(
			calendarResource.getClassUuid(), object.getLong("classUuid"));
		Assert.assertEquals(
			calendarResource.getGroupId(), object.getLong("cId"));
		Assert.assertEquals(
			calendarResource.getGroupId(), object.getLong("groupId"));
		Assert.assertEquals(
			calendarResource.getName(locale), object.getString("name"));
		Assert.assertEquals(
			calendarResource.getGroupId(), object.getLong("userId"));
	}

	protected String getResult(String urlString) throws Exception {
		final URL url = new URL(_url, urlString);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		credentialsProvider.setCredentials(
			new AuthScope(url.getHost(), url.getPort()),
			new UsernamePasswordCredentials("test@liferay.com", "test"));

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet httpGet = new HttpGet(url.toURI());

		CloseableHttpResponse closeableHttpResponse =
			closeableHttpClient.execute(httpGet);

		HttpEntity httpEntity = closeableHttpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		return StringUtil.read(inputStream);
	}

	@DeleteAfterTestRun
	private Group _group;

	private URL _url;

}