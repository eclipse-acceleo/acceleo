/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion.proposals;

import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceCompletionProposal;
import org.eclipse.acceleo.query.services.StringServices;

/**
 * {@link IServiceCompletionProposal} for {@link QueryService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryServiceCompletionProposal implements IServiceCompletionProposal {

	/**
	 * The {@link QueryService}.
	 */
	private final QueryService service;

	/**
	 * Constructor.
	 * 
	 * @param queryService
	 *            the {@link QueryService}
	 */
	public QueryServiceCompletionProposal(QueryService queryService) {
		this.service = queryService;
	}

	@Override
	public String getProposal() {
		return service.getName() + "()";
	}

	@Override
	public int getCursorOffset() {
		final int namelength = service.getName().length();
		if (service.getNumberOfParameters() == 1) {
			/*
			 * if we have only one parameter we return the offset: self.serviceCall()^
			 */
			return namelength + 2;
		} else {
			/*
			 * if we more than one parameter we return the offset: self.serviceCall(^)
			 */
			return namelength + 1;
		}
	}

	@Override
	public String getDescription() {
		final String res;

		if (service.getOrigin().getDocumentation() != null) {
			res = StringServices.NEW_LINE_PATTERN.matcher(service.getOrigin().getDocumentation().getBody()
					.getValue()).replaceAll("<br>");
		} else {
			res = "";
		}

		return res;
	}

	@Override
	public IService<?> getObject() {
		return service;
	}

}
