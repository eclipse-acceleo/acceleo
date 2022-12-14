/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.location.aql;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.emf.ecore.EObject;

/**
 * Locator service, to find the various usages (declaration, definition, references, etc.) of AQL elements.
 * 
 * @author Florent Latombe
 */
public class AqlLocator {

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The context qualified name.
	 */
	private String qualifiedName;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) {@link IQualifiedNameQueryEnvironment}.
	 * @param qualifiedName
	 *            the context qualified name
	 */
	public AqlLocator(IQualifiedNameQueryEnvironment queryEnvironment, String qualifiedName) {
		this.queryEnvironment = Objects.requireNonNull(queryEnvironment);
		this.qualifiedName = qualifiedName;
	}

	/**
	 * Provides the list of {@link AbstractAqlLocationLink} corresponding to the declaring location(s) of the
	 * AQL element found at the given position in the given source.
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) AQL {@link EObject AST element}.
	 * @param validationResult
	 *            the (non-{@code null}) {@link IValidationResult} containing {@code aqlAstElement}.
	 * @param aqlVariablesContext
	 *            the (non-{@code null}) {@link AqlVariablesLocalContext}.
	 * @return the {@link List} of {@link AbstractAqlLocationLink} corresponding to the declaration
	 *         location(s) of the element at the given position of the source.
	 */
	public List<AbstractLocationLink<?, ?>> getDeclarationLocations(EObject aqlAstElement,
			IValidationResult validationResult, AqlVariablesLocalContext aqlVariablesContext) {
		// FIXME: LSP4E does not support "go-to declaration" so we simply dispatch to "go-to definition".
		// Also, not sure we have a clear difference in Acceleo between declaration and definition.
		return this.getDefinitionLocations(aqlAstElement, validationResult, aqlVariablesContext);
	}

	/**
	 * Provides the list of {@link AbstractAqlLocationLink} corresponding to the definition location(s) of the
	 * given AQL AST element.
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) AQL {@link EObject AST element}.
	 * @param validationResult
	 *            the (non-{@code null}) {@link IValidationResult} containing {@code aqlAstElement}.
	 * @param aqlVariablesContext
	 *            the (non-{@code null}) {@link AqlVariablesLocalContext}.
	 * @return the {@link List} of {@link AbstractAqlLocationLink} corresponding to the definition location(s)
	 *         of the element at the given position of the source.
	 */
	public List<AbstractLocationLink<?, ?>> getDefinitionLocations(EObject aqlAstElement,
			IValidationResult validationResult, AqlVariablesLocalContext aqlVariablesContext) {
		final List<AbstractLocationLink<?, ?>> definitionLocations = new ArrayList<>();

		AqlDefinitionLocator definitionLocator = new AqlDefinitionLocator(this.queryEnvironment,
				validationResult, aqlVariablesContext, qualifiedName);

		List<AbstractLocationLink<?, ?>> definitionLocationsOfAqlElement = definitionLocator.doSwitch(
				aqlAstElement);
		if (definitionLocationsOfAqlElement != null) {
			definitionLocations.addAll(definitionLocationsOfAqlElement);
		}

		return definitionLocations;
	}
}
