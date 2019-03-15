import CreatableSelect from 'react-select/lib/Creatable';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

const components = {
	DropdownIndicator: null
};

const createOption = label => ({
	label,
	value: label
});

class ClayMultiselect extends Component {
	static propTypes = {
		onAction: PropTypes.func,
		value: PropTypes.arrayOf(String)
	};

	state = {
		inputValue: '',
		value: this.props.value
	};

	_handleChange = value => {
		this.props.onAction(value);
	};

	_handleInputChange = inputValue => {
		this.setState({inputValue});
	};

	_handleKeyDown = event => {
		const {value} = this.props;

		const {inputValue} = this.state;

		if (!inputValue) {
			return;
		}

		switch (event.key) {
		case 'Enter':
		case 'Tab':
		case ',':
			if (!value.map(item => item.value).includes(inputValue)) {
				this.props.onAction([...value, createOption(inputValue)]);
			}

			this.setState({inputValue: ''});

			event.preventDefault();

			break;
		default:
		}
	};

	render() {
		const {value} = this.props;
		const {inputValue} = this.state;

		return (
			<CreatableSelect
				className="multiselect-root"
				classNamePrefix="react-select"
				components={components}
				inputValue={inputValue}
				isClearable
				isMulti
				menuIsOpen={false}
				onChange={this._handleChange}
				onInputChange={this._handleInputChange}
				onKeyDown={this._handleKeyDown}
				placeholder=""
				value={value}
			/>
		);
	}
}

export default ClayMultiselect;