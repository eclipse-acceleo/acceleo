/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.common;

import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.CompletionItemKind;

/**
 * An {@link AcceleoSwitch} that provides the {@link CompletionItemKind} corresponding to an Acceleo AST node.
 * Note that the association is done not on the semantics, but depending on the icons used by LSP4E. See
 * org.eclipse.lsp4e.ui.LSPImages.
 * 
 * @author Florent Latombe
 */
public class AcceleoAstNodeToCompletionItemKind extends AcceleoSwitch<CompletionItemKind> {

	/**
	 * Switches on the given Acceleo {@link EClass}.
	 * 
	 * @param eClass
	 *            the (non-{@code null}) {@link EClass} to switch on.
	 * @return the {@link CompletionItemKind} corresponding to {@code eClass}.
	 */
	public CompletionItemKind doSwitchOnType(EClass eClass) {
		return this.doSwitch(eClass, null);
	}

	@Override
	public CompletionItemKind defaultCase(EObject object) {
		return CompletionItemKind.Text;
	}

	@Override
	public CompletionItemKind caseVariable(Variable object) {
		return CompletionItemKind.Property;
	}

	@Override
	public CompletionItemKind caseForStatement(ForStatement object) {
		return CompletionItemKind.Keyword;
	}

	@Override
	public CompletionItemKind caseIfStatement(IfStatement object) {
		return CompletionItemKind.Keyword;
	}

	@Override
	public CompletionItemKind caseLetStatement(LetStatement object) {
		return CompletionItemKind.Variable;
	}

	@Override
	public CompletionItemKind caseModule(Module object) {
		return CompletionItemKind.Module;
	}

	@Override
	public CompletionItemKind caseImport(Import object) {
		return CompletionItemKind.Interface;
	}

	@Override
	public CompletionItemKind caseFileStatement(FileStatement object) {
		return CompletionItemKind.File;
	}

	@Override
	public CompletionItemKind caseMetamodel(Metamodel object) {
		return CompletionItemKind.Method;
	}

	@Override
	public CompletionItemKind caseQuery(Query object) {
		return CompletionItemKind.Function;
	}

	@Override
	public CompletionItemKind caseTemplate(Template object) {
		return CompletionItemKind.Text;
	}

	@Override
	public CompletionItemKind caseBlock(Block object) {
		return CompletionItemKind.Struct;
	}

	@Override
	public CompletionItemKind caseComment(Comment object) {
		return CompletionItemKind.Class;
	}

	@Override
	public CompletionItemKind caseBlockComment(BlockComment object) {
		return CompletionItemKind.Class;
	}

	@Override
	public CompletionItemKind caseDocumentation(Documentation object) {
		return CompletionItemKind.Class;
	}

}
