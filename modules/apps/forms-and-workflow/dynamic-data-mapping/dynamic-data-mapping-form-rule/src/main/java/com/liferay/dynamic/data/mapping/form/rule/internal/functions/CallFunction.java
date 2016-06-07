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

package com.liferay.dynamic.data.mapping.form.rule.internal.functions;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.form.rule.DDMFormFieldRuleEvaluationResult;
import com.liferay.dynamic.data.mapping.form.rule.DDMFormRuleEvaluationException;
import com.liferay.dynamic.data.mapping.form.rule.internal.DDMFormRuleEvaluatorContext;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class CallFunction extends BaseFunction {

	@Override
	public String execute(
			DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
			List<String> parameters)
		throws DDMFormRuleEvaluationException {

		if (parameters.size() < 5) {
			throw new DDMFormRuleEvaluationException("Invalid function call");
		}

		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationResultMap =
				ddmFormRuleEvaluatorContext.
					getDDMFormFieldRuleEvaluationResults();

		long ddmDataProviderInstanceId = Long.parseLong(parameters.get(2));
		String paramsExpression = parameters.get(3);
		String resultMapExpression = parameters.get(4);

		try {
			JSONArray jsonArray = executeDataProvider(
				ddmFormRuleEvaluatorContext, ddmDataProviderInstanceId,
				paramsExpression);

			Map<String, String> resultMap = extractResultMap(
				resultMapExpression);

			if (jsonArray.length() == 1) {
				setDDMFormFieldValues(
					ddmFormFieldRuleEvaluationResultMap,
					jsonArray.getJSONObject(0), resultMap);
			}

			return StringPool.BLANK;
		}
		catch (Exception e) {
			throw new DDMFormRuleEvaluationException(
				"An error occured while trying to call a data provider", e);
		}
	}

	@Override
	public String getPattern() {
		return _PATTERN;
	}

	protected void addDDMDataProviderContextParameters(
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
		String paramsExpression,
		DDMDataProviderContext ddmDataProviderContext) {

		Map<String, String> parameters = extractParameters(
			ddmFormRuleEvaluatorContext, paramsExpression);

		if (parameters.size() == 0) {
			return;
		}

		ddmDataProviderContext.addParameters(parameters);
	}

	protected JSONArray executeDataProvider(
			DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
			Long ddmDataProviderInstanceId, String paramsExpression)
		throws Exception {

		DDMDataProviderInstanceService ddmDataProviderInstanceService =
			ddmFormRuleEvaluatorContext.getDDMDataProviderInstanceService();

		DDMDataProviderTracker ddmDataProviderTracker =
			ddmFormRuleEvaluatorContext.getDDMDataProviderTracker();

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstanceService.getDataProviderInstance(
				ddmDataProviderInstanceId);

		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer =
			ddmFormRuleEvaluatorContext.getDDMFormValuesJSONDeserializer();

		DDMDataProvider ddmDataProvider =
			ddmDataProviderTracker.getDDMDataProvider(
				ddmDataProviderInstance.getType());

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		DDMFormValues ddmFormValues = ddmFormValuesJSONDeserializer.deserialize(
			ddmForm, ddmDataProviderInstance.getDefinition());

		DDMDataProviderContext ddmDataProviderContext =
			new DDMDataProviderContext(ddmFormValues);

		addDDMDataProviderContextParameters(
			ddmFormRuleEvaluatorContext, paramsExpression,
			ddmDataProviderContext);

		return ddmDataProvider.doGet(ddmDataProviderContext);
	}

	protected void extractDDMFormFieldValue(
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
		Map<String, String> paramsMap, String expression) {

		String[] tokens = StringUtil.split(expression, CharPool.EQUAL);

		String ddmFormFieldValue = getDDMFormFieldValue(
			ddmFormRuleEvaluatorContext, tokens[1]);

		paramsMap.put(tokens[0], ddmFormFieldValue);
	}

	protected Map<String, String> extractParameters(
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
		String expression) {

		Map<String, String> paramsMap = new HashMap<>();

		if (Validator.isNull(expression)) {
			return paramsMap;
		}

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			extractDDMFormFieldValue(
				ddmFormRuleEvaluatorContext, paramsMap, expression);
		}
		else {
			for (String innerExpression : innerExpressions) {
				extractDDMFormFieldValue(
					ddmFormRuleEvaluatorContext, paramsMap, innerExpression);
			}
		}

		return paramsMap;
	}

	protected Map<String, String> extractResultMap(String expression) {
		Map<String, String> resultMap = new HashMap<>();

		if (Validator.isNull(expression)) {
			return resultMap;
		}

		String[] innerExpressions = StringUtil.split(
			expression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			String[] tokens = StringUtil.split(expression, CharPool.EQUAL);
			resultMap.put(tokens[0], tokens[1]);
		}
		else {
			for (String innerExpression : innerExpressions) {
				String[] tokens = StringUtil.split(
					innerExpression, CharPool.EQUAL);
				resultMap.put(tokens[0], tokens[1]);
			}
		}

		return resultMap;
	}

	protected String getDDMFormFieldValue(
		DDMFormRuleEvaluatorContext ddmFormRuleEvaluatorContext,
		String ddmFormFieldName) {

		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationResultMap =
				ddmFormRuleEvaluatorContext.
					getDDMFormFieldRuleEvaluationResults();

		DDMFormFieldRuleEvaluationResult ddmFormFieldRuleEvaluationResult =
			ddmFormFieldRuleEvaluationResultMap.get(ddmFormFieldName);

		Object value = ddmFormFieldRuleEvaluationResult.getValue();

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return value.toString();
	}

	protected void setDDMFormFieldValue(
		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationMap, String ddmFormFieldName,
		Object value) {

		DDMFormFieldRuleEvaluationResult ddmFormFieldRuleEvaluationResult =
			ddmFormFieldRuleEvaluationMap.get(ddmFormFieldName);

		ddmFormFieldRuleEvaluationResult.setValue(value);
	}

	protected void setDDMFormFieldValues(
		Map<String, DDMFormFieldRuleEvaluationResult>
			ddmFormFieldRuleEvaluationMap,
		JSONObject jsonObject, Map<String, String> resultMap) {

		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			setDDMFormFieldValue(
				ddmFormFieldRuleEvaluationMap, entry.getKey(),
				jsonObject.get(entry.getValue()));
		}
	}

	private static final String _PATTERN =
		"((call)\\((\\d+),\"(.+)\",\"(.+)\"\\))";

}