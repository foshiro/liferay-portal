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

AUI.add(
	'liferay-message',
	function(A) {
		var EVENT_DATA_DISMISS_ALL = {
			categoryVisible: false
		};

		var NAME = 'liferaymessage';

		var REGEX_CSS_TYPE = A.DOM._getRegExp(
			'\\blfr-message-(alert|error|help|info|success)\\b',
			'g'
		);

		var TPL_HIDE_NOTICES =
			'<button aria-label="' +
			Liferay.Language.get('close') +
			'" type="button" class="close">&#x00D7;</button>';

		var Message = A.Component.create({
			ATTRS: {
				closeButton: {
					valueFn: function() {
						return A.Node.create(TPL_HIDE_NOTICES);
					}
				},

				dismissible: {
					value: true
				},

				hideAllNotices: {
					valueFn: function() {
						var instance = this;

						return A.Node.create(
							'<a href="javascript:;"><small>' +
								instance.get('strings.dismissAll') ||
								Liferay.Language.get(
									'disable-this-note-for-all-portlets'
								) + '</small></a>'
						);
					}
				},

				persistenceCategory: {
					value: ''
				},

				persistent: {
					value: true
				},

				trigger: {
					setter: A.one
				},

				type: {
					value: 'info'
				}
			},

			CSS_PREFIX: 'lfr-message',

			HTML_PARSER: {
				closeButton: '.close',
				hideAllNotices: '.btn-link'
			},

			NAME: NAME,

			UI_ATTRS: ['dismissible', 'persistent', 'type'],

			prototype: {
				initializer: function() {
					var instance = this;

					instance._boundingBox = instance.get('boundingBox');
					instance._contentBox = instance.get('contentBox');

					instance._cssDismissible = instance.getClassName(
						'dismissible'
					);
					instance._cssPersistent = instance.getClassName(
						'persistent'
					);
				},

				renderUI: function() {
					var instance = this;

					var dismissible = instance.get('dismissible');

					if (dismissible) {
						var trigger = instance.get('trigger');

						instance._trigger = trigger;

						var closeButton = instance.get('closeButton');

						if (instance.get('persistenceCategory')) {
							var hideAllNotices = instance.get('hideAllNotices');

							instance._contentBox.append(hideAllNotices);

							instance._contentBox.addClass('dismiss-all-notes');

							instance._hideAllNotices = hideAllNotices;
						}

						instance._closeButton = closeButton;

						instance._contentBox.prepend(closeButton);
					}

					instance._dismissible = dismissible;
				},

				bindUI: function() {
					var instance = this;

					if (instance._dismissible) {
						instance.after(
							'visibleChange',
							instance._afterVisibleChange
						);

						var closeButton = instance._closeButton;

						if (closeButton) {
							closeButton.on(
								'click',
								instance._onCloseButtonClick,
								instance
							);
						}

						var trigger = instance._trigger;

						if (trigger) {
							trigger.on(
								'click',
								instance._onTriggerClick,
								instance
							);
						}

						var hideAllNotices = instance._hideAllNotices;

						if (hideAllNotices) {
							hideAllNotices.on(
								'click',
								instance._onHideAllClick,
								instance
							);
						}
					}
				},

				_afterVisibleChange: function(event) {
					var instance = this;

					var messageVisible = event.newVal;

					instance._contentBox.toggle(messageVisible);

					instance.get('trigger').toggle(!messageVisible);

					if (instance.get('persistent')) {
						var sessionData = {};

						if (themeDisplay.isImpersonated()) {
							sessionData.doAsUserId = themeDisplay.getDoAsUserIdEncoded();
						}

						if (event.categoryVisible === false) {
							sessionData[
								instance.get('persistenceCategory')
							] = true;
						}

						sessionData[instance.get('id')] = messageVisible;

						Liferay.Store(sessionData);
					}
				},

				_onCloseButtonClick: function(event) {
					var instance = this;

					instance.hide();
				},

				_onHideAllClick: function(event) {
					var instance = this;

					instance.set('visible', false, EVENT_DATA_DISMISS_ALL);
				},

				_onTriggerClick: function(event) {
					var instance = this;

					instance.show();
				},

				_uiSetDismissible: function(value) {
					var instance = this;

					instance._boundingBox.toggleClass(
						instance._cssDismissible,
						value
					);
				},

				_uiSetPersistent: function(value) {
					var instance = this;

					instance._boundingBox.toggleClass(
						instance._cssPersistent,
						value
					);
				},

				_uiSetType: function(value) {
					var instance = this;

					var contentBox = instance._contentBox;

					var cssClass = contentBox
						.attr('class')
						.replace(REGEX_CSS_TYPE, '');

					cssClass += ' ' + instance.getClassName(value);

					contentBox.attr('class', cssClass);
				}
			}
		});

		Liferay.Message = Message;
	},
	'',
	{
		requires: ['aui-base', 'liferay-store']
	}
);
