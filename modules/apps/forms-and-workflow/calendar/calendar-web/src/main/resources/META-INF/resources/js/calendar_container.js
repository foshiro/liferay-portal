AUI.add(
	'liferay-calendar-container',
	function(A) {
		var Lang = A.Lang;

		var isArray = Lang.isArray;
		var isObject = Lang.isObject;

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var CalendarContainer = A.Component.create(
			{
				ATTRS: {
					availableCalendars: {
						validator: isObject,
						value: {}
					},

					visibleCalendars: {
						validator: isObject,
						value: {}
					},

					defaultCalendar: {
						validator: isObject,
						value: null
					},
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'calendar-container',

				prototype: {
					createCalendarsAutoComplete: function(resourceURL, input, afterSelectFn) {
						var instance = this;

						input.plug(
							A.Plugin.AutoComplete,
							{
								activateFirstItem: true,
								after: {
									select: afterSelectFn
								},
								maxResults: 20,
								requestTemplate: '&' + instance.get('namespace') + 'keywords={query}',
								resultFilters: function(query, results) {
									return results.filter(
										function(item, index) {
											return !instance.getCalendar(item.raw.calendarId);
										}
									);
								},
								resultFormatter: function(query, results) {
									return results.map(
										function(result) {
											var calendar = result.raw;
											var calendarResourceName = calendar.calendarResourceName;
											var name = calendar.name;

											if (name !== calendarResourceName) {
												name = [calendarResourceName, STR_DASH, name].join(STR_SPACE);
											}

											return A.Highlight.words(name, query);
										}
									);
								},
								resultHighlighter: 'wordMatch',
								resultTextLocator: 'calendarResourceName',
								source: resourceURL,
								width: 'auto'
							}
						);

						input.ac.get('boundingBox').setStyle('min-width', input.outerWidth());
					},

					getCalendar: function(calendarId) {
						var instance = this;

						var availableCalendars = instance.get('availableCalendars');

						return availableCalendars[calendarId];
					},

					syncCalendarsMap: function(calendarLists) {
						var instance = this;

						var defaultCalendar = instance.get('defaultCalendar');

						var availableCalendars = {};

						var visibleCalendars = {};

						calendarLists.forEach(
							function(calendarList) {
								var calendars = calendarList.get('calendars');

								A.each(
									calendars,
									function(item, index) {
										var calendarId = item.get('calendarId');

										availableCalendars[calendarId] = item;

										if (item.get('visible')) {
											visibleCalendars[calendarId] = item;
										}

										if (item.get('defaultCalendar')) {
											var calendarResourceId = item.get('calendarResourceId');

											if (calendarResourceId == Liferay.CalendarUtil.GROUP_CALENDAR_RESOURCE_ID && item.get('permissions').MANAGE_BOOKINGS) {
												defaultCalendar = item;
											}
											else if (calendarResourceId == Liferay.CalendarUtil.USER_CALENDAR_RESOURCE_ID && defaultCalendar == null) {
												defaultCalendar = item;
											}
										}
									}
								);
							}
						);

						instance.set('availableCalendars', availableCalendars);
						instance.set('visibleCalendars', visibleCalendars);
						instance.set('defaultCalendar', defaultCalendar);

						return availableCalendars;
					}
				}
			}
		);

		Liferay.CalendarContainer = CalendarContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'liferay-portlet-base']
	}
);