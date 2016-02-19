;(function() {
	var PATH_CALENDAR_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/calendar-web';

	AUI().applyConfig(
		{
			groups: {
				components: {
					base: PATH_CALENDAR_WEB + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-calendar-interval-selector-scheduler-event-link': {
							path: 'js/interval_selector_scheduler_event_link.js',
							requires: ['aui-base']
						},
						'liferay-calendar-interval-selector': {
							path: 'js/interval_selector.js',
							requires: ['aui-base', 'liferay-portlet-base']
						},
						'liferay-calendar-recurrence-converter': {
							path: 'js/recurrence_converter.js',
							requires: []
						},
						'liferay-calendar-recurrence-dialog': {
							path: 'js/recurrence.js',
							requires: [
								'aui-base',
								'liferay-calendar-recurrence-util'
							]
						}
					},
					root: PATH_CALENDAR_WEB + '/'
				}
			}
		}
	);
})();