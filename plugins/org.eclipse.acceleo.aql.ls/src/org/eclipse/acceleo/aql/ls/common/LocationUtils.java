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
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(contextQualifiedName);

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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(contextQualifiedName);

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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(contextQualifiedName);

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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(contextQualifiedName);

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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(contextQualifiedName);

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
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI sourceURI = resolver.getSourceURI(contextQualifiedName);

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

}
