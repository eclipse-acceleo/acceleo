/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion.proposals;

import java.util.StringJoiner;

import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
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
	 * The {@link AcceleoAstSerializer}
	 */
	private final AcceleoAstSerializer accleeoSerializer = new AcceleoAstSerializer("");

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
		final StringBuilder res = new StringBuilder();

		res.append(service.getOrigin().getVisibility() + " " + service.getOrigin().getName());
		StringJoiner joiner = new StringJoiner(", ", "(", ")");
		for (Variable parameter : service.getOrigin().getParameters()) {
			joiner.add(accleeoSerializer.serialize(parameter));
		}
		res.append(joiner.toString());
		res.append(" = String");

		if (service.getOrigin().getDocumentation() != null) {
			res.append("\n");
			res.append(service.getOrigin().getDocumentation().getBody().getValue());
		}

		return res.toString();
	}

	@Override
	public IService<?> getObject() {
		return service;
	}

}
