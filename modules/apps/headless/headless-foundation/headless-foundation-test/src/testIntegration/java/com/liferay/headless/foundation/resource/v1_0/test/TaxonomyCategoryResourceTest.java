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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.headless.foundation.dto.v1_0.TaxonomyCategory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TaxonomyCategoryResourceTest
	extends BaseTaxonomyCategoryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());
	}

	protected void assertValid(TaxonomyCategory taxonomyCategory) {
		boolean valid = false;

		if ((taxonomyCategory.getDateCreated() != null) &&
			(taxonomyCategory.getDateModified() != null) &&
			(taxonomyCategory.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		TaxonomyCategory taxonomyCategory1,
		TaxonomyCategory taxonomyCategory2) {

		if (Objects.equals(
				taxonomyCategory1.getDescription(),
				taxonomyCategory2.getDescription()) &&
			Objects.equals(
				taxonomyCategory1.getName(), taxonomyCategory2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected TaxonomyCategory testDeleteTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());
	}

	@Override
	protected TaxonomyCategory testGetTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());
	}

	@Override
	protected TaxonomyCategory
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		return invokePostTaxonomyCategoryTaxonomyCategory(
			taxonomyCategoryId, taxonomyCategory);
	}

	@Override
	protected Long
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId()
		throws Exception {

		TaxonomyCategory taxonomyCategory =
			invokePostTaxonomyVocabularyTaxonomyCategory(
				_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());

		return taxonomyCategory.getId();
	}

	@Override
	protected TaxonomyCategory
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				Long taxonomyVocabularyId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			taxonomyVocabularyId, taxonomyCategory);
	}

	@Override
	protected Long
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId()
		throws Exception {

		return _assetVocabulary.getVocabularyId();
	}

	@Override
	protected TaxonomyCategory testPatchTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());
	}

	@Override
	protected TaxonomyCategory
			testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), taxonomyCategory);
	}

	@Override
	protected TaxonomyCategory
			testPostTaxonomyVocabularyTaxonomyCategory_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), taxonomyCategory);
	}

	@Override
	protected TaxonomyCategory testPutTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		return invokePostTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(), randomTaxonomyCategory());
	}

	private AssetVocabulary _assetVocabulary;

}