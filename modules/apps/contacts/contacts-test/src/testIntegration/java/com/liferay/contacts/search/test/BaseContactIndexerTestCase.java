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
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Marques de Paula
 */
public abstract class BaseContactIndexerTestCase {

	public void setUp() throws Exception {
		contactFixture = createContactFixture();

		contactFixture.setUp();

		contactIndexerFixture = createContactIndexerFixture();
	}

	protected ContactFixture createContactFixture() {
		return new ContactFixture(
			contactLocalService, _users, _groups, _contacts);
	}

	protected ContactIndexerFixture createContactIndexerFixture() {
		Indexer<?> indexer = indexerRegistry.getIndexer(Contact.class);

		return new ContactIndexerFixture(indexer);
	}

	protected void setGroup(Group group) {
		contactFixture.setGroup(group);
	}

	protected void setUser(User user) {
		contactFixture.setUser(user);
		contactIndexerFixture.setUser(user);
	}

	protected ContactFixture contactFixture;
	protected ContactIndexerFixture contactIndexerFixture;

	@Inject
	protected ContactLocalService contactLocalService;

	@Inject
	protected IndexerRegistry indexerRegistry;

	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private final List<Contact> _contacts = new ArrayList<>(1);

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>(1);

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>(1);

}