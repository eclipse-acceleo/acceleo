/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.common;

import java.net.URI;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IRange;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

/**
 * {@link Location}, {@link LocationLink}, {@link Range} utility class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class LocationUtils {

	/**
	 * Constructor.
	 */
	private LocationUtils() {
		// nothing to do here
	}

	/**
	 * Gets the {@link Location} of the given {@link VarRef}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param contextQualifiedName
	 *            the context qualifier name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the {@link Location} of the given {@link VarRef}
	 */
	public static Location location(IQualifiedNameQueryEnvironment queryEnvironment,
			String contextQualifiedName, IAcceleoValidationResult acceleoValidationResult, VarRef varRef) {
		final URI sourceURI = getSourceURI(queryEnvironment, contextQualifiedName);

		return new Location(sourceURI.toASCIIString(), range(acceleoValidationResult, varRef));
	}

	/**
	 * Gets the identifier {@link Location} of the given {@link VarRef}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param contextQualifiedName
	 *            the context qualifier name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the identifier {@link Location} of the given {@link VarRef}
	 */
	public static Location identifierLocation(IQualifiedNameQueryEnvironment queryEnvironment,
			String contextQualifiedName, IAcceleoValidationResult acceleoValidationResult, VarRef varRef) {
		final URI sourceURI = getSourceURI(queryEnvironment, contextQualifiedName);

		return new Location(sourceURI.toASCIIString(), identifierRange(acceleoValidationResult, varRef));
	}

	/**
	 * Gets the {@link Range} of the given {@link VarRef}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the {@link Range} of the given {@link VarRef}
	 */
	public static Range range(IAcceleoValidationResult acceleoValidationResult, VarRef varRef) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getStartLine(varRef);
		final int startColumn = astResult.getStartColumn(varRef);
		final int endLine = astResult.getEndLine(varRef);
		final int endColumn = astResult.getEndColumn(varRef);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link VarRef}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param varRef
	 *            the {@link VarRef}
	 * @return the identifier {@link Range} of the given {@link VarRef}
	 */
	public static Range identifierRange(IAcceleoValidationResult acceleoValidationResult, VarRef varRef) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getIdentifierStartLine(varRef);
		final int startColumn = astResult.getIdentifierStartColumn(varRef);
		final int endLine = astResult.getIdentifierEndLine(varRef);
		final int endColumn = astResult.getIdentifierEndColumn(varRef);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the {@link Location} of the given {@link Expression}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param contextQualifiedName
	 *            the context qualifier name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param expression
	 *            the {@link Expression}
	 * @return the {@link Location} of the given {@link Expression}
	 */
	public static Location location(IQualifiedNameQueryEnvironment queryEnvironment,
			String contextQualifiedName, IAcceleoValidationResult acceleoValidationResult,
			Expression expression) {
		final URI sourceURI = getSourceURI(queryEnvironment, contextQualifiedName);

		return new Location(sourceURI.toASCIIString(), range(acceleoValidationResult, expression));
	}

	/**
	 * Gets the identifier {@link Location} of the given {@link Expression}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param contextQualifiedName
	 *            the context qualifier name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param expression
	 *            the {@link Expression}
	 * @return the identifier {@link Location} of the given {@link Expression}
	 */
	public static Location identifierLocation(IQualifiedNameQueryEnvironment queryEnvironment,
			String contextQualifiedName, IAcceleoValidationResult acceleoValidationResult,
			Expression expression) {
		final URI sourceURI = getSourceURI(queryEnvironment, contextQualifiedName);

		return new Location(sourceURI.toASCIIString(), identifierRange(acceleoValidationResult, expression));
	}

	/**
	 * Gets the {@link Range} of the given {@link Expression}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param expression
	 *            the {@link Expression}
	 * @return the {@link Range} of the given {@link Expression}
	 */
	public static Range range(IAcceleoValidationResult acceleoValidationResult, Expression expression) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getStartLine(expression);
		final int startColumn = astResult.getStartColumn(expression);
		final int endLine = astResult.getEndLine(expression);
		final int endColumn = astResult.getEndColumn(expression);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link Expression}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param expression
	 *            the {@link Expression}
	 * @return the identifier {@link Range} of the given {@link Expression}
	 */
	public static Range identifierRange(IAcceleoValidationResult acceleoValidationResult,
			Expression expression) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getIdentifierStartLine(expression);
		final int startColumn = astResult.getIdentifierStartColumn(expression);
		final int endLine = astResult.getIdentifierEndLine(expression);
		final int endColumn = astResult.getIdentifierEndColumn(expression);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the {@link Location} of the given {@link ASTNode}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param contextQualifiedName
	 *            the context qualifier name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the {@link Location} of the given {@link ASTNode}
	 */
	public static Location location(IQualifiedNameQueryEnvironment queryEnvironment,
			String contextQualifiedName, IAcceleoValidationResult acceleoValidationResult, ASTNode astNode) {
		final URI sourceURI = getSourceURI(queryEnvironment, contextQualifiedName);

		return new Location(sourceURI.toASCIIString(), range(acceleoValidationResult, astNode));
	}

	/**
	 * Gets the identifier {@link Location} of the given {@link ASTNode}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param contextQualifiedName
	 *            the context qualifier name
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the identifier {@link Location} of the given {@link ASTNode}
	 */
	public static Location identifierLocation(IQualifiedNameQueryEnvironment queryEnvironment,
			String contextQualifiedName, IAcceleoValidationResult acceleoValidationResult, ASTNode astNode) {
		final URI sourceURI = getSourceURI(queryEnvironment, contextQualifiedName);

		return new Location(sourceURI.toASCIIString(), identifierRange(acceleoValidationResult, astNode));
	}

	/**
	 * Gets the {@link Range} of the given {@link ASTNode}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the identifier {@link Range} of the given {@link ASTNode}
	 */
	public static Range range(IAcceleoValidationResult acceleoValidationResult, ASTNode astNode) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getStartLine(astNode);
		final int startColumn = astResult.getStartColumn(astNode);
		final int endLine = astResult.getEndLine(astNode);
		final int endColumn = astResult.getEndColumn(astNode);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link ASTNode}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the identifier {@link Range} of the given {@link ASTNode}
	 */
	public static Range identifierRange(IAcceleoValidationResult acceleoValidationResult, ASTNode astNode) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getIdentifierStartLine(astNode);
		final int startColumn = astResult.getIdentifierStartColumn(astNode);
		final int endLine = astResult.getIdentifierEndLine(astNode);
		final int endColumn = astResult.getIdentifierEndColumn(astNode);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the {@link Range} of the given {@link Binding}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param binding
	 *            the {@link Binding}
	 * @return the {@link Range} of the given {@link Binding}
	 */
	public static Range range(IAcceleoValidationResult acceleoValidationResult, Binding binding) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getStartLine(binding);
		final int startColumn = astResult.getStartColumn(binding);
		final int endLine = astResult.getEndLine(binding);
		final int endColumn = astResult.getEndColumn(binding);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link Binding}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param binding
	 *            the {@link Binding}
	 * @return the identifier {@link Range} of the given {@link Binding}
	 */
	public static Range identifierRange(IAcceleoValidationResult acceleoValidationResult, Binding binding) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getIdentifierStartLine(binding);
		final int startColumn = astResult.getIdentifierStartColumn(binding);
		final int endLine = astResult.getIdentifierEndLine(binding);
		final int endColumn = astResult.getIdentifierEndColumn(binding);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the {@link Range} of the given {@link VariableDeclaration}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param variableDeclaration
	 *            the {@link VariableDeclaration}
	 * @return the {@link Range} of the given {@link VariableDeclaration}
	 */
	public static Range range(IAcceleoValidationResult acceleoValidationResult,
			VariableDeclaration variableDeclaration) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getStartLine(variableDeclaration);
		final int startColumn = astResult.getStartColumn(variableDeclaration);
		final int endLine = astResult.getEndLine(variableDeclaration);
		final int endColumn = astResult.getEndColumn(variableDeclaration);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link VariableDeclaration}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param variableDeclaration
	 *            the {@link VariableDeclaration}
	 * @return the identifier {@link Range} of the given {@link VariableDeclaration}
	 */
	public static Range identifierRange(IAcceleoValidationResult acceleoValidationResult,
			VariableDeclaration variableDeclaration) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getIdentifierStartLine(variableDeclaration);
		final int startColumn = astResult.getIdentifierStartColumn(variableDeclaration);
		final int endLine = astResult.getIdentifierEndLine(variableDeclaration);
		final int endColumn = astResult.getIdentifierEndColumn(variableDeclaration);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the {@link Range} of the given {@link Variable}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param variable
	 *            the {@link Variable}
	 * @return the {@link Range} of the given {@link Variable}
	 */
	public static Range range(IAcceleoValidationResult acceleoValidationResult, Variable variable) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getStartLine(variable);
		final int startColumn = astResult.getStartColumn(variable);
		final int endLine = astResult.getEndLine(variable);
		final int endColumn = astResult.getEndColumn(variable);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link Variable}.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param variable
	 *            the {@link Variable}
	 * @return the identifier {@link Range} of the given {@link Variable}
	 */
	public static Range identifierRange(IAcceleoValidationResult acceleoValidationResult, Variable variable) {
		final AcceleoAstResult astResult = acceleoValidationResult.getAcceleoAstResult();

		final int startLine = astResult.getIdentifierStartLine(variable);
		final int startColumn = astResult.getIdentifierStartColumn(variable);
		final int endLine = astResult.getIdentifierEndLine(variable);
		final int endColumn = astResult.getIdentifierEndColumn(variable);
		final Position start = new Position(startLine, startColumn);
		final Position end = new Position(endLine, endColumn);

		return new Range(start, end);
	}

	/**
	 * Gets the source {@link URI} of the given qualified name.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param qualifiedName
	 *            the qualified name
	 * @return the source {@link URI} of the given qualified name
	 */
	private static URI getSourceURI(IQualifiedNameQueryEnvironment queryEnvironment, String qualifiedName) {
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(qualifiedName);
		return sourceURI;
	}

	/**
	 * Gets the identifier {@link Range} of the given {@link ISourceLocation}.
	 * 
	 * @param location
	 *            the {@link ISourceLocation}
	 * @return the identifier {@link Range} of the given {@link ISourceLocation}
	 */
	public static Range identifierRange(ISourceLocation location) {

		final IRange identifierRange = location.getIdentifierRange();
		final Position identifierStart = new Position(identifierRange.getStart().getLine(), identifierRange
				.getStart().getColumn());
		final Position identifierEnd = new Position(identifierRange.getEnd().getLine(), identifierRange
				.getEnd().getColumn());

		return new Range(identifierStart, identifierEnd);
	}

	/**
	 * Gets the {@link Range} of the given {@link ISourceLocation}.
	 * 
	 * @param location
	 *            the {@link ISourceLocation}
	 * @return the {@link Range} of the given {@link ISourceLocation}
	 */
	public static Range range(ISourceLocation location) {

		final IRange range = location.getIdentifierRange();
		final Position start = new Position(range.getStart().getLine(), range.getStart().getColumn());
		final Position end = new Position(range.getEnd().getLine(), range.getEnd().getColumn());

		return new Range(start, end);
	}

}
