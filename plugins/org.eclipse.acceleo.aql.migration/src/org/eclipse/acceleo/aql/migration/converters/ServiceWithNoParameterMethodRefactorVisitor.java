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
final class ServiceWithNoParameterMethodRefactorVisitor extends ASTVisitor {

	/**
	 * The {@link IDocument} of the source code.
	 */
	private final IDocument document;

	/**
	 * The service name.
	 */
	private final String serviceName;

	/**
	 * Constructor.
	 * 
	 * @param document
	 *            the {@link IDocument} representing the Java source code
	 * @param serviceName
	 *            the service name
	 */
	ServiceWithNoParameterMethodRefactorVisitor(IDocument document, String serviceName) {
		this.document = document;
		this.serviceName = serviceName;
	}

	@Override
	public boolean visit(MethodDeclaration method) {
		if (method.getName().getIdentifier().equals(serviceName) && method.parameters().isEmpty()) {
			final ASTRewrite rewrite = ASTRewrite.create(method.getAST());

			final MethodDeclaration newMethod = method.getAST().newMethodDeclaration();
			// newMethod: <serviceModifiers> <serviceType> <serviceName>JavaService(Object object)
			for (Object modifier : method.modifiers()) {
				newMethod.modifiers().add(rewrite.createCopyTarget((ASTNode)modifier));
			}
			newMethod.setReturnType2((Type)rewrite.createCopyTarget(method.getReturnType2()));
			newMethod.setName(newMethod.getAST().newSimpleName(serviceName
					+ ExpressionConverter.JAVA_SERVICE));
			final SingleVariableDeclaration parameter = newMethod.getAST().newSingleVariableDeclaration();
			parameter.setName(newMethod.getAST().newSimpleName("object"));
			parameter.setType(newMethod.getAST().newSimpleType(newMethod.getAST().newName("Object")));
			newMethod.parameters().add(parameter);

			// newMethod body: return <serviceName>();
			// or <serviceName>();
			final Block body = newMethod.getAST().newBlock();
			final MethodInvocation methodInvocation = newMethod.getAST().newMethodInvocation();
			methodInvocation.setName(newMethod.getAST().newSimpleName(serviceName));
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
}
