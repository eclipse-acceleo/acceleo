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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * This class represent the wizard that will execute the pull up refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpWizard extends RefactoringWizard {

	/**
	 * The constructor.
	 * 
	 * @param refactoring
	 *            The refactoring.
	 * @param name
	 *            The name of the wizard.
	 */
	public AcceleoPullUpWizard(Refactoring refactoring, String name) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
	 */
	@Override
	protected void addUserInputPages() {
		Refactoring refactoring = this.getRefactoring();
		if (refactoring instanceof AcceleoPullUpRefactoring) {
			AcceleoPullUpRefactoring acceleoPullUpRefactoring = (AcceleoPullUpRefactoring)refactoring;
			Module module = acceleoPullUpRefactoring.getModule();
			this.addPage(new AcceleoPullUpFirstInputWizardPage(AcceleoUIMessages
					.getString("AcceleoEditorPullUpRefactoring.PullUpFirstInputWizardPage"), module)); //$NON-NLS-1$
			this.addPage(new AcceleoPullUpSecondInputWizardPage(AcceleoUIMessages
					.getString("AcceleoEditorPullUpRefactoring.PullUpSecondInputWizardPage"))); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		Refactoring refactoring = this.getRefactoring();
		if (refactoring instanceof AcceleoPullUpRefactoring) {
			AcceleoPullUpRefactoring acceleoPullUpRefactoring = (AcceleoPullUpRefactoring)refactoring;
			IContainer iContainer = acceleoPullUpRefactoring.getContainer();
			String fileName = acceleoPullUpRefactoring.getFileName();
			if (iContainer == null || fileName == null) {
				return super.performCancel();
			}
			IResource member = iContainer.findMember(fileName);
			if (member instanceof IFile && acceleoPullUpRefactoring.isPullUpInNewFile()) {
				try {
					member.delete(true, new NullProgressMonitor());
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
		}
		return super.performCancel();
	}

}
