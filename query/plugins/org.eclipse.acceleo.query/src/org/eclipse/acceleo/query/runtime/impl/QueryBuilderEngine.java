/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.eclipse.acceleo.query.parser.QueryParser;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * {@link QueryBuilderEngine} is the default query evaluation engine.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class QueryBuilderEngine implements IQueryBuilderEngine {

	/**
	 * Constructor. It takes an IQueryEnvironment as parameter.
	 * 
	 * @param queryEnvironment
	 *            The environment containing all necessary information and used to execute query services.
	 * @deprecated see {@link #QueryBuilderEngine()}
	 */
	public QueryBuilderEngine(IReadOnlyQueryEnvironment queryEnvironment) {
		this();
	}

	/**
	 * Constructor.
	 */
	public QueryBuilderEngine() {
	}

	@Override
	public AstResult build(String expression) throws AcceleoQueryEvaluationException {
		final AstResult result;

		if (expression != null && expression.length() > 0) {
			AstBuilderListener astBuilder = new AstBuilderListener();
			CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
			QueryLexer lexer = new QueryLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			lexer.removeErrorListeners();
			lexer.addErrorListener(astBuilder.getErrorListener());
			TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
			QueryParser parser = new QueryParser(tokens);
			parser.addParseListener(astBuilder);
			parser.removeErrorListeners();
			parser.addErrorListener(astBuilder.getErrorListener());
			// parser.setTrace(true);
			parser.setErrorHandler(new DefaultErrorStrategy() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.antlr.v4.runtime.DefaultErrorStrategy#sync(org.antlr.v4.runtime.Parser)
				 */
				@Override
				public void sync(Parser recognizer) throws RecognitionException {
					// nothing to do here
				}

			});
			parser.entry();
			result = astBuilder.getAstResult();
		} else {
			ErrorExpression errorExpression = (ErrorExpression)EcoreUtil.create(AstPackage.eINSTANCE
					.getErrorExpression());
			List<Error> errors = new ArrayList<Error>(1);
			errors.add(errorExpression);
			final Positions<ASTNode> positions = new Positions<>();
			if (expression != null) {
				positions.setStartPositions(errorExpression, Integer.valueOf(0));
				positions.setStartLines(errorExpression, Integer.valueOf(0));
				positions.setStartColumns(errorExpression, Integer.valueOf(0));
				positions.setEndPositions(errorExpression, Integer.valueOf(0));
				positions.setEndLines(errorExpression, Integer.valueOf(0));
				positions.setEndColumns(errorExpression, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"null or empty string.", new Object[] {errorExpression }));
			result = new AstResult(errorExpression, positions, errors, diagnostic);
		}

		return result;
	}

}
