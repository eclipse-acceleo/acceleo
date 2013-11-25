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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferenceEntry;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.ChangeDescriptor;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringChangeDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.search.ui.text.Match;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class is the central class in the Rename Variable refactoring process.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameVariableRefactoring extends Refactoring {

	/**
	 * Variable.
	 */
	private static final String VARIABLE = "variable"; //$NON-NLS-1$

	/**
	 * New name.
	 */
	private static final String NEWNAME = "newName"; //$NON-NLS-1$

	/**
	 * The name of the new variable.
	 */
	protected String fNewVariableName;

	/**
	 * The current variable.
	 */
	protected AcceleoPositionedVariable fVariable;

	/**
	 * The files that will be impacted by the changes and the changes.
	 */
	private Map<IFile, TextFileChange> fChanges;

	/**
	 * The title of the refactoring.
	 */
	private final String title = AcceleoUIMessages
			.getString("AcceleoEditorRenameVariableRefactoring.RenameVariableTitle"); //$NON-NLS-1$

	/**
	 * The file name.
	 */
	private String fileName;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			monitor.beginTask(AcceleoUIMessages
					.getString("AcceleoEditorRenameRefactoring.CheckingPreconditions"), 1); //$NON-NLS-1$

			if (this.fVariable == null) {
				status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
						.getString("AcceleoEditorRenameVariableRefactoring.NoVariableSpecified"))); //$NON-NLS-1$
			}

			fChanges = new LinkedHashMap<IFile, TextFileChange>();
			this.putChangesOfTheCurrentFile();

		} finally {
			monitor.done();
		}
		return status;
	}

	/**
	 * Find the change that are in the current file and put them in the map.
	 */
	private void putChangesOfTheCurrentFile() {
		TextFileChange tfc = null;
		MultiTextEdit edit = null;
		IFile file = null;
		for (Match match : this.fVariable.getVariableMatches()) {
			final ReferenceEntry entry = (ReferenceEntry)match.getElement();
			if (entry.getTemplateFile().getName().equals(fileName)) {
				file = entry.getTemplateFile();

				if (this.fChanges.containsKey(file)
						&& this.fChanges.get(file).getEdit() instanceof MultiTextEdit) {
					tfc = this.fChanges.get(file);
					edit = (MultiTextEdit)this.fChanges.get(file).getEdit();
				} else {
					tfc = new TextFileChange(this.title, file);
					edit = new MultiTextEdit();
					tfc.setEdit(edit);
					tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
				}

				final String str = ((ReferenceEntry)match.getElement()).getMessage();
				int offset = str.indexOf(this.fVariable.getVariableName());

				if (edit != null) {
					edit.addChild(new ReplaceEdit(match.getOffset() + offset, this.fVariable
							.getVariableName().length(), this.fNewVariableName));
					this.fChanges.put(file, tfc);
				}
			}
		}

		if (this.fVariable.getVariable().eContainer() != null
				&& this.fVariable.getVariable().eContainer() instanceof DocumentedElement) {
			DocumentedElement documentedElement = (DocumentedElement)this.fVariable.getVariable()
					.eContainer();
			Documentation documentation = AcceleoUIDocumentationUtils
					.getDocumentationFromFile(documentedElement);
			if (documentation != null
					&& documentation.getBody().getValue().indexOf(
							IAcceleoConstants.TAG_PARAM + ' ' + this.fVariable.getVariableName()) != -1
					&& edit != null) {

				if (file != null) {
					StringBuffer content = FileContent.getFileContent(file.getLocation().toFile());
					int index = content.indexOf(IAcceleoConstants.TAG_PARAM + ' '
							+ this.fVariable.getVariableName(), documentation.getBody().getStartPosition());
					int length = (IAcceleoConstants.TAG_PARAM + ' ' + this.fVariable.getVariableName())
							.length();
					edit.addChild(new ReplaceEdit(index, length, IAcceleoConstants.TAG_PARAM + ' '
							+ this.fNewVariableName));
					this.fChanges.put(file, tfc);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		return new RefactoringStatus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		try {
			monitor.beginTask(
					AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.CreatingChanges"), 1); //$NON-NLS-1$
			final Collection<TextFileChange> changes = fChanges.values();
			final CompositeChange change = new CompositeChange(getName(), changes.toArray(new Change[changes
					.size()])) {

				@Override
				public ChangeDescriptor getDescriptor() {
					Map<String, String> arguments = new HashMap<String, String>();
					final String project = AcceleoUIMessages
							.getString("AcceleoEditorRenameVariableRefactoring.RefactoringProjectName"); //$NON-NLS-1$
					final String description = AcceleoUIMessages
							.getString("AcceleoEditorRenameVariableRefactoring.RenamingVariable") //$NON-NLS-1$
							+ " " //$NON-NLS-1$
							+ fVariable.getVariableName();
					final String comment = AcceleoUIMessages.getString(
							"AcceleoEditorRenameVariableRefactoring.RenamingVariableWithNewName", //$NON-NLS-1$
							fVariable.getVariableName(), fNewVariableName);
					arguments.put(VARIABLE, fVariable.getVariableName());
					arguments.put(NEWNAME, fNewVariableName);
					return new RefactoringChangeDescriptor(new AcceleoRenameVariableDescriptor(project,
							description, comment, arguments));
				}
			};
			return change;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Sets the file name.
	 * 
	 * @param name
	 *            The file name.
	 */
	public void setFileName(final String name) {
		this.fileName = name;
	}

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
	 * Sets the current variable.
	 * 
	 * @param currentVariable
	 *            The current variable.
	 */
	public void setVariable(final AcceleoPositionedVariable currentVariable) {
		this.fVariable = currentVariable;
	}

	/**
	 * Returns the variable.
	 * 
	 * @return The variable.
	 */
	public AcceleoPositionedVariable getVariable() {
		return this.fVariable;
	}

	/**
	 * Sets the new name of the variable.
	 * 
	 * @param text
	 *            The new name of variable.
	 * @return The refactoring status.
	 */
	public RefactoringStatus setNewVariableName(final String text) {
		this.fNewVariableName = text;
		final RefactoringStatus status = checkVariableName(this.fNewVariableName);
		status.merge(checkOverLoading());
		return status;
	}

	/**
	 * Check that the new name does not create any conflict with an existing variable.
	 * 
	 * @return The refactoring status.
	 */
	private RefactoringStatus checkOverLoading() {
		RefactoringStatus status = new RefactoringStatus();

		// TODO SBE check if a variable in the template has the same name.

		return status;
	}

	/**
	 * Checks the name of the variable.
	 * 
	 * @param name
	 *            The name of the variable.
	 * @return The refactoring status.
	 */
	private RefactoringStatus checkVariableName(final String name) {
		RefactoringStatus status = new RefactoringStatus();
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isJavaIdentifierPart(name.charAt(i))) {
				status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
						.getString("AcceleoEditorRenameVariableRefactoring.InvalidVariableName"))); //$NON-NLS-1$
			}
		}
		return status;
	}

	/**
	 * Initialize.
	 * 
	 * @param arguments
	 *            The argument of the initialization.
	 * @return The refactoring status.
	 */
	public RefactoringStatus initialize(final Map<String, String> arguments) {
		final RefactoringStatus status = new RefactoringStatus();
		String value = arguments.get(VARIABLE);
		if (value != null) {
			// I'm not sure I need to do something here, so let's do nothing instead :)
		}
		value = arguments.get(NEWNAME);
		if (value != null) {
			this.setNewVariableName(value);
		}
		return status;
	}

}
