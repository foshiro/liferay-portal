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

package com.liferay.dynamic.data.mapping.form.rule.internal;

import static org.mockito.Mockito.when;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldRuleType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adam Brandizzi
 */
@RunWith(PowerMockRunner.class)
public class DDMFormRuleEvaluatorGraphTest {

	@Before
	public void setUp() {
		_form = new DDMForm();
		_nodes = new HashSet<>();

		setUpExpressionFactory();
		setUpFormFields();
	}

	@After
	public void tearDown() {
		_formFields = null;
		_nodes = null;
	}

	@Test
	public void testDiamondGraph() throws Exception {
		DDMFormRuleEvaluatorNode mainNode = getFreeDDMFormRuleEvaluatorNode();

		DDMFormRuleEvaluatorNode dependentNode1 =
			getDependentDDMFormRuleEvaluatorNode(mainNode);

		DDMFormRuleEvaluatorNode dependentNode2 =
			getDependentDDMFormRuleEvaluatorNode(mainNode);

		DDMFormRuleEvaluatorNode finalNode =
			getDependentDDMFormRuleEvaluatorNode(
				dependentNode1, dependentNode2);

		getFreeDDMFormRuleEvaluatorNode();

		DDMFormRuleEvaluatorGraph graph = new DDMFormRuleEvaluatorGraph(
			new HashSet<>(_nodes), _ddmFormRuleEvaluatorContext);

		List<DDMFormRuleEvaluatorNode> sortedNodes = graph.sortNodes();

		Assert.assertEquals(3, _nodes.size());
		Assert.assertEquals(3, sortedNodes.size());
		Assert.assertEquals(mainNode, sortedNodes.get(0));
		Assert.assertEquals(finalNode, sortedNodes.get(3));
	}

	@Test
	public void testIndependentFields() throws Exception {
		DDMFormRuleEvaluatorNode mainNode = getFreeDDMFormRuleEvaluatorNode();

		DDMFormRuleEvaluatorNode dependentNode =
			getDependentDDMFormRuleEvaluatorNode(mainNode);

		getFreeDDMFormRuleEvaluatorNode();

		DDMFormRuleEvaluatorGraph graph = new DDMFormRuleEvaluatorGraph(
			new HashSet<>(_nodes), _ddmFormRuleEvaluatorContext);

		List<DDMFormRuleEvaluatorNode> sortedNodes = graph.sortNodes();

		Assert.assertEquals(3, _nodes.size());
		Assert.assertEquals(2, sortedNodes.size());
		Assert.assertEquals(mainNode, sortedNodes.get(0));
		Assert.assertEquals(dependentNode, sortedNodes.get(1));
	}

	@Test
	public void testOneField() throws Exception {
		DDMFormRuleEvaluatorNode node = getFreeDDMFormRuleEvaluatorNode();

		DDMFormRuleEvaluatorGraph graph = new DDMFormRuleEvaluatorGraph(
			new HashSet<>(_nodes), _ddmFormRuleEvaluatorContext);

		List<DDMFormRuleEvaluatorNode> sortedNodes = graph.sortNodes();

		Assert.assertEquals(0, sortedNodes.size());
	}

	@Test
	public void testTwoFields() throws Exception {
		DDMFormRuleEvaluatorNode mainNode = getFreeDDMFormRuleEvaluatorNode();

		DDMFormRuleEvaluatorNode dependentNode =
			getDependentDDMFormRuleEvaluatorNode(mainNode);

		DDMFormRuleEvaluatorGraph graph = new DDMFormRuleEvaluatorGraph(
			new HashSet<>(_nodes), _ddmFormRuleEvaluatorContext);

		List<DDMFormRuleEvaluatorNode> sortedNodes = graph.sortNodes();

		Assert.assertEquals(2, sortedNodes.size());
		Assert.assertEquals(dependentNode, sortedNodes.get(0));
		Assert.assertEquals(mainNode, sortedNodes.get(1));
	}

	protected DDMFormField createFormField() {
		DDMFormField formField = new DDMFormField(
			RandomTestUtil.randomString(), "text");

		_form.addDDMFormField(formField);
		_formFields.add(formField);

		return formField;
	}

	protected DDMFormRuleEvaluatorNode getDDMFormRuleEvaluatorNode(
		DDMFormField formField, String expression) {

		DDMFormRuleEvaluatorNode node = new DDMFormRuleEvaluatorNode(
			formField.getName(), RandomTestUtil.randomString(),
			DDMFormFieldRuleType.READ_ONLY, expression);

		if (expression != null) {
			_nodes.add(node);
		}
		
		return node;
	}

	protected DDMFormRuleEvaluatorNode getDependentDDMFormRuleEvaluatorNode(
		DDMFormRuleEvaluatorNode mainNode) {

		String expression = "isVisible(" + mainNode.getDDMFormFieldName() + ",\"10\")";

		return getDDMFormRuleEvaluatorNode(createFormField(), expression);
	}

	protected DDMFormRuleEvaluatorNode getDependentDDMFormRuleEvaluatorNode(
		DDMFormRuleEvaluatorNode mainNode1,
		DDMFormRuleEvaluatorNode mainNode2) {

		String expression = "between(" + 
			mainNode1.getDDMFormFieldName() + ",10," +
			mainNode2.getDDMFormFieldName() +")";

		return getDDMFormRuleEvaluatorNode(createFormField(), expression);
	}

	protected DDMFormRuleEvaluatorNode getFreeDDMFormRuleEvaluatorNode() {
		return getDDMFormRuleEvaluatorNode(createFormField(), null);
	}

	protected void setUpExpressionFactory() {
		when(
			_ddmFormRuleEvaluatorContext.getDDMExpressionFactory()
		).thenReturn(
			new DDMExpressionFactoryImpl()
		);
	}

	protected void setUpFormFields() {
		_formFields = new ArrayList<>();

		when(
			_ddmFormRuleEvaluatorContext.getDDMFormFields()
		).thenReturn(
			_formFields
		);
	}

	@Mock
	private DDMFormRuleEvaluatorContext _ddmFormRuleEvaluatorContext;

	private DDMForm _form;
	private List<DDMFormField> _formFields;
	private Set<DDMFormRuleEvaluatorNode> _nodes;

}