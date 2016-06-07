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

package com.liferay.dynamic.data.mapping.form.rule;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.form.rule.internal.DDMFormRuleEvaluatorContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Map;

import org.junit.Before;

import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
public abstract class DDMFormRuleEvaluatorBaseTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField, DDMFormValues ddmFormValues,
		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationResults) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormField.getName());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldRuleEvaluationResult ddmFormFieldRuleEvaluationResult =
				new DDMFormFieldRuleEvaluationResult(
					ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

			ddmFormFieldRuleEvaluationResult.setErrorMessage(StringPool.BLANK);
			ddmFormFieldRuleEvaluationResult.setReadOnly(false);
			ddmFormFieldRuleEvaluationResult.setValid(true);
			ddmFormFieldRuleEvaluationResult.setVisible(true);
			ddmFormFieldRuleEvaluationResult.setValue(
				ddmFormFieldValue.getValue().getString(LocaleUtil.US));

			ddmFormFieldRuleEvaluationResults.put(
				ddmFormField.getName(), ddmFormFieldRuleEvaluationResult);
		}
	}

	protected DDMFormRuleEvaluatorContext createDDMFormRuleEvaluatorContext(
			List<DDMFormField> ddmFormFields, DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext =
			new DDMFormRuleEvaluatorContext(
				null, null, new DDMExpressionFactoryImpl(), ddmFormFields,
				ddmFormValues, null, LocaleUtil.US);

		return ddmFormRuleEvaluatorContext;
	}

	protected DDMFormValues createDDMFormValues() {
		DDMForm ddmForm = new DDMForm();
		return DDMFormValuesTestUtil.createDDMFormValues(ddmForm);
	}

}