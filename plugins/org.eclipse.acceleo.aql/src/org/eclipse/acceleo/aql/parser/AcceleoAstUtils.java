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
package org.eclipse.acceleo.aql.parser;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;

/**
 * Utility methods for navigating the {@link AcceleoAstResult Acceleo Abstract Syntax Tree (AST)}. The AST is
 * obtained using the {@link AcceleoParser}.
 * 
 * @author Florent Latombe
 */
public final class AcceleoAstUtils {

	private AcceleoAstUtils() {
		// Utility class.
	}

	/**
	 * Provides the Acceleo AST element that contains the given AQL AST element.
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) {@link EObject} that is an AQL AST element
	 *            ({@link org.eclipse.acceleo.query.ast.Expression} or {@link VariableDeclaration}).
	 * @return the Acceleo {@link Expression} or {@link TypedElement} that contains the given AQL AST element.
	 */
	public static AcceleoASTNode getContainerOfAqlAstElement(EObject aqlAstElement) {
		Expression containerAcceleoExpression = AcceleoAstUtils.getContainerAcceleoExpression(aqlAstElement);
		if (containerAcceleoExpression == null) {
			// If the AQL element is not in an Acceleo Expression, it is in a TypedElement.
			TypedElement containerAcceleoTypedElement = AcceleoAstUtils.getContainerTypedElement(
					aqlAstElement);
			// In practice, all TypedElements are also ASTNodes so this cast should be safe.
			return (AcceleoASTNode)containerAcceleoTypedElement;
		} else {
			return containerAcceleoExpression;
		}
	}

	/**
	 * Provides the AQL {@link AstResult} corresponding to an AQL AST element.
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) AQL AST element ({@link org.eclipse.acceleo.query.ast.Expression} or
	 *            {@link VariableDeclaration}).
	 * @return the AQL {@link AstResult} corresponding to {@code aqlAstElement}.
	 */
	public static AstResult getAqlAstResultOfAqlAstElement(EObject aqlAstElement) {
		AcceleoASTNode acceleoContainerOfAqlAstElement = getContainerOfAqlAstElement(aqlAstElement);
		return getContainedAqlAstResultOf(acceleoContainerOfAqlAstElement);
	}

	/**
	 * Provides the {@link AstResult} contained by an {@link AcceleoASTNode Acceleo AST Element}.
	 * 
	 * @param acceleoAstElementContainingAqlAstElement
	 *            the (non-{@code null}) {@link AcceleoASTNode}. Most of the times it should be either an
	 *            {@link Expression} or a {@link TypedElement}.
	 * @return the contained {@link AstResult AQL AST}, or {@code null} otherwise.
	 */
	public static AstResult getContainedAqlAstResultOf(
			AcceleoASTNode acceleoAstElementContainingAqlAstElement) {
		AstResult containedAstResult = null;
		if (acceleoAstElementContainingAqlAstElement instanceof Expression) {
			Expression acceleoContainerExpression = (Expression)acceleoAstElementContainingAqlAstElement;
			containedAstResult = acceleoContainerExpression.getAst();
		} else if (acceleoAstElementContainingAqlAstElement instanceof TypedElement) {
			TypedElement acceleoContainerTypedElement = (TypedElement)acceleoAstElementContainingAqlAstElement;
			containedAstResult = acceleoContainerTypedElement.getType();
		} else {
			// Other Acceleo types do not contain AQL elements.
		}
		return containedAstResult;
	}

	/**
	 * Provides the containing {@link Module} of an {@link AcceleoASTNode}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode}.
	 * @return {@code astNode} if it is a {@link Module}, or the first non-{@code null}
	 *         {@link AcceleoASTNode#eContainer() container} of {@code astNode} that is instance of
	 *         {@link Module}.
	 */
	public static Module getContainerModule(AcceleoASTNode astNode) {
		return getSelfOrFirstContainerOfType(astNode, Module.class);
	}

	/**
	 * FIXME: temporary quick implementation of a resolution mecanism for Acceleo.
	 * 
	 * @param module
	 *            the (non-{@code null}) {@link Module}.
	 * @return the fully-qualified name of {@code module}.
	 */
	public static String getModuleFullyQualifiedName(Module module) {
		Objects.requireNonNull(module);
		return module.getName() + "_" + module.getMetamodels().stream().map(Metamodel::getReferencedPackage)
				.map(EPackage::getNsURI).collect(Collectors.joining("[", ", ", "]"));
	}

	/**
	 * Provides the container Acceleo {@link Expression} of the given {@link EObject} (in our cases most
	 * likely an AQL AST element). Note that in an Acceleo AST, not all AQL Expression are contained by an
	 * Acceleo Expression. It may be contained in a {@link TypedElement} as well. Use
	 * {@link #getContainerOfAqlAstElement(EObject)}.
	 * 
	 * @param containedEObject
	 *            the (non-{@code null}) {@link EObject}.
	 * @return the Acceleo {@link Expression} that contains {@code containedEObject}.
	 */
	public static Expression getContainerAcceleoExpression(EObject containedEObject) {
		return getSelfOrFirstContainerOfType(containedEObject, Expression.class);
	}

