AUI.add(
	'liferay-calendar-interval-selector-scheduler-event-link',
	function(A) {
		var AArray = A.Array;

		var IntervalSelectorSchedulerEventLink = A.Component.create(
			{

				ATTRS: {
					intervalSelector: {
						value: null
					},

					schedulerEvent: {
						value: null
					}
				},

				NAME: 'interval-selector-scheduler-event-link',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.eventHandlers = [];

						instance.bindUI()
					},

					bindUI: function() {
						var instance = this;

						instance._attachEventHandlers();
					},

					destructor: function() {
						var instance = this;

						instance._detachEventHandlers();
					},

					_updateIntervalSelector: function() {
						var instance = this;

						if (!event.preventIntervalSelectorUpdade) {
							var intervalSelector = instance.get('intervalSelector');

							var schedulerEvent = instance.get('schedulerEvent');

							var startDate = schedulerEvent.get('startDate');

							var startDatePicker = intervalSelector.get('startDatePicker');

							var startTimePicker = intervalSelector.get('startTimePicker');

							intervalSelector.stopDurationPreservation();

							startDatePicker.deselectDates();
							startDatePicker.selectDates([startDate]);
							startTimePicker.selectDates([startDate]);

							var endDate = schedulerEvent.get('endDate');

							var endDatePicker = intervalSelector.get('endDatePicker');

							var endTimePicker = intervalSelector.get('endTimePicker');

							endDatePicker.deselectDates();
							endDatePicker.selectDates([endDate]);
							endTimePicker.selectDates([endDate]);

							intervalSelector.startDurationPreservation()
						}
					},

					_updateSchedulerEvent: function(event) {
						var instance = this;

						var schedulerEvent = instance.get('schedulerEvent');

						var scheduler = schedulerEvent.get('scheduler');

						instance._detachEventHandlers();

						schedulerEvent.setAttrs(
							{
								endDate: event.endDate,
								startDate: event.startDate
							},
							{
								preventIntervalSelectorUpdate: true
							}
						);

						scheduler.syncEventsUI();

						instance._attachEventHandlers();
					},

					_attachEventHandlers: function() {
						var instance = this;

						var intervalSelector = instance.get('intervalSelector');

						var schedulerEvent = instance.get('schedulerEvent');

						instance.eventHandlers.push(
							schedulerEvent.after('startDateChange', A.bind('_updateIntervalSelector', instance)),
							schedulerEvent.after('endDateChange', A.bind('_updateIntervalSelector', instance)),
							intervalSelector.after('intervalChange', A.bind('_updateSchedulerEvent', instance))
						);
					},

					_detachEventHandlers: function() {
						var instance = this;

						AArray.invoke(instance.eventHandlers, 'detach');

						instance.eventHandlers = [];
					}
				}
			}

		);

		Liferay.IntervalSelectorSchedulerEventLink = IntervalSelectorSchedulerEventLink;
	},
	'',
	{
		requires: ['aui-base']
	}
);