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
package org.eclipse.acceleo.aql.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * An {@link AcceleoSwitch} that produces an {@link AbstractLocationLink} that points from the designated
 * element (passed as argument) to its definition location.
 * 
 * @author Florent Latombe
 */
public class AcceleoDefinitionLocator extends AcceleoSwitch<List<AbstractLocationLink<?, ?>>> {

	/**
	 * The {@link IAcceleoEnvironment} in which this locator searches for definitions.
	 */
	private final IAcceleoEnvironment acceleoEnvironment;

	/**
	 * The {@link IQualifiedNameLookupEngine}.
	 */
	private IQualifiedNameLookupEngine lookupEngine;

	/**
	 * Constructor.
	 * 
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment} of the {@link Module} to which the
	 *            argument {@link ASTNode} belongs.
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 */
	public AcceleoDefinitionLocator(IAcceleoEnvironment acceleoEnvironment,
			IQualifiedNameLookupEngine lookupEngine) {
		this.acceleoEnvironment = acceleoEnvironment;
		this.lookupEngine = lookupEngine;
	}

	// Simple cases where the argument element is its own definition.
	/**
	 * A {@link Variable} is its own definition statement.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseVariable(org.eclipse.acceleo.Variable)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseVariable(Variable variable) {
		return Collections.singletonList(new AcceleoLocationLinkToAcceleo(variable, variable));
	}

	/**
	 * A {@link Query} is its own definition statement.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseQuery(org.eclipse.acceleo.Query)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseQuery(Query query) {
		return Collections.singletonList(new AcceleoLocationLinkToAcceleo(query, query));
	}

	/**
	 * A {@link Module} is its own definition statement.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseModule(org.eclipse.acceleo.Module)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseModule(Module module) {
		return Collections.singletonList(new AcceleoLocationLinkToAcceleo(module, module));
	}
	////

	/**
	 * For an {@link Import}, link to the referenced module's definition.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseImport(org.eclipse.acceleo.Import)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseImport(Import importStatement) {
		return this.doSwitch(importStatement.getModule());
	}

	/**
	 * For a {@link ModuleReference}, link to the definition of the resolved {@link Module}.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseModuleReference(org.eclipse.acceleo.ModuleReference)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseModuleReference(ModuleReference moduleReference) {
		final Object resolved = lookupEngine.getResolver().resolve(moduleReference.getQualifiedName());
		if (resolved instanceof Module) {
			return Collections.singletonList(new AcceleoLocationLinkToAcceleo(moduleReference,
					(Module)resolved));
		} else {
			// Could not resolve the module reference, which means that we will not be able to find the
			// definition of the referenced module.
			return null;
		}
	}

	@Override
	public List<AbstractLocationLink<?, ?>> caseParameterDocumentation(
			ParameterDocumentation parameterDocumentation) {
		List<AbstractLocationLink<?, ?>> acceleoLocationLinks = new ArrayList<>();

		String parameterDocumentationBodyValue = parameterDocumentation.getBody().getValue();
		String nameOfDocumentedParameter = parameterDocumentationBodyValue.substring(0,
				parameterDocumentationBodyValue.indexOf(' '));

		// Safe cast because the only containment reference to a {@link ParameterDocumentation} is from {@link
		// ModuleElementDocumentation}.
		ModuleElementDocumentation parent = (ModuleElementDocumentation)parameterDocumentation.eContainer();
		DocumentedElement documentedElement = parent.getDocumentedElement();

		// We want to return a link that points to the parameter of the name specified in the {@link
		// ParameterDocumentation} element.
		if (documentedElement instanceof Query) {
			Query documentedQuery = (Query)documentedElement;
			List<AcceleoLocationLinkToAcceleo> linksToQueryParametersWithMatchingName = documentedQuery
					.getParameters().stream().filter(parameter -> parameter.getName().equals(
							nameOfDocumentedParameter)).map(
									queryParameter -> new AcceleoLocationLinkToAcceleo(parameterDocumentation,
											queryParameter)).collect(Collectors.toList());
			acceleoLocationLinks.addAll(linksToQueryParametersWithMatchingName);
		} else if (documentedElement instanceof Template) {
			Template documentedTemplate = (Template)documentedElement;
			List<AcceleoLocationLinkToAcceleo> linksToTemplateParametersWithMatchingName = documentedTemplate
					.getParameters().stream().filter(parameter -> parameter.getName().equals(
							nameOfDocumentedParameter)).map(
									templateParameter -> new AcceleoLocationLinkToAcceleo(
											parameterDocumentation, templateParameter)).collect(Collectors
													.toList());
			acceleoLocationLinks.addAll(linksToTemplateParametersWithMatchingName);
		} else {
			// This should never happen as parameter documentations should only exist for elements that have
			// parameters, i.e. queries and templates.
		}
		return acceleoLocationLinks;
	}

	/**
	 * This case should never happen, because we expect the Acceleo parser to never provide an Acceleo
	 * expression on which we will switch to search for its definition locations, but instead it should
	 * provide the corresponding underlying AQL expression term.
	 * 
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseExpression(org.eclipse.acceleo.Expression)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseExpression(Expression expression) {
		throw new IllegalArgumentException(
				"No definition location can be located for an Acceleo expresssion.");
	}

	// TODO: implement more cases

}
