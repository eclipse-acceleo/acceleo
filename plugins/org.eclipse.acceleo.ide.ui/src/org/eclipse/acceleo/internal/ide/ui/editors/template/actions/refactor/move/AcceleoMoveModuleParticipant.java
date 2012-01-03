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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.move;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

/**
 * The Acceleo move module participant.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoMoveModuleParticipant extends MoveParticipant {

	/**
	 * The file moved.
	 */
	private IFile file;

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
			this.file = (IFile)element;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		return AcceleoUIMessages.getString("AcceleoEditorRenameModuleRefactoring.MoveModuleParticipant"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
	 *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor monitor) throws CoreException, OperationCanceledException {
		if (this.file != null) {
			return new AcceleoMoveModuleChange(this.file.getProject());
		}
		return null;
	}
}
