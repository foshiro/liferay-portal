/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {useRef, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import ClayModal from '@clayui/modal';
import ClayButton from '@clayui/button';
import ClayAlert from '@clayui/alert';
import getCN from 'classnames';

function SegmentsExperimentsModal({
	onClose,
	active,
	error,
	onSave,
	name = '',
	description = '',
	segmentsExperienceId,
	segmentsExperimentId
}) {
	const [inputDescription, setInputDescription] = useState(description);
	const [inputName, setInputName] = useState(name);
	const [nameError, setNameError] = useState(false);
	const inputRef = useRef();

	useEffect(() => {
		if (active && inputRef.current) inputRef.current.focus();
	}, [active]);

	useEffect(() => {
		setInputName(name);
	}, [name]);

	useEffect(() => {
		setInputDescription(description);
	}, [description]);

	const nameFormGroupClasses = getCN('form-group', {
		'has-error': nameError
	});

	return active ? (
		<ClayModal onClose={_handleModalClose}>
			{onClose => {
				return (
					<React.Fragment>
						<ClayModal.Header>
							{Liferay.Language.get('create-new-test')}
						</ClayModal.Header>
						<ClayModal.Body>
							{error && (
								<ClayAlert
									displayType="danger"
									title={Liferay.Language.get('error')}
								>
									{error}
								</ClayAlert>
							)}
							<div className={nameFormGroupClasses}>
								<label>
									{Liferay.Language.get('test-name')}
									<span aria-hidden="true">*</span>
								</label>
								<input
									className="form-control"
									onBlur={_handleNameInputBlur}
									onChange={_handleNameChange}
									onFocus={_handleNameInputFocus}
									ref={inputRef}
									value={inputName}
								/>
								{nameError && (
									<div className="form-feedback-group">
										<div className="form-feedback-item">
											{Liferay.Language.get(
												'experiment-name-is-required'
											)}
										</div>
									</div>
								)}
							</div>
							<div className="form-group">
								<label>
									{Liferay.Language.get('description')}
								</label>
								<textarea
									className="form-control"
									onChange={_handleDescriptionChange}
									placeholder={Liferay.Language.get(
										'description-placeholder'
									)}
									value={inputDescription}
								/>
							</div>
						</ClayModal.Body>
						<ClayModal.Footer
							last={
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={onClose}
									>
										{Liferay.Language.get('cancel')}
									</ClayButton>
									<ClayButton
										displayType="primary"
										onClick={_handleSave}
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								</ClayButton.Group>
							}
						/>
					</React.Fragment>
				);
			}}
		</ClayModal>
	) : null;

	function _handleNameChange(event) {
		setInputName(event.target.value);
	}

	function _handleDescriptionChange(event) {
		setInputDescription(event.target.value);
	}

	function _handleNameInputBlur() {
		if (!inputName) setNameError(true);
	}

	function _handleNameInputFocus() {
		setNameError(false);
	}

	/**
	 * Triggers `onTestCreation` and closes the modal
	 */
	function _handleSave() {
		onSave({
			name: inputName,
			description: inputDescription,
			segmentsExperienceId,
			segmentsExperimentId
		});
		_handleModalClose();
	}

	/**
	 * Resets modal values and triggers `onClose`
	 */
	function _handleModalClose() {
		setInputDescription('');
		setInputName('');
		onClose();
	}
}

SegmentsExperimentsModal.propTypes = {
	active: PropTypes.bool.isRequired,
	error: PropTypes.string,
	description: PropTypes.string,
	segmentsExperienceId: PropTypes.string,
	segmentsExperimentId: PropTypes.string,
	name: PropTypes.string,
	onClose: PropTypes.func.isRequired,
	onSave: PropTypes.func.isRequired
};

export default SegmentsExperimentsModal;
