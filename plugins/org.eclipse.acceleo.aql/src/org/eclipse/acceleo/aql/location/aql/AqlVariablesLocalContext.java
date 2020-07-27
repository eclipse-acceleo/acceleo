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
package org.eclipse.acceleo.aql.location.aql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Captures all the context necessary for a partial evaluation of an AQL expression for the
 * {@link AqlLocator}.
 * 
 * @author Florent Latombe
 */
public class AqlVariablesLocalContext {
	/**
	 * The {@link Map} of the variable definitions (i.e. the AST element that defines them).
	 */
	private final Map<String, Object> variableDefinitions;

	/**
	 * The {@link Map} of the variable types.
	 */
	private final Map<String, Set<IType>> variableTypes;

	/**
	 * Constructor that creates a new empty {@link AqlVariablesLocalContext}.
	 */
	public AqlVariablesLocalContext() {
		this.variableDefinitions = new HashMap<>();
		this.variableTypes = new HashMap<>();
	}

	/**
	 * Adds a variable to this context.
	 * 
	 * @param variableName
	 *            the (non-{@code null}) name of the variable.
	 * @param variableDefinition
	 *            the (non-{@code null}) definition of the variable, that is, the AST element that defines
	 *            them.
	 * @param variablePossibleTypes
	 *            the (non-{@code null}) {@link Set} of {@link IType} for the variable.
	 * @return this context.
	 */
	public AqlVariablesLocalContext addVariable(String variableName, Object variableDefinition,
			Set<IType> variablePossibleTypes) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(variableDefinition);
		Objects.requireNonNull(variablePossibleTypes);

		this.variableDefinitions.put(variableName, variableDefinition);
		this.variableTypes.put(variableName, variablePossibleTypes);
		return this;
	}

	/**
	 * Merges another {@link AqlVariablesLocalContext} into this.
	 * 
	 * @param contextToAdd
	 *            the (non-{@code null}) {@link AqlVariablesLocalContext} to merge.
	 * @return this {@link AqlVariablesLocalContext}.
	 */
	public AqlVariablesLocalContext addAllVariables(AqlVariablesLocalContext contextToAdd) {
		Objects.requireNonNull(contextToAdd);

		for (String variableNameToAdd : contextToAdd.getVariableNames()) {
			Object variableDefinitionToAdd = contextToAdd.getVariableDefinitions().get(variableNameToAdd);
			Set<IType> variableTypesToAdd = contextToAdd.getVariableTypes().get(variableNameToAdd);
			this.addVariable(variableNameToAdd, variableDefinitionToAdd, variableTypesToAdd);
		}

		return this;
	}

	/**
	 * Provides the names of all the variables of this context.
	 * 
	 * @return the non-{@code null}) {@link Set} of all the variable names of this.
	 */
	public Set<String> getVariableNames() {
		return this.variableDefinitions.keySet();
	}

	/**
	 * Provides the variable definitions contained by this context.
	 * 
	 * @return the read-only {@link Map} from the variable names to their definition.
	 */
	public Map<String, Object> getVariableDefinitions() {
		return Collections.unmodifiableMap(this.variableDefinitions);
	}

	/**
	 * Provides the variable types contained by this contexct.
	 * 
	 * @return the read-only {@link Map} from the variable names to their possible {@link IType types}.
	 */
	public Map<String, Set<IType>> getVariableTypes() {
		return Collections.unmodifiableMap(this.variableTypes);
	}
}
