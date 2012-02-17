/*****************************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *****************************************************************************************/
package org.eclipse.acceleo.parser.interpreter;

import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

/**
 * This represents the context of a compilation as required by Acceleo.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public class CompilationContext {
	/** The actual expression as written by the user that we are trying to compile. */
	private final String expression;

	/** This represents the type of the target EObject for which we are compiling. */
	private final String targetType;

	/**
	 * The map of variables that are to be taken into account while compiling this expression. Keys represent
	 * the variable names while values represent the variable's type.
	 */
	private final Map<String, String> variables;

	/**
	 * If we need additional dependencies to compile this expression, this should hold them. Keys represent
	 * the OCL qualified name of the dependency, values hold all potential URIs of the dependency (one
	 * qualified name may link to multiple resources.
	 */
	private final Multimap<String, URI> dependencies;

	/** This will contain the dependencies that need to be built before the current module, if any. */
	private final Set<ModuleDescriptor> extendedDependencies;

	/** The set of all metamodel URIs visible to the compiled expression. */
	private final Set<String> nsURIs;

	/**
	 * Instantiates a compilation context.
	 * 
	 * @param expression
	 *            The actual expression we are trying to compile.
	 * @param targetType
	 *            The type of the target EObject for this compilation.
	 * @param variables
	 *            The map of variables that are to be taken into account to compile this expression. May be
	 *            <code>null</code>.
	 * @param nsURIs
	 *            The set of all metamodel URIs visible to the compiled expression.
	 * @param dependencies
	 *            Optional dependencies of this compilation.
	 * @param extendedDependencies
	 *            This can be used to tell us that other modules need to be built before the current one.
	 * @since 3.3
	 */
	public CompilationContext(String expression, String targetType, Map<String, String> variables,
			Set<String> nsURIs, Multimap<String, URI> dependencies, Set<ModuleDescriptor> extendedDependencies) {
		this.expression = expression;
		this.targetType = targetType;
		this.variables = variables;
		this.nsURIs = nsURIs;
		this.dependencies = dependencies;
		this.extendedDependencies = extendedDependencies;
	}

	/**
	 * Returns the actual expression to compile.
	 * 
	 * @return The actual expression to compile.
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Returns the target type of this compilation.
	 * 
	 * @return The target type of this compilation.
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * Returns the map of accessible variables.
	 * 
	 * @return The map of accessible variables.
	 */
	public Map<String, String> getVariables() {
		return variables;
	}

	/**
	 * Returns the optional dependencies of this build.
	 * 
	 * @return The optional dependencies of this build.
	 */
	public Multimap<String, URI> getDependencies() {
		return dependencies;
	}

	/**
	 * Returns the set of all metamodel URIs visible to the compiled expression.
	 * 
	 * @return The set of all metamodel URIs visible to the compiled expression.
	 */
	public Set<String> getNsURIs() {
		return nsURIs;
	}

	/**
	 * Returns the dependencies that need to be built before the current module.
	 * 
	 * @return The dependencies that need to be built before the current module.
	 * @since 3.3
	 */
	public Set<ModuleDescriptor> getExtendedDependencies() {
		return extendedDependencies;
	}
}
