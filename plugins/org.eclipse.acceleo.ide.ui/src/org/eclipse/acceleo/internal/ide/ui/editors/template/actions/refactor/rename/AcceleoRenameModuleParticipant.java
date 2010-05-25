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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarker;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

/**
 * Participant for the rename module refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameModuleParticipant extends RenameParticipant {

	/**
	 * The MTL file.
	 */
	private IFile file;

	/**
	 * The project.
	 */
	private IProject project;

	/**
	 * The module.
	 */
	private Module module;

	/**
	 * The refactoring process.
	 */
	private AcceleoRenameModuleRefactoring refactoring;

	/**
	 * The constructor.
	 */
	public AcceleoRenameModuleParticipant() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
	 */
	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IFile
				&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)element).getFileExtension())
				&& ((IFile)element).exists()) {

			boolean result = true;
			String newName = this.getArguments().getNewName();

			if (newName.length() > 0 && newName.endsWith("." + IAcceleoConstants.MTL_FILE_EXTENSION) //$NON-NLS-1$
					&& this.getArguments().getUpdateReferences()) {
				this.file = (IFile)element;
				this.project = this.file.getProject();
				this.module = AcceleoRenameModuleUtils.getModuleFromFile(file);

				try {
					IMarker[] markers = file.findMarkers(AcceleoMarker.PROBLEM_MARKER, true,
							IResource.DEPTH_INFINITE);
					if (markers.length > 0) {
						result = false;
					} else {
						// We don't rename the file of the module because the main refactoring process will do
						// it
						this.refactoring = new AcceleoRenameModuleRefactoring(false);
					}
				} catch (CoreException e) {
					result = false;
				}
			} else {
				result = false;
			}
			return result;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		return AcceleoUIMessages.getString("AcceleoEditorRenameModuleRefactoring.RenameModuleParticipant"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
	 *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor monitor, CheckConditionsContext context)
			throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		this.refactoring.setFile(this.file);
		this.refactoring.setProject(this.project);
		this.refactoring.setModule(this.module);

		try {
			status.merge(this.refactoring.checkInitialConditions(monitor));
			String newNameWithoutExtension = this.getArguments().getNewName();
			newNameWithoutExtension = newNameWithoutExtension.substring(0, newNameWithoutExtension
					.lastIndexOf(".")); //$NON-NLS-1$
			this.refactoring.setNewModuleName(newNameWithoutExtension);
			status.merge(this.refactoring.checkFinalConditions(monitor));
		} catch (CoreException e) {
			// do nothing
		}

		return status;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createPreChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		return this.refactoring.createChange(monitor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		return null;
	}

}
