AUI.add(
	'liferay-search-modified-facet',
	function(A) {
		var FacetUtil = Liferay.Search.FacetUtil;

		var ModifiedFacet = function(form) {
			var instance = this;

			instance.form = new ModifiedFacetForm(form);

			var customRangeSearchButton = instance.form.getCustomRangeSearchButton();

			customRangeSearchButton.on('click', A.bind(instance.filterByCustomRange, instance));
		};

		A.mix(
			ModifiedFacet.prototype,
			{
				filterByCustomRange: function() {
					var instance = this;

					var fromInputDatePicker = instance.form.getFromDatePicker();

					var toInputDatePicker = instance.form.getToDatePicker();

					var fromDate = fromInputDatePicker.getDate();

					var toDate = toInputDatePicker.getDate();

					var modifiedFromParameter = fromDate.toISOString().slice(0, 10);

					var modifiedToParameter = toDate.toISOString().slice(0, 10);

					var parameterArray = document.location.search.substr(1).split('&');

					var newParameters = FacetUtil.removeURLParameters('modified', parameterArray);

					newParameters = FacetUtil.removeURLParameters('modifiedFrom', newParameters);

					newParameters = FacetUtil.removeURLParameters('modifiedTo', newParameters);

					newParameters = FacetUtil.addURLParameter('modifiedFrom', modifiedFromParameter, newParameters);

					newParameters = FacetUtil.addURLParameter('modifiedTo', modifiedToParameter, newParameters);

					document.location.search = newParameters.join('&');
				}
			}
		);

		var ModifiedFacetForm = function(form) {
			var instance = this;

			instance.form = form;
		};

		A.mix(
			ModifiedFacetForm.prototype,
			{
				getCustomRangeSearchButton: function() {
					var instance = this;

					return instance.form.one('.modified-facet-custom-range-search-button');
				},

				getFromDatePicker: function() {
					var instance = this;

					return instance._getDatePicker('.modified-facet-from-date-picker');
				},

				getToDatePicker: function() {
					var instance = this;

					return instance._getDatePicker('.modified-facet-to-date-picker');
				},

				_getDatePicker: function(inputSelector) {
					var instance = this;

					var node = instance.form.one(inputSelector)

					var input = node.one('input');

					var name = input.get('name');

					return Liferay.component(name + 'DatePicker');

				}
			}
		);

		Liferay.namespace('Search').ModifiedFacet = ModifiedFacet;
	},
	'',
	{
		requires: ['liferay-search-facet-util']
	}
);