/*******************************************************************************
 * Copyright (c) 2015 Obeo.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.eclipse.acceleo.query.parser.QueryParser;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * {@link QueryBuilderEngine} is the default query evaluation engine.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class QueryBuilderEngine implements IQueryBuilderEngine {

	/**
	 * The environment containing all necessary information and used to execute query services.
	 */
	private IQueryEnvironment queryEnvironment;

	/**
	 * Constructor. It takes an IQueryEnvironment as parameter.
	 * 
	 * @param queryEnvironment
	 *            The environment containing all necessary information and used to execute query services.
	 */
	public QueryBuilderEngine(IQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	@Override
	public IQueryBuilderEngine.AstResult build(String expression) throws AcceleoQueryEvaluationException {
		final IQueryBuilderEngine.AstResult result;

		if (expression != null && expression.length() > 0) {
			CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
			QueryLexer lexer = new QueryLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
			QueryParser parser = new QueryParser(tokens);
			AstBuilderListener astBuilder = new AstBuilderListener(queryEnvironment);
			parser.addParseListener(astBuilder);
			parser.addErrorListener(astBuilder.getErrorListener());
			// parser.setTrace(true);
			parser.entry();
			result = astBuilder.getAstResult();
		} else {
			ErrorExpression errorExpression = (ErrorExpression)EcoreUtil.create(AstPackage.eINSTANCE
					.getErrorExpression());
			List<Error> errors = new ArrayList<Error>(1);
			errors.add(errorExpression);
			final Map<Object, Integer> positions = new HashMap<Object, Integer>();
			if (expression != null) {
				positions.put(errorExpression, Integer.valueOf(0));
			}
			result = new AstResult(errorExpression, positions, positions, errors);
		}

		return result;
	}

}
