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

					daysOfWeek: {
						getter: '_getDaysOfWeek'
					},

					weeklyRecurrenceOptions: {
						setter: A.one,
						value: null
					},

					frequencySelect: {
						setter: A.one,
						value: null
					},

					frequency: {
						getter: '_getFrequency'
					},

					intervalSelect: {
						setter: A.one,
						value: null
					},

					interval: {
						getter: '_getInterval'
					},

					limitCount: {
						getter: '_getLimitCount'
					},

					limitCountInput: {
						setter: A.one,
						value: null
					},

					limitCountRadioButton: {
						setter: A.one,
						value: null
					},

					limitDate: {
						getter: '_getLimitDate'
					},

					limitDateDatePicker: {
						value: null
					},

					limitDateRadioButton: {
						setter: A.one,
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

					positionalDayOfWeek: {
						getter: '_getPositionalDayOfWeek'
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

					recurrence: {
						getter: '_getRecurrence'
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

						var limitDateDatePicker = instance.get('limitDateDatePicker');

						container.delegate('change', A.bind(instance._onInputChange, instance), 'select,input');
						container.delegate('keypress', A.bind(instance._onInputChange, instance), 'select');

						limitDateDatePicker.after('selectionChange', A.bind(instance._onInputChange, instance));
					},

					_getDaysOfWeek: function() {
						var instance = this;

						var dayOfWeekNodes = instance.get('daysOfWeekCheckboxes').filter(':checked');

						var daysOfWeek = [];

						if (dayOfWeekNodes.size()) {
							daysOfWeek = dayOfWeekNodes.getAttribute('data-weekday');
						}

						return daysOfWeek;
					},

					_getDaysOfWeekCheckboxes: function() {
						var instance = this;

						var weeklyRecurrenceOptions = instance.get('weeklyRecurrenceOptions');

						return weeklyRecurrenceOptions.all(':checkbox');
					},

					_getFrequency: function() {
						var instance = this;

						var frequencySelect = instance.get('frequencySelect');

						return frequencySelect.val();
					},

					_getInterval: function() {
						var instance = this;

						var intervalSelect = instance.get('intervalSelect');

						return intervalSelect.val();
					},

					_getLimitCount: function() {
						var limitCountInput = instance.get('limitCountInput');

						return parseInt(limitCountInput.val(), 10);
					},

					_getLimitDate: function() {
						var limitDateDatePicker = instance.get('limitDateDatePicker');

						return limitDateDatePicker.getDate();
					},

					_getLimitRadioButtons: function() {
						var instance = this;

						return [instance.get('limitCountRadioButton'), instance.get('limitDateRadioButton'), instance.get('noLimitRadioButton')];
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

					_getPositionalDayOfWeek: function() {
						var instance = this;

						var dayOfWeekSelect = instance.get('dayOfWeekSelect');

						var frequency = instance.get('frequency');

						var positionSelect = instance.get('positionSelect');

						var positionalDayOfWeek = null;

						var repeatOnDayOfWeek = instance.get('repeatOnDayOfWeekRadioButton').get('checked');

						var startDateDatePicker = instance.get('startDateDatePicker');

						if ((frequency === FREQUENCY_MONTHLY) || (frequency === FREQUENCY_YEARLY)) {
							if (repeatOnDayOfWeek) {
								positionalDayOfWeek = {
									month: startDateDatePicker.getDate().getMonth(),
									position: positionSelect.val(),
									weekday: dayOfWeekSelect.val()
								};
							}
						}

						return positionalDayOfWeek;
					},

					_getRecurrence: function() {
						var instance = this;

						return {
							count: instance.get('limitCount'),
							endValue: instance.get('limitType'),
							frequency: instance.get('frequency'),
							interval: instance.get('interval'),
							positionalWeekday: instance.get('positionalDayOfWeek'),
							untilDate: instance.get('limitDate'),
							weekdays: instance.get('daysOfWeek')
						};
					},

					_getSummary: function() {
						var instance = this;

						var recurrence = instance.get('recurrence');

						return Liferay.RecurrenceUtil.getSummary(recurrence);
					},

					_onInputChange: function(event) {
						var instance = this;

						var target = event.target;

						var dayOfWeekCheckboxes = instance.get('dayOfWeekCheckboxes');

						var limitType = instance.get('limitType');

						var limitCountInput = instance.get('limitCountInput');

						var limitDateDatePicker = instance.get('limitDateDatePicker');

						if (target === instance.get('frequencySelect')) {
							instance._toggleView('weeklyRecurrenceOptions', target.val() === FREQUENCY_WEEKLY);
							instance._toggleView('monthlyRecurrenceOptions', (target.val() === FREQUENCY_MONTHLY) || (target.val() === FREQUENCY_YEARLY));
						}

						if (target === instance.get('repeatOnDayOfWeekRadioButton')) {
							instance._toggleView('positionalDayOfWeekOptions', target.val() === 'true');
						}

						if ((limitType === LIMIT_UNLIMITED) || (limitType === LIMIT_DATE)) {
							limitCountInput.set('disabled', true);
						}
						else {
							limitCountInput.set('disabled', false);
						}

						limitCountInput.selectText();

						if ((limitType === LIMIT_UNLIMITED) || (limitType === LIMIT_COUNT)) {
							limitDateDatePicker.set('disabled', true);
						}
						else {
							limitDateDatePicker.set('disabled', false);
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