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
package org.eclipse.acceleo.query.parser;

//CHECKSTYLE:OFF
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreUtil;

//CHECKSTYLE:ON
/**
 * Builder API for AST instances.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AstBuilder {

	/**
	 * Escape \x01 sequence.
	 */
	private static final String ESCAPE_X = "\\x";

	/**
	 * Escape \u0123 sequence.
	 */
	private static final String ESCAPE_U = "\\u";

	/**
	 * Invalid escape sequence message.
	 */
	private static final String INVALID_ESCAPE_SEQUENCE = "Invalid escape sequence : ";

	/**
	 * Creates a new {@link EnumLiteral} instance given an {@link EEnumLiteral}.
	 * 
	 * @param eLiteral
	 *            the {@link EEnumLiteral} this literal denotes.
	 * @return an new {@link EnumLiteral} denoting the specified {@link EEnumLiteral}.
	 */
	public EnumLiteral enumLiteral(EEnumLiteral eLiteral) {
		EnumLiteral literal = (EnumLiteral)EcoreUtil.create(AstPackage.Literals.ENUM_LITERAL);
		literal.setLiteral(eLiteral);
		return literal;
	}

	/**
	 * Creates an integer literal given an int.
	 * 
	 * @param i
	 *            the value
	 * @return an integerliteral
	 */
	public IntegerLiteral integerLiteral(int i) {
		IntegerLiteral literal = (IntegerLiteral)EcoreUtil.create(AstPackage.Literals.INTEGER_LITERAL);
		literal.setValue(i);
		return literal;
	}

	/**
	 * Creates an real literal given an double.
	 * 
	 * @param i
	 *            the value
	 * @return an realliteral
	 */
	public RealLiteral realLiteral(double i) {
		RealLiteral literal = (RealLiteral)EcoreUtil.create(AstPackage.Literals.REAL_LITERAL);
		literal.setValue(i);
		return literal;
	}

	/**
	 * Creates an real literal given an double.
	 * 
	 * @param str
	 *            the value
	 * @return a string literal
	 */
	public StringLiteral stringLiteral(String str) {
		StringLiteral literal = (StringLiteral)EcoreUtil.create(AstPackage.Literals.STRING_LITERAL);
		literal.setValue(stripSlashes(str));
		return literal;
	}

	/**
	 * Creates a new {@link ErrorStringLiteral}.
	 * 
	 * @param str
	 *            the value
	 * @return the new {@link ErrorStringLiteral}
	 */
	public ErrorStringLiteral errorStringLiteral(String str) {
		ErrorStringLiteral errorStringLiteral = (ErrorStringLiteral)EcoreUtil
				.create(AstPackage.Literals.ERROR_STRING_LITERAL);
		errorStringLiteral.setValue(str);
		return errorStringLiteral;
	}

	/**
	 * Returns the text passed as argument replacing all escaped characters to there value.
	 * 
	 * @param text
	 *            the text to convert
	 * @return the converted text
	 */
	public static String stripSlashes(String text) {
		final int end = text.length();
		final StringBuilder res = new StringBuilder(end);
		int i = 0;
		while (i < end) {
			char c = text.charAt(i);
			if (c != '\\') {
				res.append(c);
			} else if (i + 1 < end) {
				char c1 = text.charAt(++i);
				switch (c1) {
					case 'b':
						res.append('\b');
						break;
					case 't':
						res.append('\t');
						break;
					case 'n':
						res.append('\n');
						break;
					case 'f':
						res.append('\f');
						break;
					case 'r':
						res.append('\r');
						break;
					case '\\':
						res.append('\\');
						break;
					case '\"':
						res.append('\"');
						break;
					case '\'':
						res.append('\'');
						break;
					case 'x':
						res.append(stripXChar(text, end, i));
						i += 2;
						break;
					case 'u':
						res.append(stripUChar(text, end, i));
						i += 4;
						break;
					default:
						throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + c + c1);
				}
			} else {
				throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + c);
			}
			++i;
		}
		return res.toString();
	}

	/**
	 * Gets the array of chars from a \x01 escape sequence.
	 * 
	 * @param text
	 *            the input text
	 * @param end
	 *            the input text length
	 * @param i
	 *            the current position to read
	 * @return the array of chars from a \x01 escape sequence
	 */
	private static char[] stripXChar(String text, final int end, int i) {
		if (i + 2 < end) {
			return charFromHexaCode(text.charAt(i + 1), text.charAt(i + 2));
		} else if (i + 1 < end) {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_X + text.charAt(i + 1));
		} else {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_X);
		}
	}

	/**
	 * Gets the array of chars from a \x01 escape sequence.
	 * 
	 * @param text
	 *            the input text
	 * @param end
	 *            the input text length
	 * @param i
	 *            the current position to read
	 * @return the array of chars from a \x01 escape sequence
	 */
	private static char[] stripUChar(String text, final int end, int i) {
		if (i + 4 < end) {
			return charFromHexaCode(text.charAt(i + 1), text.charAt(i + 2), text.charAt(i + 3), text
					.charAt(i + 4));
		} else if (i + 3 < end) {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_U + text.charAt(i + 1)
					+ text.charAt(i + 2) + text.charAt(i + 3));
		} else if (i + 2 < end) {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_U + text.charAt(i + 1)
					+ text.charAt(i + 2));
		} else if (i + 1 < end) {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_U + text.charAt(i + 1));
		} else {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_U);
		}
	}

	/**
	 * Gets the array of chars from a \x01 escape sequence.
	 * 
	 * @param c0
	 *            the first char
	 * @param c1
	 *            the second char
	 * @return the array of chars from a \x01 escape sequence
	 */
	private static char[] charFromHexaCode(char c0, char c1) {
		try {
			final int codePoint = Integer.parseInt(String.valueOf(new char[] {c0, c1, }), 16);
			if (Character.isValidCodePoint(codePoint)) {
				return Character.toChars(codePoint);
			} else {
				throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_X + c0 + c1);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_X + c0 + c1);
		}
	}

	/**
	 * Gets the array of chars from a \u0123 escape sequence.
	 * 
	 * @param c0
	 *            the first char
	 * @param c1
	 *            the second char
	 * @param c2
	 *            the third char
	 * @param c3
	 *            the forth char
	 * @return the array of chars from a \u0123 escape sequence
	 */
	private static char[] charFromHexaCode(char c0, char c1, char c2, char c3) {
		try {
			final int codePoint = Integer.parseInt(String.valueOf(new char[] {c0, c1, c2, c3 }), 16);
			if (Character.isValidCodePoint(codePoint)) {
				return Character.toChars(codePoint);
			} else {
				throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_U + c0 + c1 + c2 + c3);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_ESCAPE_SEQUENCE + ESCAPE_U + c0 + c1 + c2 + c3);
		}
	}

	/**
	 * Creates a boolean literal given an double.
	 * 
	 * @param bool
	 *            the value
	 * @return an boolean literal
	 */
	public BooleanLiteral booleanLiteral(boolean bool) {
		BooleanLiteral literal = (BooleanLiteral)EcoreUtil.create(AstPackage.Literals.BOOLEAN_LITERAL);
		literal.setValue(bool);
		return literal;
	}

	/**
	 * Creates a new collection type literal given the collection type and the element type.
	 * 
	 * @param type
	 *            the type of the collection (list or set)
	 * @param elementType
	 *            the type of the element
	 * @return a new collection type literal
	 */
	public CollectionTypeLiteral collectionTypeLiteral(Object type, TypeLiteral elementType) {
		if (type != List.class && type != Set.class) {
			throw new IllegalArgumentException("collection type must be either list or set.");
		}
		CollectionTypeLiteral literal = (CollectionTypeLiteral)EcoreUtil
				.create(AstPackage.Literals.COLLECTION_TYPE_LITERAL);
		literal.setValue(type);
		literal.setElementType(elementType);
		return literal;
	}

	/**
	 * Creates a new type literal given the class or the EClass.
	 * 
	 * @param type
	 *            the class or the EClass
	 * @return a new type literal given the class or the EClass.
	 */
	public TypeLiteral typeLiteral(Object type) {
		if (!(type instanceof Class<?>) && !(type instanceof EClassifier)) {
			throw new IllegalArgumentException("type argument must be a Class or an EClass instance : "
					+ type);
		}
		TypeLiteral typeLiteral = (TypeLiteral)EcoreUtil.create(AstPackage.Literals.TYPE_LITERAL);
		typeLiteral.setValue(type);
		return typeLiteral;
	}

	/**
	 * Creates a new call given it's type, the name of the service and the arguments.
	 * 
	 * @param type
	 *            type of call
	 * @param serviceName
	 *            the name of the called service
	 * @param args
	 *            the arguments
	 * @return an instance of Call.
	 */
	public Call callService(CallType type, String serviceName, Expression... args) {
		Call call = (Call)EcoreUtil.create(AstPackage.Literals.CALL);
		call.setType(type);
		call.setServiceName(serviceName);
		call.getArguments().addAll(Lists.newArrayList(args));
		return call;
	}

	/**
	 * Creates a new varRef.
	 * 
	 * @param name
	 *            the name of the variable
	 * @return a new varref
	 */
	public VarRef varRef(String name) {

		VarRef ref = (VarRef)EcoreUtil.create(AstPackage.Literals.VAR_REF);
		ref.setVariableName(name);
		return ref;
	}

	/**
	 * Creates a new {@link FeatureAccess}.
	 * 
	 * @param target
	 *            the target expression
	 * @param featureName
	 *            the feature name
	 * @return a new {@link FeatureAccess}
	 */
	public FeatureAccess featureAccess(Expression target, String featureName) {
		FeatureAccess res = (FeatureAccess)EcoreUtil.create(AstPackage.Literals.FEATURE_ACCESS);
		res.setTarget(target);
		res.setFeatureName(featureName);
		return res;
	}

	/**
	 * Creates a new {@link Lambda}.
	 * 
	 * @param expression
	 *            the lambda expression
	 * @param parameters
	 *            the parameters.
	 * @return a new {@link Lambda}.
	 */
	public Lambda lambda(Expression expression, VariableDeclaration... parameters) {
		Lambda lambda = (Lambda)EcoreUtil.create(AstPackage.Literals.LAMBDA);
		lambda.setExpression(expression);
		List<VariableDeclaration> params = Lists.newArrayList(parameters);
		lambda.getParameters().addAll(params);
		return lambda;
	}

	/**
	 * Creates a new {@link ErrorExpression}.
	 * 
	 * @return a new {@link ErrorExpression}.
	 */
	public ErrorExpression errorExpression() {
		return (ErrorExpression)EcoreUtil.create(AstPackage.Literals.ERROR_EXPRESSION);
	}

	/**
	 * Creates a new {@link ErrorTypeLiteral}.
	 * 
	 * @param segments
	 *            known segments of the {@link TypeLiteral}
	 * @return a new {@link ErrorTypeLiteral}.
	 */
	public ErrorTypeLiteral errorTypeLiteral(String... segments) {
		final ErrorTypeLiteral result = (ErrorTypeLiteral)EcoreUtil
				.create(AstPackage.Literals.ERROR_TYPE_LITERAL);

		for (String segment : segments) {
			if (segment != null && segment.length() != 0) {
				result.getSegments().add(segment);
			}
		}

		return result;
	}

	/**
	 * Creates a new {@link ErrorEnumLiteral}.
	 * 
	 * @param segments
	 *            known segments of the {@link ErrorEnumLiteral}
	 * @return a new {@link ErrorEnumLiteral}.
	 */
	public ErrorEnumLiteral errorEnumLiteral(String... segments) {
		final ErrorEnumLiteral result = (ErrorEnumLiteral)EcoreUtil
				.create(AstPackage.Literals.ERROR_ENUM_LITERAL);

		for (String segment : segments) {
			if (segment != null && segment.length() != 0) {
				result.getSegments().add(segment);
			}
		}

		return result;
	}

	/**
	 * Creates a new {@link ErrorFeatureAccessOrCall}.
	 * 
	 * @param target
	 *            the {@link ErrorFeatureAccessOrCall#getTarget() target}
	 * @return a new {@link ErrorFeatureAccessOrCall}
	 */
	public ErrorFeatureAccessOrCall errorFeatureAccessOrCall(Expression target) {
		final ErrorFeatureAccessOrCall result = (ErrorFeatureAccessOrCall)EcoreUtil
				.create(AstPackage.Literals.ERROR_FEATURE_ACCESS_OR_CALL);

		result.setTarget(target);

		return result;
	}

	/**
	 * Creates a new {@link ErrorCollectionCall}.
	 * 
	 * @param target
	 *            the {@link ErrorCollectionCall#getTarget() target}
	 * @return a new {@link ErrorCollectionCall}
	 */
	public ErrorCollectionCall errorCollectionCall(Expression target) {
		final ErrorCollectionCall result = (ErrorCollectionCall)EcoreUtil
				.create(AstPackage.Literals.ERROR_COLLECTION_CALL);

		result.setTarget(target);

		return result;
	}

	/**
	 * Creates a new {@link VariableDeclaration}.
	 * 
	 * @param name
	 *            the {@link VariableDeclaration#getName() name}
	 * @param expression
	 *            the {@link VariableDeclaration#getExpression() expression}
	 * @return a new {@link VariableDeclaration}
	 */
	public VariableDeclaration variableDeclaration(String name, Expression expression) {
		final VariableDeclaration result = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);

		result.setName(name);
		result.setExpression(expression);

		return result;
	}

	/**
	 * Creates a new {@link VariableDeclaration}.
	 * 
	 * @param name
	 *            the {@link VariableDeclaration#getName() name}
	 * @param type
	 *            the {@link VariableDeclaration#getType() type}
	 * @param expression
	 *            the {@link VariableDeclaration#getExpression() expression}
	 * @return a new {@link VariableDeclaration}
	 */
	public VariableDeclaration variableDeclaration(String name, TypeLiteral type, Expression expression) {
		final VariableDeclaration result = variableDeclaration(name, expression);

		result.setType(type);

		return result;
	}

	/**
	 * Creates a new {@link NullLiteral}.
	 * 
	 * @return a new {@link NullLiteral}
	 */
	public NullLiteral nullLiteral() {
		return (NullLiteral)EcoreUtil.create(AstPackage.Literals.NULL_LITERAL);
	}

	/**
	 * Creates a new {@link SetInExtensionLiteral} with the given {@link List} of {@link Expression}.
	 * 
	 * @param expressions
	 *            the {@link List} of {@link Expression}
	 * @return a new {@link SetInExtensionLiteral} with the given {@link List} of {@link Expression}
	 */
	public SetInExtensionLiteral setInExtension(List<Expression> expressions) {
		final SetInExtensionLiteral result = (SetInExtensionLiteral)EcoreUtil
				.create(AstPackage.Literals.SET_IN_EXTENSION_LITERAL);

		result.getValues().addAll(expressions);

		return result;
	}

	/**
	 * Creates a new {@link SequenceInExtensionLiteral} with the given {@link List} of {@link Expression}.
	 * 
	 * @param expressions
	 *            the {@link List} of {@link Expression}
	 * @return a new {@link SequenceInExtensionLiteral} with the given {@link List} of {@link Expression}
	 */
	public SequenceInExtensionLiteral sequenceInExtension(List<Expression> expressions) {
		final SequenceInExtensionLiteral result = (SequenceInExtensionLiteral)EcoreUtil
				.create(AstPackage.Literals.SEQUENCE_IN_EXTENSION_LITERAL);

		result.getValues().addAll(expressions);

		return result;
	}

	/**
	 * Creates a new {@link ErrorVariableDeclaration}.
	 * 
	 * @param variableName
	 *            the {@link ErrorVariableDeclaration#getName() variable name}
	 * @param type
	 *            the {@link ErrorVariableDeclaration#getType() variable type}
	 * @param expression
	 *            the {@link ErrorVariableDeclaration#getExpression() variable expression}
	 * @return a new {@link ErrorVariableDeclaration}
	 */
	public ErrorVariableDeclaration errorVariableDeclaration(String variableName, TypeLiteral type,
			Expression expression) {
		ErrorVariableDeclaration result = (ErrorVariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.ERROR_VARIABLE_DECLARATION);

		result.setName(variableName);
		result.setType(type);
		result.setExpression(expression);

		return result;
	}

	/**
	 * Creates a new {@link Conditional} operator instance.
	 * 
	 * @param predicate
	 *            the predicate
	 * @param trueBranch
	 *            the true branch
	 * @param falseBranch
	 *            the false branch
	 * @return a conditional operator with the specified predicate, true branche and false branch.
	 */
	public Conditional conditional(Expression predicate, Expression trueBranch, Expression falseBranch) {
		Conditional result = (Conditional)EcoreUtil.create(AstPackage.Literals.CONDITIONAL);
		result.setPredicate(predicate);
		result.setTrueBranch(trueBranch);
		result.setFalseBranch(falseBranch);
		return result;
	}

	/**
	 * Creates a new {@link Binding} instance.
	 * 
	 * @param varName
	 *            the variable name of the binding
	 * @param type
	 *            the binding type it can be <code>null</code>
	 * @param expression
	 *            the expression which value is bound to the variable.
	 * @return the new {@link Binding} instance created.
	 */
	public Binding binding(String varName, TypeLiteral type, Expression expression) {
		Binding binding = (Binding)EcoreUtil.create(AstPackage.Literals.BINDING);
		binding.setName(varName);
		binding.setType(type);
		binding.setValue(expression);
		return binding;
	}

	/**
	 * Creates a new {@link Let} operator.
	 * 
	 * @param body
	 *            the body of the let
	 * @param bindings
	 *            the bindings of the let
	 * @return the new {@link Let} created.
	 */
	public Let let(Expression body, Binding... bindings) {
		Let let = (Let)EcoreUtil.create(AstPackage.Literals.LET);
		let.setBody(body);
		for (Binding binding : bindings) {
			let.getBindings().add(binding);
		}
		return let;
	}

	/**
	 * Creates a new {@link TypeSetLiteral}.
	 * 
	 * @param types
	 *            the {@link TypeSetLiteral#getValues() types}
	 * @return the created {@link TypeSetLiteral}
	 */
	public TypeSetLiteral typeSetLiteral(List<TypeLiteral> types) {
		final TypeSetLiteral typeSetLiteral = (TypeSetLiteral)EcoreUtil
				.create(AstPackage.Literals.TYPE_SET_LITERAL);

		typeSetLiteral.getTypes().addAll(types);

		return typeSetLiteral;
	}

	/**
	 * Creates a new {@link ErrorBinding}.
	 * 
	 * @param name
	 *            the {@link ErrorBinding#getName() name} it can be <code>null</code>
	 * @param type
	 *            the binding type it can be <code>null</code>
	 * @return the new {@link ErrorBinding} created.
	 */
	public ErrorBinding errorBinding(String name, TypeLiteral type) {
		final ErrorBinding errorBinding = (ErrorBinding)EcoreUtil.create(AstPackage.Literals.ERROR_BINDING);

		errorBinding.setName(name);
		errorBinding.setType(type);

		return errorBinding;
	}

}
