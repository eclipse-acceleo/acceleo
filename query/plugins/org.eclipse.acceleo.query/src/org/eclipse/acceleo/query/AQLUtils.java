/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.eclipse.acceleo.query.parser.QueryParser;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AQLUtils {

	/**
	 * Constructor.
	 */
	private AQLUtils() {
		// nothing to do here
	}

	/**
	 * Computes the {@link List} of available types for the given {@link List} of registered
	 * {@link EPackage#getNsURI() nsURI}.
	 * 
	 * @param uris
	 *            the {@link List} of registered {@link EPackage#getNsURI() nsURI}
	 * @return the {@link List} of available types for the given {@link List} of registered
	 *         {@link EPackage#getNsURI() nsURI}
	 */
	public static List<String> computeAvailableTypes(List<String> uris, boolean includePrimitiveTypes,
			boolean includeSequenceTypes, boolean includeSetTypes) {
		final Set<String> types = new HashSet<>();

		if (includePrimitiveTypes) {
			types.add(AstSerializer.STRING_TYPE);
			types.add(AstSerializer.INTEGER_TYPE);
			types.add(AstSerializer.REAL_TYPE);
			types.add(AstSerializer.BOOLEAN_TYPE);
		}

		if (uris != null) {
			for (String nsURI : uris) {
				final EPackage ePkg = EPackageRegistryImpl.INSTANCE.getEPackage(nsURI);
				if (ePkg != null) {
					types.addAll(getEClassifiers(ePkg));
				}
			}
		}

		final List<String> res = new ArrayList<>(types.size() * 3);
		for (String type : types) {
			res.add(type);
			if (includeSequenceTypes) {
				res.add("Sequence(" + type + ")");
			}
			if (includeSetTypes) {
				res.add("OrderedSet(" + type + ")");
			}
		}
		Collections.sort(res);

		return res;
	}

	/**
	 * Gets the {@link List} of all classifiers in the given {@link EPackage}.
	 * 
	 * @param ePkg
	 *            the {@link EPackage}
	 * @return the {@link List} of all classifiers in the given {@link EPackage}
	 */
	private static List<String> getEClassifiers(EPackage ePkg) {
		final List<String> res = new ArrayList<>();

		for (EClassifier eClassifier : ePkg.getEClassifiers()) {
			res.add(ePkg.getName() + "::" + eClassifier.getName());
		}
		for (EPackage child : ePkg.getESubpackages()) {
			res.addAll(getEClassifiers(child));
		}

		return res;
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	public static AstResult parseWhileAqlExpression(String expression) {
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
			parser.expression();
			result = astBuilder.getAstResult();
		} else {
			org.eclipse.acceleo.query.ast.ErrorExpression errorExpression = (org.eclipse.acceleo.query.ast.ErrorExpression)EcoreUtil
					.create(AstPackage.eINSTANCE.getErrorExpression());
			List<org.eclipse.acceleo.query.ast.Error> aqlErrors = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			aqlErrors.add(errorExpression);
			final Positions<ASTNode> aqlPositions = new Positions<>();
			if (expression != null) {
				aqlPositions.setIdentifierStartPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierStartLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierStartColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndColumns(errorExpression, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"missing expression", new Object[] {errorExpression }));
			result = new AstResult(errorExpression, aqlPositions, aqlErrors, diagnostic);
		}

		return result;
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	public static AstResult parseWhileAqlTypeLiteral(String expression) {
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
			parser.typeLiteral();
			result = astBuilder.getAstResult();
		} else {
			ErrorTypeLiteral errorTypeLiteral = (ErrorTypeLiteral)EcoreUtil.create(AstPackage.eINSTANCE
					.getErrorTypeLiteral());
			List<org.eclipse.acceleo.query.ast.Error> errs = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			errs.add(errorTypeLiteral);
			final Positions<ASTNode> aqlPositions = new Positions<>();
			if (expression != null) {
				aqlPositions.setIdentifierStartPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierStartLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierStartColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndColumns(errorTypeLiteral, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"missing type literal", new Object[] {errorTypeLiteral }));
			result = new AstResult(errorTypeLiteral, aqlPositions, errs, diagnostic);
		}

		return result;
	}

	/**
	 * Gets the AQL {@link String} representation of the given {@link IType}.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return the AQL {@link String} representation of the given {@link IType}
	 */
	public static String getAqlTypeString(IType type) {
		final StringBuilder res = new StringBuilder();

		if (type instanceof EClassifierType) {
			res.append(((EClassifierType)type).getType().getEPackage().getName());
			res.append(AstSerializer.ECORE_SEPARATOR);
			res.append(((EClassifierType)type).getType().getName());
		} else if (type instanceof SequenceType) {
			res.append("Sequence(");
			res.append(getAqlTypeString(((SequenceType)type).getCollectionType()));
			res.append(")");
		} else if (type instanceof EClassifierSetLiteralType) {
			res.append("{");
			final StringJoiner joiner = new StringJoiner(" | ");
			for (EClassifier eClassifier : ((EClassifierSetLiteralType)type).getEClassifiers()) {
				joiner.add(eClassifier.getEPackage().getName() + AstSerializer.ECORE_SEPARATOR + eClassifier
						.getName());
			}
			res.append(joiner.toString());
			res.append("}");
		} else if (type instanceof SetType) {
			res.append("OrderedSet(");
			res.append(getAqlTypeString(((SetType)type).getCollectionType()));
			res.append(")");
		} else if (type instanceof ClassType) {
			final Class<?> cls = ((ClassType)type).getType();
			if (cls == Double.class) {
				res.append(AstSerializer.REAL_TYPE);
			} else {
				res.append(cls.getSimpleName());
			}
		}

		return res.toString();
	}

	/**
	 * Gets the line number and the column number for each offsets of the given text.
	 * 
	 * @param text
	 *            the String
	 * @return the line number and the column number for each offsets of the given text
	 */
	public static int[][] getLinesAndColumns(String text) {
		final int[][] res = new int[text.length()][2];
		try (LineNumberReader reader = new LineNumberReader(new StringReader(text))) {
			int column = 0;
			for (int i = 0; i < res.length; i++) {
				final int previousLine = reader.getLineNumber();
				reader.read();
				if (previousLine != reader.getLineNumber()) {
					column = 0;
				} else {
					column++;
				}
				res[i][0] = reader.getLineNumber();
				res[i][1] = column;
			}
		} catch (IOException e) {
			// can't happen: we are reading from memory
		}
		return res;
	}

}
