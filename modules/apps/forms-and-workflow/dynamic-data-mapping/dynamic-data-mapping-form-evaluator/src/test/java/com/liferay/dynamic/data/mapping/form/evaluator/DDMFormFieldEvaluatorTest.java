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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.DDMFormEvaluatorImpl;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.DDMFormFieldEvaluatorImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class DDMFormFieldEvaluatorTest {

	@Deprecated
	@Test
	public void testFormEvaluatorEvaluate() throws DDMFormEvaluationException {
		DDMForm form = new DDMForm();

		DDMFormField field = getFormField(form);

		DDMFormValues formValues = getFormValues(form, field);

		DDMFormEvaluator evaluator = getDDMFormEvaluator();

		DDMFormEvaluationResult result = evaluator.evaluate(
			form, formValues, LocaleUtil.getDefault());

		List<DDMFormFieldEvaluationResult> fieldResults =
			result.getDDMFormFieldEvaluationResults();

		Assert.assertEquals(1, fieldResults.size());

		DDMFormFieldEvaluationResult fieldResult = fieldResults.get(0);

		Assert.assertEquals(field.getName(), fieldResult.getName());
	}

	@Test
	public void testEvaluate() throws DDMFormEvaluationException {
		DDMForm form = new DDMForm();

		DDMFormField field = getFormField(form);

		DDMFormValues formValues = getFormValues(form, field);

		DDMFormFieldEvaluator evaluator = getDDMFormFieldEvaluator();

		List<DDMFormFieldEvaluationResult> results = evaluator.evaluate(
			form, field, formValues, LocaleUtil.getDefault());

		Assert.assertEquals(1, results.size());

		DDMFormFieldEvaluationResult fieldResult = results.get(0);

		Assert.assertEquals(field.getName(), fieldResult.getName());
	}
	
	@Test
	@Deprecated
	public void testFormEvaluatorEvaluateTwoFields() throws DDMFormEvaluationException {
		DDMForm form = new DDMForm();

		DDMFormField field1 = getFormField(form);

		DDMFormField field2 = getFormField(form);

		DDMFormValues formValues = getFormValues(form, field1, field2);

		DDMFormEvaluator evaluator = getDDMFormEvaluator();

		DDMFormEvaluationResult result = evaluator.evaluate(
			form, formValues, LocaleUtil.getDefault());

		List<DDMFormFieldEvaluationResult> fieldResults =
			result.getDDMFormFieldEvaluationResults();

		Assert.assertEquals(2, fieldResults.size());

		DDMFormFieldEvaluationResult fieldResult = fieldResults.get(0);

		Assert.assertEquals(field1.getName(), fieldResult.getName());

		fieldResult = fieldResults.get(1);
		
		Assert.assertEquals(field2.getName(), fieldResult.getName());
	}

	
	@Test
	public void testEvaluateTwoFields() throws DDMFormEvaluationException {
		DDMForm form = new DDMForm();

		DDMFormField field1 = getFormField(form);

		DDMFormField field2 = getFormField(form);

		DDMFormValues formValues = getFormValues(form, field1, field2);

		DDMFormFieldEvaluator evaluator = getDDMFormFieldEvaluator();

		List<DDMFormFieldEvaluationResult> results = evaluator.evaluate(
			form, field1, formValues, LocaleUtil.getDefault());

		Assert.assertEquals(1, results.size());

		DDMFormFieldEvaluationResult fieldResult = results.get(0);

		Assert.assertEquals(field1.getName(), fieldResult.getName());
	}

	@Deprecated
	protected DDMFormEvaluator getDDMFormEvaluator() {
		DDMFormEvaluator ddmFormEvaluator = new DDMFormEvaluatorImpl();

		ReflectionTestUtil.setFieldValue(
			ddmFormEvaluator, "_ddmExpressionFactory",
			new DDMExpressionFactoryImpl());

		return ddmFormEvaluator;
	}

	protected DDMFormFieldEvaluator getDDMFormFieldEvaluator() {
		DDMFormFieldEvaluator ddmFormFieldEvaluator =
			new DDMFormFieldEvaluatorImpl();

		ReflectionTestUtil.setFieldValue(
			ddmFormFieldEvaluator, "_ddmExpressionFactory",
			new DDMExpressionFactoryImpl());

		return ddmFormFieldEvaluator;
	}

	protected DDMFormField getFormField(DDMForm form) {
		DDMFormField field = new DDMFormField(
			RandomTestUtil.randomString(), "text");

		form.addDDMFormField(field);

		return field;
	}

	protected DDMFormValues getFormValues(
		DDMForm form, DDMFormField... fields) {

		DDMFormValues formValues = new DDMFormValues(form);

		for (DDMFormField field : fields) {
			DDMFormFieldValue fieldValue = new DDMFormFieldValue();

			fieldValue.setName(field.getName());

			Value value = new UnlocalizedValue(RandomTestUtil.randomString());

			fieldValue.setValue(value);

			formValues.addDDMFormFieldValue(fieldValue);
		}

		return formValues;
	}

}