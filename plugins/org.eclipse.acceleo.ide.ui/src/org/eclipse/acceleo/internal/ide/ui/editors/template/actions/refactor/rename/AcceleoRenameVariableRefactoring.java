/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferenceEntry;
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
	private String fNewVariableName;

	/**
	 * The files that will be impacted by the changes and the changes.
	 */
	private Map<IFile, TextFileChange> fChanges;

	/**
	 * The current variable.
	 */
	private AcceleoPositionedVariable fVariable;

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
			monitor.beginTask("Checking preconditions...", 1); //$NON-NLS-1$

			if (this.fVariable == null) {
				status.merge(RefactoringStatus.createErrorStatus("No variable specified")); //$NON-NLS-1$
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
		for (Iterator<Match> iterator = this.fVariable.getVariableMatches().iterator(); iterator.hasNext();) {
			final Match match = (Match)iterator.next();
			final ReferenceEntry entry = (ReferenceEntry)match.getElement();
			if (entry.getTemplateFile().getName().equals(fileName)) {
				final IFile file = ((ReferenceEntry)match.getElement()).getTemplateFile();

				TextFileChange tfc = null;
				MultiTextEdit edit = null;

				if (this.fChanges.containsKey(file)
						&& this.fChanges.get(file).getEdit() instanceof MultiTextEdit) {
					tfc = this.fChanges.get(file);
					edit = (MultiTextEdit)this.fChanges.get(file).getEdit();
				} else {
					tfc = new TextFileChange("Refactoring: Rename Variable", file); //$NON-NLS-1$
					edit = new MultiTextEdit();
					tfc.setEdit(edit);
					tfc.setTextType("mtl"); //$NON-NLS-1$
				}

				final String str = ((ReferenceEntry)match.getElement()).getMessage();
				int offset = str.indexOf(this.fVariable.getVariableName());

				edit.addChild(new ReplaceEdit(match.getOffset() + offset, this.fVariable.getVariableName()
						.length(), this.fNewVariableName));

				this.fChanges.put(file, tfc);
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
			monitor.beginTask("Creating change...", 1); //$NON-NLS-1$
			final Collection<TextFileChange> changes = fChanges.values();
			final CompositeChange change = new CompositeChange(getName(), changes.toArray(new Change[changes
					.size()])) {

				@Override
				public ChangeDescriptor getDescriptor() {
					Map<String, String> arguments = new HashMap<String, String>();
					final String project = "Acceleo Rename Variable"; //$NON-NLS-1$
					final String description = "Renaming variable " + fVariable.getVariableName(); //$NON-NLS-1$
					final String comment = "Renaming variable " + fVariable.getVariableName() //$NON-NLS-1$
							+ " with the new name: " + fNewVariableName; //$NON-NLS-1$
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
		return "Rename Variable"; //$NON-NLS-1$
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

		// TODO !!!

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
		String value = (String)arguments.get(VARIABLE);
		if (value != null) {
			// I'm not sure I need to do something here, so let's do nothing instead :)
		}
		value = (String)arguments.get(NEWNAME);
		if (value != null) {
			this.setNewVariableName(value);
		}
		return status;
	}

}
