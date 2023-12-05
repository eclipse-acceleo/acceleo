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
package org.eclipse.acceleo.aql.migration.converters;

import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Adds a method with an {@link Object} parameter that delegates to original service without parameter.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
final class AmbiguousServiceMethodRefactorVisitor extends ASTVisitor {

	/**
	 * The {@link IDocument} of the source code.
	 */
	private final IDocument document;

	/**
	 * The service name.
	 */
	private final String serviceName;

	/**
	 * The {@link List} of arguments passed to the service.
	 */
	private final List<Expression> serviceArguments;

	/**
	 * Constructor.
	 * 
	 * @param document
	 *            the {@link IDocument} representing the Java source code
	 * @param call
	 *            the service {@link Call}
	 */
	AmbiguousServiceMethodRefactorVisitor(IDocument document, Call call) {
		this.document = document;
		this.serviceName = call.getServiceName();
		this.serviceArguments = call.getArguments();
	}

	@Override
	public boolean visit(MethodDeclaration method) {
		if (method.getName().getIdentifier().equals(serviceName) && parametersAreMatching(method.parameters(),
				serviceArguments) && !newMethodExists(method)) {
			final ASTRewrite rewrite = ASTRewrite.create(method.getAST());

			final MethodDeclaration newMethod = method.getAST().newMethodDeclaration();
			// newMethod: <serviceModifiers> <serviceType> <serviceName>JavaService(...)
			for (Object modifier : method.modifiers()) {
				newMethod.modifiers().add(rewrite.createCopyTarget((ASTNode)modifier));
			}
			newMethod.setReturnType2((Type)rewrite.createCopyTarget(method.getReturnType2()));
			newMethod.setName(newMethod.getAST().newSimpleName(serviceName
					+ ExpressionConverter.JAVA_SERVICE));
			for (Object parameter : method.parameters()) {
				newMethod.parameters().add(rewrite.createCopyTarget((ASTNode)parameter));
			}

			// newMethod body: return <serviceName>(...);
			// or <serviceName>(...);
			final Block body = newMethod.getAST().newBlock();
			final MethodInvocation methodInvocation = newMethod.getAST().newMethodInvocation();
			methodInvocation.setName(newMethod.getAST().newSimpleName(serviceName));
			for (Object parameter : method.parameters()) {
				final ASTNode argument = rewrite.createCopyTarget(((SingleVariableDeclaration)parameter)
						.getName());
				methodInvocation.arguments().add(argument);
			}
			final Statement statement;
			if (method.getReturnType2() != null && !"void".equals(method.getReturnType2().toString())) {
				final ReturnStatement returnStatement = newMethod.getAST().newReturnStatement();
				returnStatement.setExpression(methodInvocation);
				statement = returnStatement;
			} else {
				final ExpressionStatement expressionStatement = newMethod.getAST().newExpressionStatement(
						methodInvocation);
				statement = expressionStatement;
			}
			body.statements().add(statement);
			newMethod.setBody(body);

			// add the newMethod to the containing Class
			final TypeDeclaration typeDeclaration = (TypeDeclaration)method.getParent();
			final ListRewrite declarations = rewrite.getListRewrite(typeDeclaration,
					TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
			declarations.insertAfter(newMethod, method, null);
			try {
				final TextEdit edit = rewrite.rewriteAST(document, Collections.EMPTY_MAP);
				edit.apply(document);
			} catch (IllegalArgumentException | MalformedTreeException | BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Tells if the new method we want to create for the given {@link MethodDeclaration} already exists in its
	 * parent {@link TypeDeclaration}.
	 * 
	 * @param method
	 *            the {@link MethodDeclaration}
	 * @return <code>true</code> if the new method we want to create for the given {@link MethodDeclaration}
	 *         already exists in its parent {@link TypeDeclaration}, <code>false</code> otherwise
	 */
	private boolean newMethodExists(MethodDeclaration method) {
		boolean res = false;

		final String newMethodName = serviceName + ExpressionConverter.JAVA_SERVICE;
		final TypeDeclaration typeDeclaration = (TypeDeclaration)method.getParent();
		for (MethodDeclaration methodDeclaration : typeDeclaration.getMethods()) {
			if (newMethodName.equals(methodDeclaration.getName().getIdentifier()) && haveSameParameterTypes(
					method, methodDeclaration)) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * Tells if both given {@link MethodDeclaration} have the same parameter types.
	 * 
	 * @param method
	 *            first {@link MethodDeclaration}
	 * @param methodDeclaration
	 *            second {@link MethodDeclaration}
	 * @return <code>true</code> if both given {@link MethodDeclaration} have the same parameter types,
	 *         <code>false</code> otherwise
	 */
	private boolean haveSameParameterTypes(MethodDeclaration method, MethodDeclaration methodDeclaration) {
		boolean res;

		if (method.parameters().size() == methodDeclaration.parameters().size()) {
			res = true;
			for (int i = 0; i < method.parameters().size(); i++) {
				final SingleVariableDeclaration methodParameter = (SingleVariableDeclaration)method
						.parameters().get(i);
				final SingleVariableDeclaration methodDeclarationParameter = (SingleVariableDeclaration)methodDeclaration
						.parameters().get(i);
				if (!methodParameter.getType().toString().equals(methodDeclarationParameter.getType()
						.toString())) {
					res = false;
					break;
				}
			}
		} else {
			res = false;
		}

		return res;
	}

	/**
	 * Tells if the given {@link List} of arguments matches the given {@link List} of parameters.
	 * 
	 * @param parameters
	 *            the {@link List} of {@link SingleVariableDeclaration parameters} accepted by the method
	 * @param arguments
	 *            the {@link List} of {@link Expression} arguments passed to the service call
	 * @return <code>true</code> if the given {@link List} of arguments matches the given {@link List} of
	 *         parameters, <code>false</code> otherwise
	 */
	private boolean parametersAreMatching(List<SingleVariableDeclaration> parameters,
			List<Expression> arguments) {
		boolean res = true;

		if (parameters.size() == arguments.size()) {
			// TODO we should validate each expression and declaration type to see if they match
			res = true;
		} else {
			res = false;
		}

		return res;
	}
}
