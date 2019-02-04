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

package com.liferay.headless.web.experience.internal.resource;

import com.liferay.headless.web.experience.dto.Comment;
import com.liferay.headless.web.experience.resource.CommentResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseCommentResourceImpl implements CommentResource {

	@Override
	public Comment getComment(Long id) throws Exception {
		return new Comment();
	}

	@Override
	public Page<Comment> getCommentCommentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page<>(Collections.emptyList(), 0);
	}

	@Override
	public Page<Comment> getContentDocumentCommentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page<>(Collections.emptyList(), 0);
	}

	@Override
	public Page<Comment> getStructuredContentsCommentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page<>(Collections.emptyList(), 0);
	}

	protected <T, R> List<R> transform(
		List<T> list, Function<T, R> transformFunction) {

		return TransformUtil.transform(list, transformFunction);
	}

}