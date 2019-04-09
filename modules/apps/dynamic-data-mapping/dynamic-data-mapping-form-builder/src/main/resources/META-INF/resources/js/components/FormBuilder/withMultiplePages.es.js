import '../SuccessPage/SuccessPagePaginationItem.soy.js';
import '../SuccessPage/SuccessPageRenderer.soy.js';
import '../SuccessPage/SuccessPageWizardItem.soy.js';
import Component from 'metal-jsx';
import {ClayActionsDropdown} from 'clay-dropdown';
import {Config} from 'metal-state';
import {setValue} from '../../util/i18n.es';

const withMultiplePages = ChildComponent => {
	class MultiplePages extends Component {
		static PROPS = {

			/**
			 * @instance
			 * @memberof MultiplePages
			 * @type {object}
			 */

			successPageSettings: Config.shapeOf(
				{
					body: Config.object(),
					enabled: Config.bool(),
					title: Config.object()
				}
			).value({})
		}

		static STATE = {

			/**
			 * @default false
			 * @instance
			 * @memberof FormRenderer
			 * @type {boolean}
			 */

			dropdownExpanded: Config.bool().value(false).internal()
		}

		_getPageSettingsItems() {
			const {
				pages,
				paginationMode,
				successPageSettings
			} = this.props;
			const pageSettingsItems = [
				{
					'label': Liferay.Language.get('add-new-page'),
					'settingsItem': 'add-page'
				}
			];

			if (this.getPages().length === 1) {
				pageSettingsItems.push(
					{
						'label': Liferay.Language.get('reset-page'),
						'settingsItem': 'reset-page'
					}
				);
			}
			else {
				pageSettingsItems.push(
					{
						'label': Liferay.Language.get('delete-current-page'),
						'settingsItem': 'delete-page'
					}
				);
			}

			if (!successPageSettings.enabled) {
				pageSettingsItems.push(
					{
						'label': Liferay.Language.get('add-success-page'),
						'settingsItem': 'add-success-page'
					}
				);
			}

			if (pages.length > 1) {
				let label = Liferay.Language.get('switch-pagination-to-top');

				if (paginationMode == 'wizard') {
					label = Liferay.Language.get('switch-pagination-to-bottom');
				}

				pageSettingsItems.push(
					{
						label,
						settingsItem: 'switch-pagination-mode'
					}
				);
			}

			return pageSettingsItems;
		}

		_handleExpandedChanged({newVal}) {
			this.setState(
				{
					dropdownExpanded: newVal
				}
			);
		}

		_addPage() {
			const {dispatch} = this.context;

			dispatch('pageAdded');
		}

		_addSuccessPage() {
			const {dispatch} = this.context;
			const {pages} = this.props;

			this._updateSuccessPage(
				{
					body: Liferay.Language.get('your-information-was-successfully-received-thanks-for-fill-out'),
					enabled: true,
					title: Liferay.Language.get('done')
				}
			);

			dispatch('activePageUpdated', pages.length);
		}

		_updateSuccessPage({body = '', title = '', enabled}) {
			const {dispatch} = this.context;
			const {editingLanguageId} = this.props;
			const successPageSettings = {
				body: {},
				enabled,
				title: {}
			};

			setValue(successPageSettings, editingLanguageId, 'body', body);
			setValue(successPageSettings, editingLanguageId, 'title', title);

			dispatch('successPageChanged', successPageSettings);
		}

		_deletePage() {
			const {dispatch} = this.context;

			dispatch('pageDeleted', this.props.activePage);
		}

		_deleteSuccessPage() {
			const {dispatch} = this.context;

			this._updateSuccessPage(
				{
					enabled: false
				}
			);

			dispatch('activePageUpdated', this.props.pages.length - 1);
		}

		_handleDropdownItemClicked({data}) {
			const {activePage, successPageSettings} = this.props;
			const {settingsItem} = data.item;

			this.setState(
				{
					dropdownExpanded: false
				}
			);

			if (settingsItem == 'add-page') {
				this._addPage();
			}
			else if (settingsItem === 'reset-page') {
				this._resetPage();
			}
			else if (settingsItem === 'delete-page') {
				if (successPageSettings.enabled && activePage == this.getPages().length - 1) {
					this._deleteSuccessPage();
				}
				else {
					this._deletePage();
				}
			}
			else if (settingsItem == 'switch-pagination-mode') {
				this._switchPaginationMode();
			}
			else if (settingsItem == 'add-success-page') {
				this._addSuccessPage();
			}
		}

		_switchPaginationMode() {
			const {dispatch} = this.context;

			dispatch('paginationModeUpdated');
		}

		_resetPage() {
			const {dispatch} = this.context;

			dispatch('pageReset');
		}

		isDropdownDisabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId !== editingLanguageId;
		}

		getPages() {
			let {pages} = this.props;
			const {successPageSettings} = this.props;

			if (successPageSettings.enabled) {
				pages = [
					...pages,
					{
						contentRenderer: 'success',
						paginationItemRenderer: 'success',
						rows: [],
						successPageSettings
					}
				];
			}

			return pages;
		}

		getPaginationPosition() {
			const {pages, paginationMode} = this.props;
			const position = paginationMode === 'wizard' ? 'top' : 'bottom';

			return pages.length > 1 ? position : 'top';
		}

		render() {
			const {dropdownExpanded} = this.state;
			const {spritemap} = this.props;

			return (
				<div class={`container ddm-paginated-builder ${this.getPaginationPosition()}`}>
					<ChildComponent
						{...this.props}
						pages={this.getPages()}
					/>

					<ClayActionsDropdown
						disabled={this.isDropdownDisabled()}
						elementClasses={'ddm-paginated-builder-dropdown'}
						events={{
							expandedChanged: this._handleExpandedChanged.bind(this),
							itemClicked: this._handleDropdownItemClicked.bind(this)
						}}
						expanded={dropdownExpanded}
						items={this._getPageSettingsItems()}
						spritemap={spritemap}
					/>
				</div>
			);
		}
	}

	return MultiplePages;
};

export default withMultiplePages;