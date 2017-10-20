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

package com.liferay.calendar.rest.internal.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.vulcan.provider.Provider;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Allows resources to provide {@link Company} as a parameter in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
@Component(immediate = true)
public class DisplayTimeZoneProvider implements Provider<TimeZone> {

	@Override
	public TimeZone createContext(HttpServletRequest request) {
		try {
			User user = _userService.getCurrentUser();

			TimeZone timeZone = user.getTimeZone();

			if (timeZone == null) {
				Company company = _portal.getCompany(request);

				timeZone = company.getTimeZone();
			}

			return timeZone;
			
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private UserService _userService;

	@Reference
	private Portal _portal;
	
}