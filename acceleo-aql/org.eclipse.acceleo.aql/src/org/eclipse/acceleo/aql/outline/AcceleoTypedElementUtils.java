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
package org.eclipse.acceleo.aql.outline;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * Utility class related to Acceleo's {@link TypedElement}, whose {@link IType} is rooted in either the Java
 * type system or the Ecore type system.
 * 
 * @author Florent Latombe
 */
public final class AcceleoTypedElementUtils {

	private AcceleoTypedElementUtils() {
		// Utility class.
	}

	/**
	 * Provides the "union type" {@link String} representation of all the possible {@link IType AQL types} for
	 * a {@link TypedElement}.
	 * 
	 * @param acceleoTypedElement
	 *            the (non-{@code null}) {@link TypedElement}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult}.
	 * @return the {@link String} representation of all the possible types of {@code acceleoTypedElement}.
	 */
	public static String getPossibleTypesRepresentation(TypedElement acceleoTypedElement,
			IAcceleoValidationResult acceleoValidationResult) {
		return getPossibleTypes(acceleoTypedElement, acceleoValidationResult).stream().map(
				AcceleoTypedElementUtils::getFullyQualifiedName).collect(Collectors.joining(" | "));
	}

	/**
	 * Retrieves the possible {@link IType AQL Types} of an Acceleo {@link TypedElement} using the
	 * {@link IAcceleoValidationResult}.
	 * 
	 * @param acceleoTypedElement
	 *            the (non-{@code null}) {@link TypedElement}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult}.
	 * @return the {@link Set} of possible {@link IType types}.
	 */
	public static Set<IType> getPossibleTypes(TypedElement acceleoTypedElement,
			IAcceleoValidationResult acceleoValidationResult) {
		AstResult typeAstResult = acceleoTypedElement.getType();
		IValidationResult parameterTypeValidationResult = acceleoValidationResult.getValidationResult(
				typeAstResult);
		Set<IType> possibleTypes = parameterTypeValidationResult.getPossibleTypes(typeAstResult.getAst());

		if (possibleTypes == null) {
			possibleTypes = new HashSet<>();
		}
		return possibleTypes;
	}

	/**
	 * Provides a {@link String} representation of the fully-qualified name of an {@link IType AQL type}. e.g.
	 * either the fully-qualified name of the underlying java type ("java.util.String") or the fully-qualified
	 * name of the Ecore type ("ecore::EReference").
	 * 
	 * @param aqlType
	 *            the (non-{@code null}) {@link IType}.
	 * @return the {@link String} representation of the fully-qualified Java or Ecore name.
	 */
	public static String getFullyQualifiedName(IType aqlType) {
		Object javaClassOrEClass = Objects.requireNonNull(aqlType).getType();
		if (javaClassOrEClass instanceof Class) {
			Class<?> javaClass = (Class<?>)javaClassOrEClass;
			return javaClass.getCanonicalName();
		} else if (javaClassOrEClass instanceof EClass) {
			EClass eClass = (EClass)javaClassOrEClass;
			return getCanonicalName(eClass);
		} else {
			throw new IllegalArgumentException("Unexpected 'type' Object in " + aqlType.toString() + ": "
					+ javaClassOrEClass.toString() + " is neither a Java class nor an EClass instance.");
		}
	}

	/**
	 * Provides the canonical name for an {@link EClass}, using the "::" separator.
	 * 
	 * @param eClass
	 *            the (non-{@code null}) {@link EClass}.
	 * @return the (non-{@code null}) canonical name of {@code eClass}.
	 */
	private static String getCanonicalName(EClass eClass) {
		String eClassName = eClass.getName();
		Stack<EPackage> ePackages = new Stack<>();
		EPackage currentEPackage = eClass.getEPackage();
		do {
			ePackages.add(currentEPackage);
			currentEPackage = currentEPackage.getESuperPackage();
		} while (currentEPackage != null);

		String packageFullyQualifiedName = ePackages.stream().map(EPackage::getName).collect(Collectors
				.joining("::"));
		return packageFullyQualifiedName + "::" + eClassName;
	}

	/**
	 * Provides a {@link String} representation of a {@link List} of {@link Variable} from Acceleo.
	 * 
	 * @param variables
	 *            the (non-{@code null}) {@link List} of {@link Variable}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult}.
	 * @return a (non-{@code null}) {@link String}, concatenation of the {@link String} representations of
	 *         each variable's possible types.
	 */
	public static String getVariablesListRepresentation(List<Variable> variables,
			IAcceleoValidationResult acceleoValidationResult) {
		String variablesListRepresentation = "";
		for (Variable variable : variables) {
			String variablePossibleTypesRepresentation = AcceleoTypedElementUtils
					.getPossibleTypesRepresentation(variable, acceleoValidationResult);
			variablesListRepresentation += variablePossibleTypesRepresentation;
			if (variables.indexOf(variable) != variables.size() - 1) {
				variablesListRepresentation += ", ";
			}
		}
		return variablesListRepresentation;
	}
}
