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
package org.eclipse.acceleo.aql.ls.common;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.SymbolKind;

/**
 * An {@link AcceleoSwitch} to determine, from an {@link AcceleoASTNode} element, its corresponding
 * {@link SymbolKind}.
 * 
 * @author Florent Latombe
 */
public class AcceleoAstNodeToSymbolKind extends AcceleoSwitch<SymbolKind> {

	/**
	 * The {@link IAcceleoValidationResult} that accompanies the Acceleo {@link AcceleoASTNode} being switched
	 * on. It can be used to retrieve additional information such as types.
	 */
	private final IAcceleoValidationResult acceleoValidationResult;

	/**
	 * Constructor.
	 * 
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult} of the Acceleo document from which
	 *            the elements on which this switches come from.
	 */
	public AcceleoAstNodeToSymbolKind(IAcceleoValidationResult acceleoValidationResult) {
		this.acceleoValidationResult = Objects.requireNonNull(acceleoValidationResult);
	}

	/**
	 * Default case: {@link SymbolKind#Null}.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public SymbolKind defaultCase(EObject object) {
		return SymbolKind.Null;
	}

	// Expressions and their surroundings: depends on the type
	@Override
	public SymbolKind caseBinding(Binding binding) {
		return this.doSwitch(binding.getInitExpression());
	}

	@Override
	public SymbolKind caseExpressionStatement(ExpressionStatement expressionStatement) {
		return this.doSwitch(expressionStatement.getExpression());
	}

	@Override
	public SymbolKind caseExpression(Expression expression) {
		IValidationResult expressionValidationResult = this.acceleoValidationResult.getValidationResult(
				expression.getAst());
		Set<IType> possibleTypes = expressionValidationResult.getPossibleTypes(expression.getAst().getAst());
		return getSymbolKindFor(possibleTypes);
	}

	@Override
	public SymbolKind caseVariable(Variable variable) {
		// AstResult variableType = variable.getType();
		// IValidationResult variableTypeValidationResult = this.acceleoValidationResult.getValidationResult(
		// variableType);
		// Set<IType> possibleTypes = variableTypeValidationResult.getPossibleTypes(variableType.getAst());
		// return getSymbolKindFor(possibleTypes);
		return SymbolKind.Property;
	}

	private SymbolKind getSymbolKindFor(Set<IType> possibleTypes) {
		if (possibleTypes == null || possibleTypes.isEmpty()) {
			return SymbolKind.Object;
		} else {
			IType type = possibleTypes.iterator().next();
			return getSymbolKindFor(type);
		}
	}

	private SymbolKind getSymbolKindFor(IType type) {
		Object javaClassOrEClass = type.getType();
		if (javaClassOrEClass instanceof Class) {
			return getSymbolKindFor((Class<?>)javaClassOrEClass);
		} else if (javaClassOrEClass instanceof EClass) {
			return getSymbolKindFor((EClass)javaClassOrEClass);
		} else {
			throw new IllegalArgumentException("Unexpected 'type' Object in " + type.toString() + ": "
					+ javaClassOrEClass.toString() + " is neither a Java class nor an EClass instance.");
		}
	}

	private SymbolKind getSymbolKindFor(EClass eClass) {
		return getSymbolKindFor(eClass.getInstanceClass());
	}

	private SymbolKind getSymbolKindFor(Class<?> javaClass) {
		SymbolKind symbolKind = SymbolKind.Object;
		if (String.class.isAssignableFrom(javaClass)) {
			symbolKind = SymbolKind.String;
		} else if (Number.class.isAssignableFrom(javaClass)) {
			symbolKind = SymbolKind.Number;
		} else if (Boolean.class.isAssignableFrom(javaClass)) {
			symbolKind = SymbolKind.Boolean;
		} else if (Array.class.isAssignableFrom(javaClass)) {
			symbolKind = SymbolKind.Array;
		} else if (Enum.class.isAssignableFrom(javaClass)) {
			symbolKind = SymbolKind.EnumMember;
		}
		return symbolKind;
	}
	////

	// Programming structures
	@Override
	public SymbolKind caseForStatement(ForStatement object) {
		return SymbolKind.Operator;
	}

	@Override
	public SymbolKind caseIfStatement(IfStatement object) {
		return SymbolKind.Operator;
	}

	@Override
	public SymbolKind caseLetStatement(LetStatement letStatement) {
		return SymbolKind.Operator;
	}
	////

	// Main declarations
	@Override
	public SymbolKind caseModule(Module object) {
		return SymbolKind.Module;
	}

	@Override
	public SymbolKind caseImport(Import object) {
		return SymbolKind.Property;
	}

	@Override
	public SymbolKind caseFileStatement(FileStatement object) {
		return SymbolKind.File;
	}

	@Override
	public SymbolKind caseMetamodel(Metamodel object) {
		return SymbolKind.Method;
	}
	////

	// Main stuff
	@Override
	public SymbolKind caseQuery(Query object) {
		return SymbolKind.Function;
	}

	@Override
	public SymbolKind caseTemplate(Template object) {
		return SymbolKind.String;
	}

	@Override
	public SymbolKind caseBlock(Block object) {
		return SymbolKind.Struct;
	}
	////
}
