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
package org.eclipse.acceleo.aql.location;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.aql.location.aql.AqlLocator;
import org.eclipse.acceleo.aql.location.aql.AqlVariablesLocalContext;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoAstUtils;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.emf.ecore.EObject;

/**
 * Locator service, to find the various usages (declaration, definition, references, etc.) of AST elements in
 * the Acceleo sources.
 * 
 * @author Florent Latombe
 */
public class AcceleoLocator {

	/**
	 * The {@link IQualifiedNameQueryEnvironment} of the Acceleo contents.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link AqlLocator} we delegate to in case we are inside an AQL expression.
	 */
	private final AqlLocator aqlLocator;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private IAcceleoValidationResult acceleoValidationResult;

	/**
	 * Creates a new {@link AcceleoLocator}.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) {@link IQualifiedNameQueryEnvironment} of the Acceleo contents.
	 * @param qualifiedName
	 *            the context qualified name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 */
	public AcceleoLocator(IQualifiedNameQueryEnvironment queryEnvironment, String qualifiedName,
			IAcceleoValidationResult acceleoValidationResult) {
		this.queryEnvironment = queryEnvironment;
		this.acceleoValidationResult = acceleoValidationResult;

		this.aqlLocator = new AqlLocator(queryEnvironment, qualifiedName);
	}

	/**
	 * Provides the list of {@link AbstractAcceleoLocationLink} corresponding to the declaring location(s) of
	 * the Acceleo element found at the given position in the given source.
	 * 
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @param position
	 *            the (non-{@code null}) caret position in the source.
	 * @return the {@link List} of {@link AbstractAcceleoLocationLink} corresponding to the declaration
	 *         location(s) of the element at the given position of the source.
	 */
	public List<AbstractLocationLink<?, ?>> getDeclarationLocations(AcceleoAstResult acceleoAstResult,
			int position) {
		// FIXME: LSP4E does not support "go-to declaration" so we simply dispatch to "go-to definition".
		// Also, not sure we have a clear difference in Acceleo between declaration and definition.
		return this.getDefinitionLocations(acceleoAstResult, position);
	}

	/**
	 * Provides the list of {@link AbstractAcceleoLocationLink} corresponding to the definition location(s) of
	 * the Acceleo element found at the given position in the given source.
	 * 
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @param position
	 *            the (non-{@code null}) caret position in the source.
	 * @return the {@link List} of {@link AbstractAcceleoLocationLink} corresponding to the definition
	 *         location(s) of the element at the given position of the source.
	 */
	public List<AbstractLocationLink<?, ?>> getDefinitionLocations(AcceleoAstResult acceleoAstResult,
			int position) {
		final List<AbstractLocationLink<?, ?>> definitionLocations = new ArrayList<>();

		// Determine whether under the cursor location there is an element for which we are able to provide
		// definition location(s).
		EObject acceleoOrAqlNodeUnderCursor = acceleoAstResult.getAstNode(position);
		if (acceleoOrAqlNodeUnderCursor != null) {
			if (acceleoOrAqlNodeUnderCursor instanceof ASTNode) {
				ASTNode astNodeUnderCursor = (ASTNode)acceleoOrAqlNodeUnderCursor;
				// The element under the cursor may actually be linked to another element: "bigger", for
				// instance if on an expression we actually want to designate the variable being defined, or
				// "smaller" for instance if we are on part of an AQL expression, we actually want to
				// designate one of the terms of the expression.
				ASTNode elementWhoseDefinitionToLocate = new AcceleoDefinedElementAssociator().doSwitch(
						astNodeUnderCursor);
				definitionLocations.addAll(getDefinitionLocations(position, elementWhoseDefinitionToLocate));
			} else if (acceleoOrAqlNodeUnderCursor instanceof org.eclipse.acceleo.query.ast.Expression
					|| acceleoOrAqlNodeUnderCursor instanceof org.eclipse.acceleo.query.ast.VariableDeclaration) {
				// The cursor was on an AQL expression term.
				// We will delegate to an AQL locator, but we also need to determine where in the Acceleo AST
				// the AQL expression is so we can provide the right context variables.
				AqlVariablesLocalContext acceleoLocalVariablesContext = getVariablesContext(
						acceleoOrAqlNodeUnderCursor);
				AstResult astResultOfAqlNode = AcceleoAstUtils.getAqlAstResultOfAqlAstElement(
						acceleoOrAqlNodeUnderCursor);
				IValidationResult validationResult = acceleoValidationResult.getValidationResult(
						astResultOfAqlNode);
				definitionLocations.addAll(this.aqlLocator.getDefinitionLocations(acceleoOrAqlNodeUnderCursor,
						validationResult, acceleoLocalVariablesContext));
			}
		}
		return definitionLocations;
	}

	/**
	 * Provides the {@link AqlVariablesLocalContext} of variables used to partially evaluate an AQL AST
	 * element, depending on where in the Acceleo AST it is located.
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) AQL AST element.
	 * @return the {@link AqlVariablesLocalContext}.
	 */
	private AqlVariablesLocalContext getVariablesContext(EObject aqlAstElement) {
		ASTNode acceleoContainerOfAqlElement = AcceleoAstUtils.getContainerOfAqlAstElement(aqlAstElement);
		AqlVariablesLocalContext context = new AcceleoExpressionVariablesContextProvider(
				acceleoValidationResult).doSwitch(acceleoContainerOfAqlElement);
		return context;
	}

	private List<AbstractLocationLink<?, ?>> getDefinitionLocations(int position,
			ASTNode elementWhoseDefinitionToLocate) {
		final List<AbstractLocationLink<?, ?>> definitionLocations = new ArrayList<>();

		// The definition locator needs the environment for resolving references to out-of-file elements, and
		// the position so it can delegate to the AQL locator if we are inside an expression.
		AcceleoDefinitionLocator definitionLocator = new AcceleoDefinitionLocator(queryEnvironment);

		// Retrieve the links from our element to its definition location(s).
		List<AbstractLocationLink<?, ?>> linksToDefinitionLocations = definitionLocator.doSwitch(
				(ASTNode)elementWhoseDefinitionToLocate);

		if (linksToDefinitionLocations != null) {
			definitionLocations.addAll(linksToDefinitionLocations);
		}

		return definitionLocations;
	}

}
