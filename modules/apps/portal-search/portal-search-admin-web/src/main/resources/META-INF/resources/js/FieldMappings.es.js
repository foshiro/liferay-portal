import 'clay-button';
import 'metal';
import 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import templates from './FieldMappings.soy';

class FieldMappings extends PortletBase {

	attached() {
		AUI().use(
			'aui-ace-editor',
			A => {
				const editor = new A.AceEditor({
					boundingBox: this.refs.wrapper,
					highlightActiveLine: false,
					mode: 'json',
					value: '{}',
					tabSize: 4
				}).render();
			}
		);
	}

	_selectText() {
		let copyTextArea = document.querySelector(".ace_text-input")
		copyTextArea.focus();
		copyTextArea.select();

		setTimeout(
			function() {
				document.execCommand("copy");
				Liferay.Portal.ToolTip.show(document.querySelector(".btn-copy"), 'Copied');
			},
			0
		);
	}
}

FieldMappings.STATE = {

};

Soy.register(FieldMappings, templates);

export {FieldMappings};
export default FieldMappings;