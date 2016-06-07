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

package com.liferay.dynamic.data.mapping.form.rule.functions;

import com.liferay.dynamic.data.mapping.form.rule.DDMFormFieldRuleEvaluationResult;
import com.liferay.dynamic.data.mapping.form.rule.DDMFormRuleEvaluationException;
import com.liferay.dynamic.data.mapping.form.rule.DDMFormRuleEvaluatorBaseTest;
import com.liferay.dynamic.data.mapping.form.rule.internal.DDMFormRuleEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.rule.internal.functions.ContainsFunction;
import com.liferay.dynamic.data.mapping.form.rule.internal.functions.EqualsFunction;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class EqualsFunctionTest extends DDMFormRuleEvaluatorBaseTest {

	@Test(expected = DDMFormRuleEvaluationException.class)
	public void testInvalidParameters() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();
		DDMFormValues ddmFormValues = createDDMFormValues();
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmFormFields, ddmFormValues);

		EqualsFunction equalsFunction = new EqualsFunction();

		equalsFunction.execute(
			ddmFormRuleEvaluatorContext, ListUtil.fromArray(new String[0]));
	}

	@Test
	public void testWithConstants() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("world"));

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

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

		List<String> parameters = ListUtil.fromArray(
			new String[] {
				"equals(field0,\"world\")", "equals", "field0", "world"
			});

		EqualsFunction equalsFunction = new EqualsFunction();

		String expression = equalsFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("TRUE", expression);
	}

	@Test
	public void testWithConstants2() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("world"));

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

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

		List<String> parameters = ListUtil.fromArray(
			new String[] {
				"equals(field0,\"hello\")", "equals", "field0", "hello"
			});

		EqualsFunction equalsFunction = new EqualsFunction();

		String expression = equalsFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("FALSE", expression);
	}

	@Test
	public void testWithOtherField() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0",
				new UnlocalizedValue("texts are equal"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1",
				new UnlocalizedValue("texts are equal"));

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

		List<String> parameters = ListUtil.fromArray(
			new String[] {
				"equals(field0, field1)", "equals", "field0", "field1"
			});

		ContainsFunction containsFunction = new ContainsFunction();

		String expression = containsFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("TRUE", expression);
	}

	@Test
	public void testWithOtherField2() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0",
				new UnlocalizedValue("a simple text"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1",
				new UnlocalizedValue("a different text"));

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

		List<String> parameters = ListUtil.fromArray(
			new String[] {
				"equals(field0, field1)", "equals", "field0", "field1"
			});

		ContainsFunction containsFunction = new ContainsFunction();

		String expression = containsFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("FALSE", expression);
	}

}