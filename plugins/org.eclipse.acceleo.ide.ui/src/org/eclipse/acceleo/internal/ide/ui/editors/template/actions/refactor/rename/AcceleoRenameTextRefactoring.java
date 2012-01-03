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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ocl.ecore.LiteralExp;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class will realize the refactoring of all occurrences of a text selection.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameTextRefactoring extends Refactoring {

	/**
	 * The original text.
	 */
	private static final String ORIGINAL_TEXT = "original_text"; //$NON-NLS-1$

	/**
	 * New name.
	 */
	private static final String NEWNAME = "newName"; //$NON-NLS-1$

	/**
	 * The title of the refactoring.
	 */
	private final String title = AcceleoUIMessages
			.getString("AcceleoEditorRenameVariableRefactoring.RenameTextTitle"); //$NON-NLS-1$

	/**
	 * The error message in case of an invalid text.
	 */
	private final String invalidText = AcceleoUIMessages
			.getString("AcceleoEditorRenameTextRefactoring.NoTextSpecified"); //$NON-NLS-1$

	/**
	 * The parent.
	 */
	private ModuleElement parent;

	/**
	 * The source content.
	 */
	private AcceleoSourceContent acceleoSourceContent;

	/**
	 * The text selection.
	 */
	private ITextSelection textSelection;

	/**
	 * The new text.
	 */
	private String newName;

	/**
	 * The text file change.
	 */
	private TextFileChange textFileChange;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#getName()
	 */
	@Override
	public String getName() {
		return this.title;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (this.textSelection == null || this.textSelection.getText() == null
				|| this.textSelection.getText().length() == 0) {
			return RefactoringStatus.createErrorStatus(invalidText);
		}
		return new RefactoringStatus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		if (this.newName == null) {
			status.merge(RefactoringStatus.createErrorStatus(invalidText));
		}

		computeChange();

		return status;
	}

	/**
	 * Compute the change.
	 */
	private void computeChange() {
		this.textFileChange = new TextFileChange(title, this.acceleoSourceContent.getFile());
		MultiTextEdit edit = new MultiTextEdit();
		this.textFileChange.setEdit(edit);
		this.textFileChange.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);

		String text = this.textSelection.getText();

		if (this.parent instanceof Template) {
			Template template = (Template)this.parent;
			List<OCLExpression> body = getContainedOCLExpression(template);
			for (OCLExpression templateExpression : body) {
				if (templateExpression instanceof LiteralExp) {
					LiteralExp textExpression = (LiteralExp)templateExpression;

					int end = textExpression.getEndPosition();

					int index = textExpression.toString().indexOf(text); // textExpression.getValue().indexOf(text);
					while (index != -1 && index <= end) {
						edit.addChild(new ReplaceEdit(textExpression.getStartPosition() + index - 1,
								this.textSelection.getLength(), newName));
						index = textExpression.toString().indexOf(text, index + 1);
					}
				}
			}
		}
	}

	/**
	 * Finds recursively all the OCLExpression of a given block.
	 * 
	 * @param block
	 *            The given block
	 * @return All the OCLExpression contained in that block
	 */
	private List<OCLExpression> getContainedOCLExpression(Block block) {
		List<OCLExpression> body = block.getBody();
		if (body == null || body.size() == 0) {
			return null;
		}

		List<OCLExpression> subBody = new ArrayList<OCLExpression>();
		for (OCLExpression oclExpression : body) {
			if (oclExpression instanceof Block) {
				subBody.addAll(getContainedOCLExpression((Block)oclExpression));
			}
		}
		body.addAll(subBody);
		return body;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return this.textFileChange;
	}

	/**
	 * Sets the source content.
	 * 
	 * @param content
	 *            The source content
	 */
	public void setSourceContent(AcceleoSourceContent content) {
		this.acceleoSourceContent = content;
	}

	/**
	 * Sets the module element.
	 * 
	 * @param moduleElement
	 *            the module element
	 */
	public void setParent(ModuleElement moduleElement) {
		this.parent = moduleElement;
	}

	/**
	 * Initialize.
	 * 
	 * @param arguments
	 *            The argument of the initialization.
	 * @return The refactoring status.
	 */
	public RefactoringStatus initialize(Map<String, String> arguments) {
		final RefactoringStatus status = new RefactoringStatus();
		String value = arguments.get(ORIGINAL_TEXT);
		if (value != null) {
			// I'm not sure I need to do something here, so let's do nothing instead :)
		}
		value = arguments.get(NEWNAME);
		if (value != null) {
			this.setNewName(value);
		}
		return status;
	}

	/**
	 * Sets the value of the replacing text.
	 * 
	 * @param text
	 *            The nex text
	 * @return The refactoring status
	 */
	public RefactoringStatus setNewName(String text) {
		if (text == null || text.length() == 0) {
			return RefactoringStatus.createErrorStatus(invalidText);
		}
		this.newName = text;
		return new RefactoringStatus();
	}

	/**
	 * Returns the original text.
	 * 
	 * @return the original text
	 */
	public String getText() {
		return this.textSelection.getText();
	}

	/**
	 * Sets the text selecton.
	 * 
	 * @param selection
	 *            The text selection
	 */
	public void setSelection(ITextSelection selection) {
		this.textSelection = selection;
	}

}