	/**
	 * Provides the container Acceleo {@link TypedElement} of the given {@link EObject} (in our cases most
	 * likely an AQL AST element).
	 * 
	 * @param containedEObject
	 *            the (non-{@code null}) {@link EObject}.
	 * @return the Acceleo {@link TypedElement} that contains {@code containedEObject}.
	 */
	public static TypedElement getContainerTypedElement(EObject containedEObject) {
		return getSelfOrFirstContainerOfType(containedEObject, TypedElement.class);
	}

	/**
	 * Provides the first container (in the EMF sense) of the given {@link EObject} that is an instance of the
	 * given {@link Class}. If the {@link EObject} is an instance of the specified type, then it is simply
	 * cast and returned.
	 * 
	 * @param <T>
	 *            the type of the container.
	 * @param containedEObject
	 *            the (non-{@code null}) contained {@link EObject}.
	 * @param containerType
	 *            the (non-{@code null}) {@link Class} of the container.
	 * @return the first container of {@code containedEObject} that is an instance of {@code containerType}.
	 */
	private static <T extends EObject> T getSelfOrFirstContainerOfType(EObject containedEObject,
			Class<T> containerType) {
		Objects.requireNonNull(containedEObject);
		Objects.requireNonNull(containerType);

		EObject candidate = containedEObject;
		while (candidate != null && !(containerType.isInstance(candidate))) {
			candidate = candidate.eContainer();
		}
		return containerType.cast(candidate);
	}

	/**
	 * Provides the {@link AcceleoASTNode} in the given {@link AcceleoAstResult} corresponding to the given
	 * {@link AcceleoASTNode}. That is, either the {@link AcceleoASTNode} itself, or its equivalent
	 * {@link AcceleoASTNode}, if the {@link AcceleoASTNode} and the {@link AcceleoAstResult} come from two
	 * different parsings.
	 * 
	 * @param acceleoAstNode
	 *            the (non-{@code null}) {@link AcceleoASTNode}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return {@code astNode} or its equivalent in {@code acceleoAstResult}.
	 * @param <T>
	 *            the specific type of {@link AcceleoASTNode}.
	 */
	public static <T extends AcceleoASTNode> T getSelfOrEquivalentOf(T acceleoAstNode,
			AcceleoAstResult inAcceleoAstResult) {
		if (inAcceleoAstResult.getStartPosition(acceleoAstNode) == -1 && inAcceleoAstResult.getEndPosition(
				acceleoAstNode) == -1) {
			return getStructuralEqualOf(acceleoAstNode, inAcceleoAstResult);
		} else {
			return acceleoAstNode;
		}
	}

	/**
	 * Provides the equivalent {@link EObject AQL AST element} of an {@link EObject AQL AST element} found in
	 * the given {@link AcceleoAstResult Acceleo AST}.
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) {@link EObject AQL AST element}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult Acceleo AST} to search in.
	 * @return {@code aqlAstElement} or its equivalent in {@code inAcceleoAstResult}.
	 * @param <T>
	 *            the specific type of {@link EObject AQL AST element}.
	 */
	public static <T extends EObject> T getSelfOrEquivalentOf(T aqlAstElement,
			AcceleoAstResult inAcceleoAstResult) {
		// Step 1: navigate from the AQL AST element up to the container Acceleo AST element.
		AcceleoASTNode acceleoContainerOfAqlAstElement = getContainerOfAqlAstElement(aqlAstElement);

		// Step 2: find the equivalent of the container Acceleo AST element.
		AcceleoASTNode equivalentOfAcceleoContainerOfAqlAstElement = getSelfOrEquivalentOf(
				acceleoContainerOfAqlAstElement, inAcceleoAstResult);

		// Step 3: find AQL AST in the container equivalent.
		AstResult aqlAstToSearchIn = getContainedAqlAstResultOf(equivalentOfAcceleoContainerOfAqlAstElement);

		// Step 4: finally, search for the AQL AST element in the AQL AST contained by our original Acceleo
		// AST.
		return getSelfOrEquivalentOf(aqlAstElement, aqlAstToSearchIn);
	}

