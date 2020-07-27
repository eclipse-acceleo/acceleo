/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion.proposals;

import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceCompletionProposal;

/**
 * {@link IServiceCompletionProposal} for {@link TemplateService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TemplateServiceCompletionProposal implements IServiceCompletionProposal {

	/**
	 * The {@link TemplateService}.
	 */
	private final TemplateService service;

	/**
	 * Constructor.
	 * 
	 * @param templateService
	 *            the {@link TemplateService}
	 */
	public TemplateServiceCompletionProposal(TemplateService templateService) {
		this.service = templateService;
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
			res = service.getOrigin().getDocumentation().getBody().getValue().replace("\n", "<br>");
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
