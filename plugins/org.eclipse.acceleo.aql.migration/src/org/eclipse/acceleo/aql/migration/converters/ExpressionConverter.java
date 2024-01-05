/*******************************************************************************
 * Copyright (c) 2017, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.converters;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.AcceleoFactory;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.aql.migration.MigrationException;
import org.eclipse.acceleo.aql.migration.converters.ModuleConverter.ImpliciteSelfFrame;
import org.eclipse.acceleo.aql.migration.converters.utils.OperationUtils;
import org.eclipse.acceleo.aql.migration.converters.utils.TypeUtils;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EOperationImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ocl.ecore.BooleanLiteralExp;
import org.eclipse.ocl.ecore.CollectionItem;
import org.eclipse.ocl.ecore.CollectionLiteralExp;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.EcorePackage;
import org.eclipse.ocl.ecore.EnumLiteralExp;
import org.eclipse.ocl.ecore.IfExp;
import org.eclipse.ocl.ecore.IntegerLiteralExp;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.ecore.LetExp;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.OrderedSetType;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.RealLiteralExp;
import org.eclipse.ocl.ecore.SequenceType;
import org.eclipse.ocl.ecore.SetType;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.TypeExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;

/**
 * A converter dedicated to OCLExpressions.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class ExpressionConverter extends AbstractConverter {

	/**
	 * The java service suffix.
	 */
	public static final String JAVA_SERVICE = "JavaService";

	/**
	 * The self variable.
	 */
	private static final String SELF_VARIABLE_NAME = "self";

	/**
	 * The current implicit self variable name stack.
	 */
	private Deque<ImpliciteSelfFrame> implicitSelfStack;

	/**
	 * The trget folder {@link Path}.
	 */
	private final Path targetFolderPath;

	/**
	 * The counter of created variable.
	 */
	private int varCount;

	/**
	 * The {@link Map} of Java service {@link Call} to there class qualified name.
	 */
	private Map<Call, String> javaServiceCalls = new HashMap<Call, String>();

	/**
	 * Constructor.
	 * 
	 * @param targetFolderPath
	 *            the target folder {@link Path}
	 */
	public ExpressionConverter(Path targetFolderPath) {
		this.targetFolderPath = targetFolderPath;
	}

	/**
	 * Gets the {@link Map} of Java service {@link Call} to there class qualified name.
	 * 
	 * @return the {@link Map} of Java service {@link Call} to there class qualified name
	 */
	public Map<Call, String> getJavaServiceCalls() {
		return javaServiceCalls;
	}

	/**
	 * Converts the given {@link OCLExpression} to a {@link Statement}.
	 * 
	 * @param input
	 *            the expression to convert
	 * @param implicitSelfStack
	 *            the current implicit self variable name stack
	 * @return the statement
	 */
	public Statement convertToStatement(OCLExpression input, Deque<ImpliciteSelfFrame> implicitSelfStack) {
		Statement output = AcceleoFactory.eINSTANCE.createExpressionStatement();
		((ExpressionStatement)output).setExpression(convertToExpression(input, implicitSelfStack));
		return output;
	}

	/**
	 * Converts the given {@link OCLExpression} to an {@link Expression}.
	 * 
	 * @param inputExpression
	 *            the expression to convert
	 * @param implicitSelfStack
	 *            the current implicit self variable name stack
	 * @return the Acceleo 4 expression
	 */
	public org.eclipse.acceleo.Expression convertToExpression(OCLExpression inputExpression,
			Deque<ImpliciteSelfFrame> implicitSelfStack) {
		org.eclipse.acceleo.Expression outputExpression = AcceleoFactory.eINSTANCE.createExpression();
		this.implicitSelfStack = implicitSelfStack;
		try {
			outputExpression.setAst(createAstResult((Expression)convert(inputExpression)));
		} finally {

		}
		return outputExpression;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.migration.converters.AbstractConverter#convert(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected Object convert(EObject input) {
		Object output = null;
		switch (input.eClass().getClassifierID()) {
			case EcorePackage.VARIABLE:
				output = caseVariable((Variable)input);
				break;
			case EcorePackage.PROPERTY_CALL_EXP:
				output = casePropertyCallExp((PropertyCallExp)input);
				break;
			case EcorePackage.VARIABLE_EXP:
				output = caseVariableExp((VariableExp)input);
				break;
			case MtlPackage.TEMPLATE_INVOCATION:
				output = caseTemplateInvocation((TemplateInvocation)input);
				break;
			case MtlPackage.QUERY_INVOCATION:
				output = caseQueryInvocation((QueryInvocation)input);
				break;
			case EcorePackage.ITERATOR_EXP:
				output = caseIteratorExp((IteratorExp)input);
				break;
			case EcorePackage.TYPE_EXP:
				output = TypeUtils.createTypeLiteral(((TypeExp)input).getReferredType());
				break;
			case EcorePackage.OPERATION_CALL_EXP:
				output = caseOperationCallExp((OperationCallExp)input);
				break;
			case EcorePackage.STRING_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createStringLiteral();
				((StringLiteral)output).setValue(((StringLiteralExp)input).getStringSymbol());
				break;
			case EcorePackage.INTEGER_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createIntegerLiteral();
				((IntegerLiteral)output).setValue(((IntegerLiteralExp)input).getIntegerSymbol());
				break;
			case EcorePackage.BOOLEAN_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createBooleanLiteral();
				((BooleanLiteral)output).setValue(((BooleanLiteralExp)input).getBooleanSymbol());
				break;
			case EcorePackage.REAL_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createRealLiteral();
				((RealLiteral)output).setValue(((RealLiteralExp)input).getRealSymbol());
				break;
			case EcorePackage.ENUM_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createEnumLiteral();
				((EnumLiteral)output).setEPackageName(((EnumLiteralExp)input).getReferredEnumLiteral()
						.getEEnum().getEPackage().getName());
				((EnumLiteral)output).setEEnumName(((EnumLiteralExp)input).getReferredEnumLiteral().getEEnum()
						.getName());
				((EnumLiteral)output).setEEnumLiteralName(((EnumLiteralExp)input).getReferredEnumLiteral()
						.getName());
				break;
			case EcorePackage.NULL_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createNullLiteral();
				break;
			case EcorePackage.COLLECTION_LITERAL_EXP:
				output = convertCollectionLiteralExp((CollectionLiteralExp)input);
				break;
			case EcorePackage.IF_EXP:
				output = caseIfExp((IfExp)input);
				break;
			case EcorePackage.COLLECTION_ITEM:
				output = convert(((CollectionItem)input).getItem());
				break;
			case EcorePackage.LET_EXP:
				output = caseLetExp((LetExp)input);
				break;
			// case EcorePackage.COLLECTION_RANGE:
			// output = convert(((CollectionItem)input).getItem());
			// break;
			default:
				throw new MigrationException(input);
		}
		return output;
	}

	private Object caseLetExp(LetExp input) {
		final Let output = AstFactory.eINSTANCE.createLet();

		output.setBody((Expression)convert(input.getIn()));

		if (input.getVariable() != null) {
			final Binding binding = AstFactory.eINSTANCE.createBinding();
			binding.setName(input.getVariable().getName());
			binding.setType(TypeUtils.createTypeLiteral(input.getVariable().getType()));
			binding.setValue((Expression)convert(input.getVariable().getInitExpression()));
			output.getBindings().add(binding);
		}

		return output;
	}

	private Object caseIfExp(IfExp input) {
		Conditional output = AstFactory.eINSTANCE.createConditional();

		output.setPredicate((Expression)convert(input.getCondition()));
		output.setTrueBranch((Expression)convert(input.getThenExpression()));
		output.setFalseBranch((Expression)convert(input.getElseExpression()));

		return output;
	}

	private Object caseVariable(Variable input) {
		VariableDeclaration output = AstFactory.eINSTANCE.createVariableDeclaration();
		output.setName(input.getName());
		if (output.getExpression() != null) {
			output.setExpression((Expression)convert(input.getInitExpression()));
		}
		return output;
	}

	private Expression caseVariableExp(VariableExp input) {
		Expression output = null;
		// if (input.getReferredVariable().eContainer() instanceof IteratorExp) {
		// IteratorExp iterator = (IteratorExp)input.getReferredVariable().eContainer();
		// output = (Expression)convert(iterator.getSource());
		// } else {
		output = AstFactory.eINSTANCE.createVarRef();
		String variableName = input.getReferredVariable().getName();
		if (implicitSelfStack != null && SELF_VARIABLE_NAME.equals(variableName)) {
			variableName = implicitSelfStack.peekLast().getImplicitSelfName();
		}
		((VarRef)output).setVariableName(variableName);
		// }
		return output;
	}

	private Expression casePropertyCallExp(PropertyCallExp input) {
		Call output = AstFactory.eINSTANCE.createCall();
		output.setServiceName(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME);
		if (input.getSource().getType() instanceof CollectionType) {
			output.setType(CallType.COLLECTIONCALL);
		} else {
			output.setType(CallType.CALLORAPPLY);
		}
		output.getArguments().add((Expression)convert(input.getSource()));
		StringLiteral propertyName = AstFactory.eINSTANCE.createStringLiteral();
		if (!input.getReferredProperty().eIsProxy()) {
			propertyName.setValue(input.getReferredProperty().getName());
		} else {
			final URI proxyURI = ((EStructuralFeatureImpl)input.getReferredProperty()).eProxyURI();
			final String[] segments = proxyURI.fragment().split("/");
			propertyName.setValue(segments[segments.length - 1]);
		}
		output.getArguments().add(propertyName);
		return output;
	}

	private Expression caseOperationCallExp(OperationCallExp input) {
		final Expression res;

		if ("oclIsUndefined".equals(input.getReferredOperation().getName())) {
			Call output = AstFactory.eINSTANCE.createCall();
			output.setServiceName(AstBuilderListener.EQUALS_SERVICE_NAME);
			output.setType(CallType.CALLSERVICE);
			output.getArguments().add((Expression)convert(input.getSource()));
			output.getArguments().add(AstFactory.eINSTANCE.createNullLiteral());
			res = output;
		} else if (isIntegerDivOpCall(input)) {
			res = convertIntegerDivOpCall(input);
		} else if (isInvokeCall(input)) {
			res = convertInvokeCall(input);
		} else if (isOclAsSetCall(input)) {
			res = convertOclAsSetCall(input);
		} else if (isAddAllCall(input)) {
			res = convertAddAllCall(input);
		} else if (isRemoveAllCall(input)) {
			res = convertRemoveAllCall(input);
		} else if (isSelectByKindOrTypeCall(input)) {
			res = convertSelectByKindOrTypeCall(input);
		} else if (isTokenizeLineCall(input)) {
			res = convertTokenizeLineCall(input);
		} else if (isCurrentCall(input)) {
			res = convertCurrentCall(input);
		} else {
			Call output = OperationUtils.createCall(input);
			output.getArguments().add((Expression)convert(input.getSource()));
			map(input.getArgument(), output.getArguments());
			res = output;
		}

		return res;
	}

	private Expression convertCurrentCall(OperationCallExp input) {
		final Expression res;

		String variableName;
		org.eclipse.ocl.expressions.OCLExpression<EClassifier> firstArgument = input.getArgument().get(0);
		if (firstArgument instanceof IntegerLiteralExp) {
			int index = ((IntegerLiteralExp)firstArgument).getIntegerSymbol();
			variableName = getCurrentVariableName(index);
		} else if (firstArgument instanceof TypeExp) {
			final EClassifier eClassifier = ((TypeExp)firstArgument).getReferredType();
			variableName = getCurrentVariableName(eClassifier);
		} else {
			variableName = null;
		}

		if (variableName != null) {
			res = AstFactory.eINSTANCE.createVarRef();
			((VarRef)res).setVariableName(variableName);
		} else {
			// fallback to classic conversion
			Call output = OperationUtils.createCall(input);
			output.getArguments().add((Expression)convert(input.getSource()));
			map(input.getArgument(), output.getArguments());
			res = output;
		}

		return res;
	}

	private String getCurrentVariableName(EClassifier eClassifier) {
		String res = null;

		final Iterator<ImpliciteSelfFrame> it = implicitSelfStack.descendingIterator();
		while (it.hasNext()) {
			final ImpliciteSelfFrame frame = it.next();
			if (frame.getInput() instanceof ForBlock) {
				final ForBlock forBlock = (ForBlock)frame.getInput();
				final EClassifier forType = forBlock.getLoopVariable().getType();
				if (eClassifier == forType || (eClassifier instanceof EClass && forType instanceof EClass
						&& ((EClass)forType).isSuperTypeOf((EClass)eClassifier))) {
					res = frame.getImplicitSelfName();
					break;
				}
			}
		}

		return res;
	}

	private String getCurrentVariableName(int index) {
		String res = null;

		int localIndex = index;
		final Iterator<ImpliciteSelfFrame> it = implicitSelfStack.descendingIterator();
		ImpliciteSelfFrame forParentFrame = null;
		boolean wasForFrame = false;
		while (it.hasNext()) {
			final ImpliciteSelfFrame frame = it.next();
			if (wasForFrame) {
				forParentFrame = frame;
			}
			if (frame.getInput() instanceof ForBlock) {
				if (localIndex == 0) {
					res = frame.getImplicitSelfName();
					break;
				} else {
					localIndex--;
					wasForFrame = true;
				}
			} else {
				wasForFrame = false;
			}
		}
		if (res == null && forParentFrame != null) {
			res = forParentFrame.getImplicitSelfName();
		}

		return res;
	}

	private boolean isCurrentCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && ("current".equals(referredOperation.getName()))
		/* && "oclstdlib_OclAny_Class".equals(((EClass)referredOperation.eContainer()).getName()) */;
	}

	private Expression convertTokenizeLineCall(OperationCallExp input) {
		final Call res = AstFactory.eINSTANCE.createCall();
		res.setType(CallType.CALLSERVICE);
		res.setServiceName("tokenize");
		res.getArguments().add((Expression)convert(input.getSource()));

		final StringLiteral stringLiteral = AstFactory.eINSTANCE.createStringLiteral();
		stringLiteral.setValue("");
		final Call lineSeparator = AstFactory.eINSTANCE.createCall();
		lineSeparator.setType(CallType.CALLSERVICE);
		lineSeparator.setServiceName("lineSeparator");
		lineSeparator.getArguments().add(stringLiteral);
		res.getArguments().add(lineSeparator);

		return res;
	}

	private boolean isTokenizeLineCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && ("tokenizeLine".equals(referredOperation.getName()))
				&& "oclstdlib_String_Class".equals(((EClass)referredOperation.eContainer()).getName());
	}

	private Expression convertSelectByKindOrTypeCall(OperationCallExp input) {
		final Call res = AstFactory.eINSTANCE.createCall();
		res.setType(CallType.COLLECTIONCALL);
		res.setServiceName("select");
		res.getArguments().add((Expression)convert(input.getSource()));

		final Lambda lambda = AstFactory.eINSTANCE.createLambda();

		final Call call = AstFactory.eINSTANCE.createCall();
		call.setType(CallType.CALLSERVICE);
		if ("selectByKind".equals(input.getReferredOperation().getName())) {
			call.setServiceName("oclIsKindOf");
		} else {
			call.setServiceName("oclIsTypeOf");
		}

		final VariableDeclaration varDeclaration = AstFactory.eINSTANCE.createVariableDeclaration();
		varDeclaration.setName("var" + varCount++);
		lambda.getParameters().add(varDeclaration);
		final VarRef varRef = AstFactory.eINSTANCE.createVarRef();
		varRef.setVariableName(varDeclaration.getName());
		call.getArguments().add(varRef);
		call.getArguments().add((Expression)convert(input.getArgument().get(0)));
		lambda.setExpression(call);
		res.getArguments().add(lambda);

		return res;
	}

	private boolean isSelectByKindOrTypeCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		// CHECKSTYLE:OFF
		return referredOperation != null && ("selectByKind".equals(referredOperation.getName())
				|| "selectByType".equals(referredOperation.getName())) && ("OrderedSet(T)_Class".equals(
						((EClass)referredOperation.eContainer()).getName()) || "Sequence(T)_Class".equals(
								((EClass)referredOperation.eContainer()).getName()));
		// CHECKSTYLE:ON
	}

	private Expression convertRemoveAllCall(OperationCallExp input) {
		final Call res = OperationUtils.createCall(input);

		res.setServiceName("sub");
		res.getArguments().add((Expression)convert(input.getSource()));
		map(input.getArgument(), res.getArguments());

		return res;
	}

	private boolean isRemoveAllCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && ("removeAll".equals(referredOperation.getName()))
				&& "oclstdlib_Collection(T)_Class".equals(((EClass)referredOperation.eContainer()).getName());
	}

	private Expression convertAddAllCall(OperationCallExp input) {
		final Call res = OperationUtils.createCall(input);

		res.setServiceName("add");
		res.getArguments().add((Expression)convert(input.getSource()));
		map(input.getArgument(), res.getArguments());

		return res;
	}

	private boolean isAddAllCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && ("addAll".equals(referredOperation.getName()))
				&& "oclstdlib_Collection(T)_Class".equals(((EClass)referredOperation.eContainer()).getName());
	}

	/**
	 * Converts the given integer div operator {@link OperationCallExp}.
	 * 
	 * @param input
	 *            the oclAsSet {@link OperationCallExp}
	 * @return the converted integer div operator {@link OperationCallExp}
	 */
	private Expression convertIntegerDivOpCall(OperationCallExp input) {
		Call res = OperationUtils.createCall(input);

		final Expression leftOperand = (Expression)convert(input.getSource());
		final Expression rightOperand = (Expression)convert(input.getArgument().get(0));
		final Call leftToDouble = AstPackage.eINSTANCE.getAstFactory().createCall();
		leftToDouble.setServiceName("toDouble");
		leftToDouble.getArguments().add(leftOperand);
		final Call rightToDouble = AstPackage.eINSTANCE.getAstFactory().createCall();
		rightToDouble.setServiceName("toDouble");
		rightToDouble.getArguments().add(rightOperand);
		res.getArguments().add(leftToDouble);
		res.getArguments().add(rightToDouble);

		return res;
	}

	/**
	 * Tells if the given {@link OperationCallExp} is an integer div operator call.
	 * 
	 * @param input
	 *            the {@link OperationCallExp} to check
	 * @return <code>true</code> if the given {@link OperationCallExp} is an integer div operator call,
	 *         <code>false</code> otherwise
	 */
	private boolean isIntegerDivOpCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		// CHECKSTYLE:OFF
		return referredOperation != null && ("/".equals(referredOperation.getName()) && referredOperation
				.eContainer() instanceof EClass && "Integer_Class".equals(((EClass)referredOperation
						.eContainer()).getName()) || referredOperation.eIsProxy()
								&& ((EOperationImpl)referredOperation).eProxyURI().toString().equals(
										"http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore#/0/Integer_Class/%2F"));
		// CHECKSTYLE:ON
	}

	/**
	 * Converts the given oclAsSet {@link OperationCallExp}.
	 * 
	 * @param input
	 *            the oclAsSet {@link OperationCallExp}
	 * @return the converted oclAsSet {@link OperationCallExp}
	 */
	private Expression convertOclAsSetCall(OperationCallExp input) {
		Call res = AstFactory.eINSTANCE.createCall();

		res.setServiceName("asSet");
		res.setType(CallType.COLLECTIONCALL);
		res.getArguments().add((Expression)convert(input.getSource()));

		return res;
	}

	/**
	 * Tells if the given {@link OperationCallExp} is an oclAsSet call.
	 * 
	 * @param input
	 *            the {@link OperationCallExp} to check
	 * @return <code>true</code> if the given {@link OperationCallExp} is an oclAsSet call, <code>false</code>
	 *         otherwise
	 */
	private boolean isOclAsSetCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && "oclAsSet".equals(referredOperation.getName())
				&& referredOperation.eContainer() instanceof EClass && "oclstdlib".equals(
						((EPackage)referredOperation.eContainer().eContainer()).getName());
	}

	/**
	 * Converts the given invoke {@link OperationCallExp}.
	 * 
	 * @param input
	 *            the invoke call
	 * @return the converted invoke {@link OperationCallExp}
	 */
	private Expression convertInvokeCall(OperationCallExp input) {
		final Call res = OperationUtils.createCall(input);

		res.setType(CallType.CALLSERVICE);
		final String serviceSignature = ((org.eclipse.ocl.expressions.StringLiteralExp<EClassifier>)input
				.getArgument().get(1)).getStringSymbol();
		final String serviceName = serviceSignature.substring(0, serviceSignature.indexOf("("));
		res.setServiceName(serviceName);
		map(((CollectionLiteralExp)input.getArgument().get(2)).getPart(), res.getArguments());
		final String serviceClassName = ((org.eclipse.ocl.expressions.StringLiteralExp<EClassifier>)input
				.getArgument().get(0)).getStringSymbol();
		if (res.getArguments().isEmpty()) {
			res.setServiceName(serviceName + JAVA_SERVICE);
			final String varName = ((Query)input.eContainer()).getParameter().get(0).getName();
			final VarRef varRef = AstFactory.eINSTANCE.createVarRef();
			varRef.setVariableName(varName);
			res.getArguments().add(varRef);
			try {
				refactorService(serviceClassName, serviceName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			javaServiceCalls.put(res, serviceClassName);
		}

		return res;
	}

	/**
	 * Renames the service and add an {@link Object} parameter.
	 * 
	 * @param serviceClassName
	 *            the service Class name
	 * @param serviceName
	 *            the service name
	 * @throws IOException
	 *             if the java file can't be read or written
	 */
	private void refactorService(String serviceClassName, String serviceName) throws IOException {
		ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
		final File javaFile = new File(targetFolderPath + FileSystems.getDefault().getSeparator()
				+ serviceClassName.replace(".", FileSystems.getDefault().getSeparator()) + ".java");
		if (javaFile.exists()) {
			final IDocument document = new Document(new String(Files.readAllBytes(javaFile.toPath())));
			parser.setSource(document.get().toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			cu.accept(new ServiceWithNoParameterMethodRefactorVisitor(document, serviceName));
			Files.write(javaFile.toPath(), document.get().getBytes(), StandardOpenOption.CREATE);
		}
	}

	/**
	 * Tells if the given {@link OperationCallExp} is an invoke() call.
	 * 
	 * @param input
	 *            the {@link OperationCallExp}
	 * @return <code>true</code> if the given OperationCallExp is an invoke() call, <code>false</code>
	 *         otherwise
	 */
	public boolean isInvokeCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && "invoke".equals(referredOperation.getName()) && referredOperation
				.eContainer() instanceof EClass && "oclstdlib_OclAny_Class".equals(((EClass)referredOperation
						.eContainer()).getName());
	}

	private Expression caseIteratorExp(IteratorExp input) {
		if ("collect".equals(input.getName()) && (input.getSource().getType() instanceof OrderedSetType
				|| input.getSource().getType() instanceof SetType)) {
			// we add a asSequence before to match A3 behavior
			Call asSequence = AstFactory.eINSTANCE.createCall();
			asSequence.setServiceName("asSequence");
			asSequence.setType(CallType.COLLECTIONCALL);
			asSequence.getArguments().add((Expression)convert(input.getSource()));
			return convertIterator(input, asSequence);
		} else {
			return convertIterator(input, (Expression)convert(input.getSource()));
		}
	}

	private Expression convertIterator(IteratorExp input, Expression source) {
		Call output = AstFactory.eINSTANCE.createCall();
		if (input.getSource().getType() instanceof CollectionType) {
			output.setType(CallType.COLLECTIONCALL);
		} else {
			output.setType(CallType.CALLORAPPLY);
		}
		output.setServiceName(input.getName());
		output.getArguments().add(source);

		Lambda lambda = AstFactory.eINSTANCE.createLambda();
		map(input.getIterator(), lambda.getParameters());
		lambda.setExpression((Expression)convert(input.getBody()));
		output.getArguments().add(lambda);
		return output;
	}

	private Expression caseTemplateInvocation(TemplateInvocation input) {
		Call output = AstFactory.eINSTANCE.createCall();
		output.setServiceName(input.getDefinition().getName());
		map(input.getArgument(), output.getArguments());

		final EClassifier queryReceiverType = input.getDefinition().getParameter().get(0).getEGenericType()
				.getEClassifier();
		wrapToCollectionCall(queryReceiverType, output);

		return output;
	}

	private Expression caseQueryInvocation(QueryInvocation input) {
		Call output = AstFactory.eINSTANCE.createCall();
		output.setType(CallType.CALLORAPPLY);
		output.setServiceName(input.getDefinition().getName());
		map(input.getArgument(), output.getArguments());

		final EClassifier queryReceiverType = input.getDefinition().getParameter().get(0).getEGenericType()
				.getEClassifier();
		wrapToCollectionCall(queryReceiverType, output);

		return output;
	}

	/**
	 * Wraps The given converted {@link Call} to a {@link CallType#COLLECTIONCALL collection call} if the
	 * given receiver type is a {@link CollectionType}.
	 * 
	 * @param receiverType
	 *            the revceiver {@link EClassifier}
	 * @param call
	 *            the converted {@link Call}
	 */
	private void wrapToCollectionCall(final EClassifier receiverType, Call call) {
		if (receiverType instanceof OrderedSetType) {
			call.setType(CallType.COLLECTIONCALL);
			final Expression receiver = call.getArguments().remove(0);
			if (receiver instanceof Call && "asOrderedSet".equals(((Call)receiver).getServiceName())) {
				call.getArguments().add(0, receiver);
			} else {
				final Call asOrderedSetCall = AstFactory.eINSTANCE.createCall();
				asOrderedSetCall.setServiceName("asOrderedSet");
				asOrderedSetCall.setType(CallType.COLLECTIONCALL);
				asOrderedSetCall.getArguments().add(receiver);
				call.getArguments().add(0, asOrderedSetCall);
			}
		} else if (receiverType instanceof SequenceType) {
			call.setType(CallType.COLLECTIONCALL);
			final Expression receiver = call.getArguments().remove(0);
			if (receiver instanceof Call && "asSequence".equals(((Call)receiver).getServiceName())) {
				call.getArguments().add(0, receiver);
			} else {
				final Call asOrderedSetCall = AstFactory.eINSTANCE.createCall();
				asOrderedSetCall.setServiceName("asSequence");
				asOrderedSetCall.setType(CallType.COLLECTIONCALL);
				asOrderedSetCall.getArguments().add(receiver);
				call.getArguments().add(0, asOrderedSetCall);
			}
		}
	}

	private Expression convertCollectionLiteralExp(CollectionLiteralExp input) {
		Expression output = null;
		EClassifier type = input.getEType();
		switch (type.eClass().getClassifierID()) {
			case EcorePackage.SEQUENCE_TYPE:
				output = AstFactory.eINSTANCE.createSequenceInExtensionLiteral();
				map(input.getPart(), ((SequenceInExtensionLiteral)output).getValues());
				break;
			case EcorePackage.BAG_TYPE:
				output = AstFactory.eINSTANCE.createSequenceInExtensionLiteral();
				map(input.getPart(), ((SequenceInExtensionLiteral)output).getValues());
				break;
			case EcorePackage.SET_TYPE:
				output = AstFactory.eINSTANCE.createSetInExtensionLiteral();
				map(input.getPart(), ((SetInExtensionLiteral)output).getValues());
				break;
			case EcorePackage.ORDERED_SET_TYPE:
				output = AstFactory.eINSTANCE.createSetInExtensionLiteral();
				map(input.getPart(), ((SetInExtensionLiteral)output).getValues());
				break;
			default:
				throw new MigrationException(type);
		}
		return output;
	}

}
