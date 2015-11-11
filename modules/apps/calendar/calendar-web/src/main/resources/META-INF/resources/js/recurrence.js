AUI.add(
	'liferay-calendar-recurrence-dialog',
	function(A) {

		var AArray = A.Array;

		var Lang = A.Lang;

		var toInt = Lang.toInt;

		var FREQUENCY_DAILY = 'DAILY';

		var FREQUENCY_WEEKLY = 'WEEKLY';

		var FREQUENCY_MONTHLY = 'MONTHLY';

		var FREQUENCY_YEARLY = 'YEARLY';

		var LIMIT_DATE = 'on';

		var LIMIT_COUNT = 'after';

		var LIMIT_UNLIMITED = 'never';

		var RecurrenceDialogController = A.Component.create(
			{

				ATTRS: {
					container: {
						setter: A.one,
						value: null
					},

					dayOfWeekSelect: {
						setter: A.one,
						value: null
					},

					daysOfWeekCheckboxes: {
						getter: '_getDaysOfWeekCheckboxes'
					},

					weeklyRecurrenceOptions: {
						setter: A.one,
						value: null
					},

					frequencySelect: {
						setter: A.one,
						value: null
					},

					intervalSelect: {
						setter: A.one,
						value: null
					},

					limitByCountRadioButton: {
						setter: A.one,
						value: null
					},

					limitByCountInput: {
						setter: A.one,
						value: null
					},

					limitByDateRadioButton: {
						setter: A.one,
						value: null
					},

					limitByDateDatePicker: {
						value: null
					},

					limitRadioButtons: {
						getter: '_getLimitRadioButtons'
					},

					limitType: {
						getter: '_getLimitType'
					},

					noLimitRadioButton: {
						setter: A.one,
						value: null
					},

					positionalDayOfWeekOptions: {
						setter: A.one,
						value: null
					},

					monthlyRecurrenceOptions: {
						setter: A.one,
						value: null
					},

					positionSelect: {
						setter: A.one,
						value: null
					},

					repeatCheckbox: {
						setter: A.one,
						value: null
					},

					repeatOnDayOfWeekRadioButton: {
						setter: A.one,
						value: null
					},

					repeatOnDayOfMonthRadioButton: {
						setter: A.one,
						value: null
					},

					startDateDatePicker: {
						value: null
					},

					summary: {
						getter: '_getSummary'
					}
				},

				NAME: 'recurrence-dialog',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.bindUI()
					},

					bindUI: function() {
						var instance = this;

						var container = instance.get('container');

						var limitByDateDatePicker = instance.get('limitByDateDatePicker');

						container.delegate('change', A.bind(instance._onInputChange, instance), 'select,input');
						container.delegate('keypress', A.bind(instance._onInputChange, instance), 'select');

						limitByDateDatePicker.after('selectionChange', A.bind(instance._onInputChange, instance));
					},

					_getDaysOfWeekCheckboxes: function() {
						var instance = this;

						var weeklyRecurrenceOptions = instance.get('weeklyRecurrenceOptions');

						return weeklyRecurrenceOptions.all(':checkbox');
					},

					_getLimitRadioButtons: function() {
						var instance = this;

						return [instance.get('limitByCountRadioButton'), instance.get('limitByDateRadioButton'), instance.get('noLimitRadioButton')];
					},

					_getLimitType: function() {
						var instance = this;

						var checkedLimitRadioButton = instance.get('limitRadioButtons').filter(
							function(item, index) {
								return item.get('checked');
							}
						)[0];

						if (checkedLimitRadioButton) {
							return checkedLimitRadioButton.val();
						}
					},

					_getSummary: function() {
						var instance = this;

						var dayOfWeekNodes = instance.get('daysOfWeekCheckboxes').filter(':checked');

						var dayOfWeekSelect = instance.get('dayOfWeekSelect');

						var frequency = instance.get('frequencySelect').val();

						var intervalNode = instance.get('intervalSelect');

						var limitByCountInput = instance.get('limitByCountInput');

						var limitByDateDatePicker = instance.get('limitByDateDatePicker');

						var limitType = instance.get('limitType');

						var positionSelect = instance.get('positionSelect');

						var startDateDatePicker = instance.get('startDateDatePicker');

						var selectedDaysOfWeek = [];

						if (dayOfWeekNodes.size()) {
							selectedDaysOfWeek = dayOfWeekNodes.getAttribute('data-weekday');
						}

						var positionalDayOfWeek = null;
						var repeatOnDayOfWeek = instance.get('repeatOnDayOfWeekRadioButton').get('checked');

						if ((frequency === FREQUENCY_MONTHLY) || (frequency === FREQUENCY_YEARLY)) {
							if (repeatOnDayOfWeek) {
								positionalDayOfWeek = {
									month: startDateDatePicker.getDate().getMonth(),
									position: positionSelect.val(),
									weekday: dayOfWeekSelect.val()
								};
							}
						}

						var recurrence = {
							count: limitByCountInput.val(),
							endValue: limitType,
							frequency: frequency,
							interval: intervalNode.val(),
							positionalWeekday: positionalDayOfWeek,
							untilDate: limitByDateDatePicker.getDate(),
							weekdays: selectedDaysOfWeek
						};

						return Liferay.RecurrenceUtil.getSummary(recurrence);
					},

					_onInputChange: function(event) {
						var instance = this;

						var target = event.target;

						var dayOfWeekCheckboxes = instance.get('dayOfWeekCheckboxes');

						var limitType = instance.get('limitType');

						var limitByCountInput = instance.get('limitByCountInput');

						var limitByDateDatePicker = instance.get('limitByDateDatePicker');

						if (target === instance.get('frequencySelect')) {
							instance._toggleView('weeklyRecurrenceOptions', target.val() === FREQUENCY_WEEKLY);
							instance._toggleView('monthlyRecurrenceOptions', (target.val() === FREQUENCY_MONTHLY) || (target.val() === FREQUENCY_YEARLY));
						}

						if (target === instance.get('repeatOnDayOfWeekRadioButton')) {
							instance._toggleView('positionalDayOfWeekOptions', target.val() === 'true');
						}

						if ((limitType === LIMIT_UNLIMITED) || (limitType === LIMIT_DATE)) {
							limitByCountInput.set('disabled', true);
						}
						else {
							limitByCountInput.set('disabled', false);
						}

						limitByCountInput.selectText();

						if ((limitType === LIMIT_UNLIMITED) || (limitType === LIMIT_COUNT)) {
							limitByDateDatePicker.set('disabled', true);
						}
						else {
							limitByDateDatePicker.set('disabled', false);
						}

						instance.fire('recurrenceChange');
					},

					_toggleView: function(viewName, show) {
						var instance = this;

						var viewNode = instance.get(viewName);

						if (viewNode) {
							viewNode.toggle(show);
						}
					}
				}
			}
		);

		Liferay.RecurrenceDialogController = RecurrenceDialogController;
	},
	'',
	{
		requires: ['aui-base', 'liferay-calendar-recurrence-util']
	}
);