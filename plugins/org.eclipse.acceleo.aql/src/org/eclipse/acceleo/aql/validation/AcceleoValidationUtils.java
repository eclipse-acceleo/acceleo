/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * Utility class related to Acceleo's validation, particularly {@link TypedElement}, whose {@link IType} is
 * rooted in either the Java type system or the Ecore type system.
 * 
 * @author Florent Latombe
 */
public final class AcceleoValidationUtils {

	private AcceleoValidationUtils() {
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
				AcceleoValidationUtils::getFullyQualifiedName).collect(Collectors.joining(" | "));
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
		String fullyQualifiedName = null;
		Object javaClassOrEClassifier = Objects.requireNonNull(aqlType).getType();
		if (javaClassOrEClassifier instanceof Class) {
			Class<?> javaClass = (Class<?>)javaClassOrEClassifier;
			fullyQualifiedName = javaClass.getCanonicalName();
		} else if (javaClassOrEClassifier instanceof EClass) {
			EClass eClass = (EClass)javaClassOrEClassifier;
			fullyQualifiedName = getCanonicalName(eClass);
		} else if (javaClassOrEClassifier instanceof EDataType) {
			EDataType eDataType = (EDataType)javaClassOrEClassifier;
			fullyQualifiedName = eDataType.getInstanceClass().getCanonicalName();
		} else {
			throw new IllegalArgumentException("Unexpected 'type' Object in " + aqlType.toString() + ": "
					+ javaClassOrEClassifier.toString()
					+ " is neither a Java class nor an EClassifier instance.");
		}
		return fullyQualifiedName;
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
			String variablePossibleTypesRepresentation = AcceleoValidationUtils
					.getPossibleTypesRepresentation(variable, acceleoValidationResult);
			variablesListRepresentation += variablePossibleTypesRepresentation;
			if (variables.indexOf(variable) != variables.size() - 1) {
				variablesListRepresentation += ", ";
			}
		}
		return variablesListRepresentation;
	}

	/**
	 * Provides the {@link Set} of possible {@link IType types} for an Acceleo {@link TypedElement} within an
	 * {@link IQueryEnvironment}.
	 * 
	 * @param typedElement
	 *            the (non-{@code null}) {@link TypedElement}.
	 * @param queryEnvironment
	 *            the (non-{@code null}) {@link IQueryEnvironment}.
	 * @return the {@link Set} of possible {@link IType types} for {@code typedElement} in
	 *         {@code queryEnvironment}.
	 */
	public static Set<IType> getPossibleTypes(TypedElement typedElement, IQueryEnvironment queryEnvironment) {
		AstResult typeToAnalyze = typedElement.getType();
		if (typeToAnalyze == null) {
			// In some cases, no type is specified for the TypedElement.
			return new HashSet<>();
		} else {
			AstValidator aqlValidator = new AstValidator(new ValidationServices(queryEnvironment));
			IValidationResult variableTypeValidationResult = aqlValidator.validate(Collections.emptyMap(),
					typedElement.getType());
			return variableTypeValidationResult.getPossibleTypes(typedElement.getTypeAql());
		}
	}
}
