/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation.quickfixes;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.quickfixes.IAstQuickFix;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.emf.ecore.util.ComposedSwitch;

public class AcceleoQuickFixesSwitch extends ComposedSwitch<List<IAstQuickFix>> {

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private IAcceleoValidationResult validationResult;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}.
	 * @param validationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param moduleQualifiedName
	 *            the {@link Module} qualified name
	 * @param moduleText
	 *            the text representation of the {@link Module}
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AcceleoQuickFixesSwitch(IQualifiedNameQueryEnvironment queryEnvironment,
			IAcceleoValidationResult validationResult, String moduleQualifiedName, String moduleText,
			String endLine) {
		super();
		addSwitch(new AqlQuickFixesSwitch(queryEnvironment, validationResult, moduleQualifiedName, moduleText,
				endLine));
		addSwitch(new ModuleQuickFixesSwitch(queryEnvironment, validationResult, moduleQualifiedName,
				moduleText, endLine));
		this.validationResult = validationResult;
	}

	/**
	 * Gets the {@link List} of {@link IAstQuickFix} for the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the node
	 * @return the {@link List} of {@link IAstQuickFix} for the given {@link ASTNode}
	 */
	public List<IAstQuickFix> getQuickFixes(ASTNode node) {
		final List<IAstQuickFix> res;

		final List<IValidationMessage> errors;
		final ASTNode localNode;
		if (node instanceof StringLiteral && node.eContainer() instanceof Call
				&& AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME.equals(((Call)node.eContainer())
						.getServiceName())) {
			final Call call = (Call)node.eContainer();
			errors = validationResult.getValidationMessages(call).stream().filter(m -> m
					.getLevel() == ValidationMessageLevel.ERROR).collect(Collectors.toList());
			localNode = call;
		} else if (node instanceof CommentBody) {
			final Comment comment = (Comment)node.eContainer();
			errors = validationResult.getValidationMessages(comment).stream().filter(m -> m
					.getLevel() == ValidationMessageLevel.ERROR).collect(Collectors.toList());
			localNode = comment;
		} else {
			errors = validationResult.getValidationMessages(node).stream().filter(m -> m
					.getLevel() == ValidationMessageLevel.ERROR).collect(Collectors.toList());
			localNode = node;
		}

		if (!errors.isEmpty()) {
			final List<IAstQuickFix> quickFixes = doSwitch(localNode);
			if (quickFixes != null) {
				res = quickFixes;
			} else {
				res = Collections.emptyList();
			}
		} else {
			res = Collections.emptyList();
		}

		return res;
	}

}
