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

package com.liferay.contacts.search.test;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;

/**
 * @author Lucas Marques de Paula
 */
public abstract class BaseContactIndexerTestCase {

	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpContactFixture();
		setUpContactIndexerFixture();
	}

	protected void setUpContactFixture() throws Exception {
		contactFixture = new ContactFixture(contactLocalService);

		contactFixture.setUp();

		contactFixture.setUser(_user);
		contactFixture.setGroup(_group);

		_contacts = contactFixture.getContacts();
	}

	protected void setUpContactIndexerFixture() {
		Indexer<Contact> indexer = indexerRegistry.getIndexer(Contact.class);

		contactIndexerFixture = new ContactIndexerFixture(indexer);

		contactIndexerFixture.setUser(_user);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();
		_users = userSearchFixture.getUsers();

		_group = userSearchFixture.addGroup();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);
	}

	protected ContactFixture contactFixture;
	protected ContactIndexerFixture contactIndexerFixture;

	@Inject
	protected ContactLocalService contactLocalService;

	@Inject
	protected IndexerRegistry indexerRegistry;

	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private List<Contact> _contacts;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}