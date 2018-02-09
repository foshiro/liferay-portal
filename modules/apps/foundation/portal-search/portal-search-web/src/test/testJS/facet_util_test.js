'use strict';

describe(
	'Liferay.Search.FacetUtil',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-search-facet-util',
					function(A) {
						done();
					}
				);
			}
		);

		describe(
			'.removeURLParameters()',
			function() {
				it(
					'should remove the parameter whose name is the given key.',
					function(done) {
						var parameterArray = [
							'modified=last-24-hours',
							'q=test'
						];

						var newParameters = Liferay.Search.FacetUtil.removeURLParameters('modified', parameterArray);

						assert.equal(newParameters.length, 1);
						assert.equal(newParameters[0], 'q=test');

						done();
					}
				);
			}
		);
	}
);