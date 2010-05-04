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
import java.util.List;
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
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.search.ui.text.Match;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class is the central class in the Rename Query refactoring process.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameQueryRefactoring extends Refactoring {

	/**
	 * Query.
	 */
	private static final String QUERY = "query"; //$NON-NLS-1$

	/**
	 * New name.
	 */
	private static final String NEWNAME = "newName"; //$NON-NLS-1$

	/**
	 * Update references.
	 */
	private static final String REFERENCES = "references"; //$NON-NLS-1$

	/**
	 * The name of the new query.
	 */
	private String fNewQueryName;

	/**
	 * The files that will be impacted by the changes and the changes.
	 */
	private Map<IFile, TextFileChange> fChanges;

	/**
	 * Whether or not we have to updates all the references (default = true).
	 */
	private boolean fUpdateReferences = true;

	/**
	 * The current query.
	 */
	private AcceleoPositionedQuery fQuery;

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

			if (this.fQuery == null) {
				status.merge(RefactoringStatus.createErrorStatus("No query specified")); //$NON-NLS-1$
			}

			this.fQuery.computeOccurrencesOfQuery();

			fChanges = new LinkedHashMap<IFile, TextFileChange>();
			if (this.fUpdateReferences) {
				this.putChangesNotInTheCurrentFile();
			}

			this.putChangesOfTheCurrentFile();

		} finally {
			monitor.done();
		}
		return status;
	}

	/**
	 * Find the change that are <b>not</b> in the current file and put them in the map.
	 */
	private void putChangesNotInTheCurrentFile() {
		final MultiTextEdit edit = new MultiTextEdit();
		for (Iterator<Match> iterator = this.fQuery.getQueryMatches().iterator(); iterator.hasNext();) {
			final Match match = (Match)iterator.next();
			final ReferenceEntry entry = (ReferenceEntry)match.getElement();
			if (!entry.getTemplateFile().getName().equals(fileName)) {
				final IFile file = ((ReferenceEntry)match.getElement()).getTemplateFile();
				final TextFileChange tfc = new TextFileChange("Refactoring: Rename Query", file); //$NON-NLS-1$

				final String str = ((ReferenceEntry)match.getElement()).getMessage();
				int offset = str.indexOf(this.fQuery.getQueryName());

				edit.addChild(new ReplaceEdit(match.getOffset() + offset,
						this.fQuery.getQueryName().length(), this.fNewQueryName));
				tfc.setEdit(edit);

				// TODO set texttype !!!
				tfc.setTextType("mtl"); //$NON-NLS-1$

				this.fChanges.put(file, tfc);
			}
		}
	}

	/**
	 * Find the change that are in the current file and put them in the map.
	 */
	private void putChangesOfTheCurrentFile() {
		final MultiTextEdit edit = new MultiTextEdit();
		for (Iterator<Match> iterator = this.fQuery.getQueryMatches().iterator(); iterator.hasNext();) {
			final Match match = (Match)iterator.next();
			final ReferenceEntry entry = (ReferenceEntry)match.getElement();
			if (entry.getTemplateFile().getName().equals(fileName)) {
				final IFile file = ((ReferenceEntry)match.getElement()).getTemplateFile();
				final TextFileChange tfc = new TextFileChange("Refactoring: Rename Query", file); //$NON-NLS-1$

				final String str = ((ReferenceEntry)match.getElement()).getMessage();
				int offset = str.indexOf(this.fQuery.getQueryName());

				edit.addChild(new ReplaceEdit(match.getOffset() + offset,
						this.fQuery.getQueryName().length(), this.fNewQueryName));
				tfc.setEdit(edit);

				// TODO set texttype !!!
				tfc.setTextType("mtl"); //$NON-NLS-1$

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
		final RefactoringStatus status = new RefactoringStatus();

		if (AcceleoPositionedQuery.getInput().length == 0) {
			status.merge(RefactoringStatus.createErrorStatus("There are no query in the current file")); //$NON-NLS-1$
		}

		return status;
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
					final String project = "Acceleo Rename Query"; //$NON-NLS-1$
					final String description = "Renaming query " + fQuery.getQueryName(); //$NON-NLS-1$
					final String comment = "Renaming query " + fQuery.getQueryName() //$NON-NLS-1$
							+ " with the new name: " + fNewQueryName; //$NON-NLS-1$
					arguments.put(QUERY, fQuery.getQueryName());
					arguments.put(NEWNAME, fNewQueryName);
					arguments.put(REFERENCES, Boolean.valueOf(fUpdateReferences).toString());
					return new RefactoringChangeDescriptor(new AcceleoRenameQueryDescriptor(project,
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
		return "Rename Query"; //$NON-NLS-1$
	}

	/**
	 * Sets the current query.
	 * 
	 * @param currentQuery
	 *            The current query.
	 */
	public void setQuery(final AcceleoPositionedQuery currentQuery) {
		this.fQuery = currentQuery;
	}

	/**
	 * Returns the query.
	 * 
	 * @return The query.
	 */
	public AcceleoPositionedQuery getQuery() {
		return this.fQuery;
	}

	/**
	 * Indicates whether or not we should update all the occurrences of the query name.
	 * 
	 * @param selection
	 *            true if we should update the selection.
	 */
	public void setUpdateReferences(final boolean selection) {
		this.fUpdateReferences = selection;
	}

	/**
	 * Sets the new name of the query.
	 * 
	 * @param text
	 *            The new name of query.
	 * @return The refactoring status.
	 */
	public RefactoringStatus setNewQueryName(final String text) {
		this.fNewQueryName = text;
		final RefactoringStatus status = checkQueryName(this.fNewQueryName);
		status.merge(checkOverLoading());
		return status;
	}

	/**
	 * Check that the new name does not create any conflict with an existing query.
	 * 
	 * @return The refactoring status.
	 */
	private RefactoringStatus checkOverLoading() {
		RefactoringStatus status = new RefactoringStatus();

		final AcceleoPositionedQuery[] array = AcceleoPositionedQuery.getInput();
		for (int i = 0; i < array.length; i++) {
			if (this.fNewQueryName.equals(array[i].getQueryName())) {

				List<Variable> listOfParametersOfTheCurrentQuery = this.fQuery.getQuery().getParameter();
				List<Variable> listOfParametersOfTheQueryWithTheSameName = array[i].getQuery().getParameter();

				if (listOfParametersOfTheCurrentQuery.size() == listOfParametersOfTheQueryWithTheSameName
						.size()) {
					boolean overloadingError = true;

					for (int j = 0; j < listOfParametersOfTheCurrentQuery.size(); j++) {
						if (!listOfParametersOfTheCurrentQuery.get(j).getEType().equals(
								listOfParametersOfTheQueryWithTheSameName.get(j).getEType())) {
							overloadingError = false;
							break;
						}
					}

					if (overloadingError) {
						status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
								.getString("AcceleoEditorRenameQueryRefactoring.QueryOverloadingError"))); //$NON-NLS-1$
					}
				}
			}
		}

		return status;
	}

	/**
	 * Checks the name of the query.
	 * 
	 * @param name
	 *            The name of the query.
	 * @return The refactoring status.
	 */
	private RefactoringStatus checkQueryName(final String name) {
		RefactoringStatus status = new RefactoringStatus();
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isJavaIdentifierPart(name.charAt(i))) {
				status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
						.getString("AcceleoEditorRenameQueryRefactoring.InvalidQueryName"))); //$NON-NLS-1$
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
		String value = (String)arguments.get(QUERY);
		if (value != null) {
			// I'm not sure I need to do something here, so let's do nothing instead :)
		}
		value = (String)arguments.get(NEWNAME);
		if (value != null) {
			this.setNewQueryName(value);
		}
		value = (String)arguments.get(REFERENCES);
		if (value != null) {
			setUpdateReferences(Boolean.valueOf(value).booleanValue());
		}
		return status;
	}
}
