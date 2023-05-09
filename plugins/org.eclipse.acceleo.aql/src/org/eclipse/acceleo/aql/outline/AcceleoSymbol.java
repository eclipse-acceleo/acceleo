/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.outline;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;

/**
 * Represents an element declared in an Acceleo {@link Module}.
 * 
 * @author Florent Latombe
 */
public class AcceleoSymbol {

	/**
	 * The "main" semantic element from the AST that this symbol represents.
	 */
	private final AcceleoASTNode semanticElement;

	/**
	 * The {@link IAcceleoValidationResult validation result} of the module the represented semantic element
	 * originates from. We need the whole module validation result because subsequent uses of this symbol may
	 * require validation informations related to other members of the AST accessible from the semantic
	 * element represented by this symbol.
	 */
	private final IAcceleoValidationResult acceleoValidationResult;

	/**
	 * The name of this symbol.
	 */
	private final String name;

	/**
	 * Additional details about this symbol.
	 */
	private final String details;

	/**
	 * The children of this symbol, usually to represent its properties or other symbols defined within its
	 * namespace.
	 */
	private final List<AcceleoSymbol> children = new ArrayList<>();

	/**
	 * Constructor for a non-deprecated {@link AcceleoSymbol}.
	 * 
	 * @param semanticElement
	 *            the (non-{@code null}) {@link AcceleoASTNode semantic element} from the AST which this
	 *            symbol represents.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult validation result} of the module from
	 *            which {@code semanticElement} originates.
	 * @param name
	 *            the (non-{@code null}) name of the symbol.
	 * @param details
	 *            the (maybe-{@code null}) additional details about the symbol.
	 */
	public AcceleoSymbol(AcceleoASTNode semanticElement, IAcceleoValidationResult acceleoValidationResult,
			String name, String details) {
		this.semanticElement = Objects.requireNonNull(semanticElement);
		this.acceleoValidationResult = Objects.requireNonNull(acceleoValidationResult);
		this.name = Objects.requireNonNull(name);
		this.details = details;
	}

	/**
	 * Provides the semantic element represented by this symbol.
	 * 
	 * @return the (non-{@code null}) {@link AcceleoASTNode} represented by this.
	 */
	public AcceleoASTNode getSemanticElement() {
		return semanticElement;
	}

	/**
	 * Provides the whole {@link IAcceleoValidationResult Acceleo validation result} of the document
	 * containing the semantic element represented by this symbol.
	 * 
	 * @return the (non-{@code null}) {@link IAcceleoValidationResult}.
	 */
	public IAcceleoValidationResult getAcceleoValidationResult() {
		return this.acceleoValidationResult;
	}

	/**
	 * Provides the name of this symbol.
	 * 
	 * @return the (non-{@code null}) name of this symbol.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Provides the optional details of this symbol.
	 * 
	 * @return a (maybe-{@code null}) user-friendly {@link String}.
	 */
	public String getDetails() {
		return this.details;
	}

	/**
	 * Provides the children of this symbol.
	 * 
	 * @return a (non-{@code null}) {@link List} of children {@link AcceleoSymbol}.
	 */
	public List<AcceleoSymbol> getChildren() {
		return this.children;
	}

}
