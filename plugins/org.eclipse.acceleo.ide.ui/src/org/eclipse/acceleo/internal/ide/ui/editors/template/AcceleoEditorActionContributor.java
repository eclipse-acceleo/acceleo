/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.CommentAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.OpenDeclarationAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.CreateProtectedAreaAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.ExtractAsTemplateAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.BasicTextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Contributes global actions for the Acceleo template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoEditorActionContributor extends BasicTextEditorActionContributor {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.BasicTextEditorActionContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		if (!(part instanceof ITextEditor)) {
			return;
		}
		IActionBars actionBars = getActionBars();
		if (actionBars == null) {
			return;
		}
		OpenDeclarationAction openAction = new OpenDeclarationAction();
		openAction.setActionDefinitionId(OpenDeclarationAction.ACTION_ID);
		actionBars.setGlobalActionHandler(OpenDeclarationAction.COMMAND_ID, openAction);

		ReferencesSearchAction referencesSearchAction = new ReferencesSearchAction();
		referencesSearchAction.setActionDefinitionId(ReferencesSearchAction.CUSTOM_ACTION_ID);
		actionBars.setGlobalActionHandler(ReferencesSearchAction.CUSTOM_COMMAND_ID, referencesSearchAction);

		CommentAction commentAction = new CommentAction();
		commentAction.setActionDefinitionId(CommentAction.ACTION_ID);
		actionBars.setGlobalActionHandler(CommentAction.COMMAND_ID, commentAction);

		ExtractAsTemplateAction asTemplateAction = new ExtractAsTemplateAction();
		asTemplateAction.setActionDefinitionId(ExtractAsTemplateAction.ACTION_ID);
		actionBars.setGlobalActionHandler(ExtractAsTemplateAction.COMMAND_ID, asTemplateAction);

		CreateProtectedAreaAction protectedAreaAction = new CreateProtectedAreaAction();
		protectedAreaAction.setActionDefinitionId(CreateProtectedAreaAction.ACTION_ID);
		actionBars.setGlobalActionHandler(CreateProtectedAreaAction.COMMAND_ID, protectedAreaAction);
	}

}