	/**
	 * Provides the equivalent {@link EObject AQL AST element} of an {@link EObject AQL AST element} found in
	 * the given {@link AstResult AQL AST}.
	 * 
	 * @param <T>
	 *            the specific type of {@link EObject AQL AST element}.
	 * @param aqlAstElement
	 *            the (non-{@code null}) {@link T AQL AST element}.
	 * @param inAqlAstResult
	 *            the (non-{@code null}) {@link AstResult AQL AST} to search in.
	 * @return {@code aqlAstElement} or its equivalent in {@code inAqlAstResult}.
	 */
	public static <T extends EObject> T getSelfOrEquivalentOf(T aqlAstElement, AstResult inAqlAstResult) {
		org.eclipse.acceleo.query.ast.Expression aqlRootElement = inAqlAstResult.getAst();
		if (isEqualStructurally(aqlAstElement, aqlRootElement)) {
			return (T)aqlRootElement;
		} else {
			Iterator<EObject> candidatesIterator = aqlRootElement.eAllContents();
			while (candidatesIterator.hasNext()) {
				EObject candidate = candidatesIterator.next();
				if (isEqualStructurally(candidate, aqlAstElement)) {
					return (T)candidate;
				}
			}
		}
		throw new IllegalArgumentException("Equivalent of AQL AcceleoASTNode \"" + aqlAstElement.toString()
				+ "\" could not be found in AQL AST: " + inAqlAstResult);
	}

	/**
	 * Provides the {@link AcceleoASTNode} in the given {@link AcceleoAstResult} that is structurally equal to
	 * the given {@link AcceleoASTNode}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode} whose equivalent we are looking for.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult} in which to search.
	 * @return the {@link AcceleoASTNode} of {@code inAcceleoAstResult} that is structurally equal to
	 *         {@code astNode}. If there is none, an exception is thrown.
	 * @param <T>
	 *            the specific type of {@link AcceleoASTNode}.
	 */
	private static <T extends AcceleoASTNode> T getStructuralEqualOf(T astNode,
			AcceleoAstResult inAcceleoAstResult) {
		Module inAcceleoModule = inAcceleoAstResult.getModule();
		if (isEqualStructurally(astNode, inAcceleoModule)) {
			return (T)inAcceleoModule;
		} else {
			Iterator<EObject> candidatesIterator = inAcceleoModule.eAllContents();
			while (candidatesIterator.hasNext()) {
				EObject candidate = candidatesIterator.next();
				if (candidate instanceof AcceleoASTNode) {
					AcceleoASTNode candidateAstNode = (AcceleoASTNode)candidate;
					if (isEqualStructurally(candidateAstNode, astNode)) {
						return (T)candidateAstNode;
					}
				}
			}
		}
		throw new IllegalArgumentException("Equivalent of Acceleo AcceleoASTNode \"" + astNode.toString()
				+ "\" could not be found in Acceleo AST: " + inAcceleoAstResult);
	}

	/**
	 * Customized {@link EqualityHelper} necessary because the default behavior does not go well with the Java
	 * objects ASTResult and AcceleoAstResult we use via EDataTypes.
	 * 
	 * @param left
	 *            an {@link AcceleoASTNode}.
	 * @param right
	 *            an {@link AcceleoASTNode}.
	 * @return {@code true} if {@code left} and {@code right} are structurally equal.
	 * @see EqualityHelper#equals(EObject, EObject)
	 */
	public static boolean isEqualStructurally(AcceleoASTNode left, AcceleoASTNode right) {
		return new EqualityHelper() {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean haveEqualAttribute(EObject eObject1, EObject eObject2,
					org.eclipse.emf.ecore.EAttribute attribute) {
				boolean result;
				if (attribute.getEAttributeType().equals(AcceleoPackage.eINSTANCE.getASTResult())) {
					AstResult value1 = (AstResult)eObject1.eGet(attribute);
					AstResult value2 = (AstResult)eObject2.eGet(attribute);
					result = this.equals(value1.getAst(), value2.getAst());
				} else if (attribute.getEAttributeType().equals(AcceleoPackage.eINSTANCE
						.getAcceleoAstResult())) {
					AcceleoAstResult value1 = (AcceleoAstResult)eObject1.eGet(attribute);
					AcceleoAstResult value2 = (AcceleoAstResult)eObject2.eGet(attribute);
					result = this.equals(value1.getModule(), value2.getModule());
				} else {
					result = super.haveEqualAttribute(eObject1, eObject2, attribute);
				}
				return result;
			};
		}.equals(left, right);
	}

	/**
	 * Customized {@link EqualityHelper} for AQL AST elements.
	 * 
	 * @param left
	 *            an {@link EObject AQL AST element}.
	 * @param right
	 *            an {@link EObject AQL AST element}.
	 * @return {@code true} if {@code left} and {@code right} are structurally equal.
	 * @see EqualityHelper#equals(EObject, EObject)
	 */
	public static boolean isEqualStructurally(EObject left, EObject right) {
		return new EqualityHelper() {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean haveEqualAttribute(EObject eObject1, EObject eObject2,
					org.eclipse.emf.ecore.EAttribute attribute) {
				return super.haveEqualAttribute(eObject1, eObject2, attribute);
			};
		}.equals(left, right);
	}

}
