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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryValidatorImplTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());

		_fragmentEntryValidatorImpl = new FragmentEntryValidatorImpl();
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameEmpty()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"#: only 1 subschema matches out of 2\n#/fieldSets/0/fields/0" +
				"/name: expected minLength: 1, actual: 0");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-invalid-field-name-empty.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"#: only 1 subschema matches out of 2\n#/fieldSets/0/fields/0: " +
				"required key [name] not found");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-invalid-field-name-missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameNonalphanumeric()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"#: only 1 subschema matches out of 2\n#/fieldSets/0/fields/0" +
				"/name: string [a_b-c.d?e] does not match pattern " +
					"^[A-Za-z0-9]+");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent(
				"configuration-invalid-field-name-non-alphanumeric.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameWithSpace()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"#: only 1 subschema matches out of 2\n#/fieldSets/0/fields/0" +
				"/name: string [a b] does not match pattern ^[A-Za-z0-9]+");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent(
				"configuration-invalid-field-name-with-space.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSetsExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"#: extraneous key [extra] is not permitted");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent(
				"configuration-invalid-field-sets-extra-properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSetsMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"#: required key [fieldSets] not found");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-invalid-field-sets-missing.json"));
	}

	@Test
	public void testValidateConfigurationValidComplete() throws Exception {
		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-valid-complete.json"));
	}

	@Test
	public void testValidateConfigurationValidRequired() throws Exception {
		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-valid-required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _getFileContent(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private FragmentEntryValidatorImpl _fragmentEntryValidatorImpl;

}