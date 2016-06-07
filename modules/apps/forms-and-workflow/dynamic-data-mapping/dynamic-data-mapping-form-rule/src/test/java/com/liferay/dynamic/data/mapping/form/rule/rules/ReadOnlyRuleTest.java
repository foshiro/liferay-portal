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

package com.liferay.dynamic.data.mapping.form.rule.rules;

import com.liferay.dynamic.data.mapping.form.rule.DDMFormFieldRuleEvaluationResult;
import com.liferay.dynamic.data.mapping.form.rule.DDMFormRuleEvaluatorBaseTest;
import com.liferay.dynamic.data.mapping.form.rule.internal.DDMFormRuleEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.rule.internal.rules.ReadOnlyRule;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class ReadOnlyRuleTest extends DDMFormRuleEvaluatorBaseTest {

	@Test
	public void testNotReadOnly() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field2", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormField fieldDDMFormField2 = new DDMFormField("field3", "text");

		ddmFormFields.add(fieldDDMFormField2);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("3"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2",
				new UnlocalizedValue("simple text"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		DDMFormFieldValue fieldDDMFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field3_instanceId", "field3",
				new UnlocalizedValue("simple text"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue2);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmFormFields, ddmFormValues);

		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationResults =
				ddmFormRuleEvaluatorContext.
					getDDMFormFieldRuleEvaluationResults();

		createDDMFormFieldRuleEvaluationResult(
			fieldDDMFormField0, ddmFormValues,
			ddmFormFieldRuleEvaluationResults);

		createDDMFormFieldRuleEvaluationResult(
			fieldDDMFormField1, ddmFormValues,
			ddmFormFieldRuleEvaluationResults);

		createDDMFormFieldRuleEvaluationResult(
			fieldDDMFormField2, ddmFormValues,
			ddmFormFieldRuleEvaluationResults);

		ReadOnlyRule readOnlyRule = new ReadOnlyRule(
			"field1", StringPool.BLANK,
			"between(field1, 5, 10) && equals(field2, field3)",
			ddmFormRuleEvaluatorContext);

		readOnlyRule.execute();

		DDMFormFieldRuleEvaluationResult ddmFormFieldRuleEvaluationResult =
			ddmFormFieldRuleEvaluationResults.get("field1");

		Assert.assertFalse(ddmFormFieldRuleEvaluationResult.isReadOnly());
	}

	@Test
	public void testReadOnly() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field2", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("3"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("7"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmFormFields, ddmFormValues);

		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationResults =
				ddmFormRuleEvaluatorContext.
					getDDMFormFieldRuleEvaluationResults();

		createDDMFormFieldRuleEvaluationResult(
			fieldDDMFormField0, ddmFormValues,
			ddmFormFieldRuleEvaluationResults);

		createDDMFormFieldRuleEvaluationResult(
			fieldDDMFormField1, ddmFormValues,
			ddmFormFieldRuleEvaluationResults);

		ReadOnlyRule readOnlyRule = new ReadOnlyRule(
			"field1", StringPool.BLANK, "10 >= (field1 + field2)",
			ddmFormRuleEvaluatorContext);

		readOnlyRule.execute();

		DDMFormFieldRuleEvaluationResult ddmFormFieldRuleEvaluationResult =
			ddmFormFieldRuleEvaluationResults.get("field1");

		Assert.assertTrue(ddmFormFieldRuleEvaluationResult.isReadOnly());
	}

}