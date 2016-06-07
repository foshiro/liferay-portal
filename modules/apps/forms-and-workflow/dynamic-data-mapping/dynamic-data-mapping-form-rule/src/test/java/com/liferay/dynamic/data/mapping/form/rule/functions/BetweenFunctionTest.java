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
import com.liferay.dynamic.data.mapping.form.rule.internal.functions.BetweenFunction;
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
public class BetweenFunctionTest extends DDMFormRuleEvaluatorBaseTest {

	@Test(expected = DDMFormRuleEvaluationException.class)
	public void testInvalidParameters() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();
		DDMFormValues ddmFormValues = createDDMFormValues();
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			createDDMFormRuleEvaluatorContext(ddmFormFields, ddmFormValues);
		BetweenFunction betweenFunction = new BetweenFunction();

		betweenFunction.execute(
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
				"field0_instanceId", "field0", new UnlocalizedValue("15"));

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
				"between(field0,10,20)", "between", "field0", "10", "20"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
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
				"field0_instanceId", "field0", new UnlocalizedValue("3"));

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
				"between(field0,1,2)", "between", "field0", "1", "2"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("FALSE", expression);
	}

	@Test
	public void testWithConstants3() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("7"));

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
				"between(field0,5,7)", "between", "field0", "5", "7"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("TRUE", expression);
	}

	@Test
	public void testWithConstants4() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("2"));

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
				"between(field0,2,4)", "between", "field0", "2", "4"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("TRUE", expression);
	}

	@Test
	public void testWithConstants5() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormValues ddmFormValues = createDDMFormValues();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("1"));

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
				"between(field0,3,6)", "between", "field0", "3", "6"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("FALSE", expression);
	}

	@Test
	public void testWithOtherFields() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormField fieldDDMFormField2 = new DDMFormField("field2", "text");

		ddmFormFields.add(fieldDDMFormField2);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("10"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("5"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		DDMFormFieldValue fieldDDMFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("15"));

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

		List<String> parameters = ListUtil.fromArray(
			new String[] {
				"between(field0,field1,field2)", "between", "field0", "field1",
				"field2"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("TRUE", expression);
	}

	@Test
	public void testWithOtherFields2() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormField fieldDDMFormField2 = new DDMFormField("field2", "text");

		ddmFormFields.add(fieldDDMFormField2);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("17"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("5"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue1);

		DDMFormFieldValue fieldDDMFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2_instanceId", "field2", new UnlocalizedValue("15"));

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

		List<String> parameters = ListUtil.fromArray(
			new String[] {
				"between(field0,field1,field2)", "between", "field0", "field1",
				"field2"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("FALSE", expression);
	}

	@Test
	public void testWithOtherFieldsAndConstants() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("3"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("5"));

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
				"between(field0,2,field1)", "between", "field0", "2", "field1"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("TRUE", expression);
	}

	@Test
	public void testWithOtherFieldsAndConstants2() throws Exception {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		DDMFormField fieldDDMFormField0 = new DDMFormField("field0", "text");

		ddmFormFields.add(fieldDDMFormField0);

		DDMFormField fieldDDMFormField1 = new DDMFormField("field1", "text");

		ddmFormFields.add(fieldDDMFormField1);

		DDMFormValues ddmFormValues = createDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue fieldDDMFormFieldValue0 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("3"));

		ddmFormFieldValues.add(fieldDDMFormFieldValue0);

		DDMFormFieldValue fieldDDMFormFieldValue1 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1_instanceId", "field1", new UnlocalizedValue("5"));

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
				"between(field0,field1,10)", "between", "field0", "field1", "10"
			});

		BetweenFunction betweenFunction = new BetweenFunction();

		String expression = betweenFunction.execute(
			ddmFormRuleEvaluatorContext, parameters);

		Assert.assertEquals("FALSE", expression);
	}

}