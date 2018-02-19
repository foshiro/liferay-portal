AUI.add(
	'liferay-search-bar',
	function(A) {
		var SearchBar = function(form, input) {
			var instance = this;

			instance.form = form;
			instance.input = input
		};

		SearchBar.prototype.search = function() {
			var instance = this;

			var keywords = instance.input.val();

			keywords = keywords.replace(/^\s+|\s+$/, '');

			if (keywords != '') {
				submitForm(instance.form);
			}
		};

		Liferay.namespace('Search').SearchBar = SearchBar;
	},
	'',
	{
		requires: []
	}
);