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

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AcceleoRefactoringUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * This class will launch the PullUp refactoring. This refactoring will allow the user to create a new module
 * that the original module will extend and the template that we will have pull up will be overridden by the
 * original template.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpAction extends AbstractRefactoringAction {

	/**
	 * The constructor.
	 */
	public AcceleoPullUpAction() {
		super(false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringAction#launchRefactoring()
	 */
	@Override
	protected void launchRefactoring() {
		this.name = AcceleoUIMessages.getString("AcceleoEditorPullUpRefactoring.RefactoringTitle"); //$NON-NLS-1$

		if (this.fWindow != null && AcceleoRefactoringUtils.allResourceSaved() && !this.editor.isDirty()) {
			IFile file = this.editor.getFile();
			Module module = AcceleoRefactoringUtils.getModuleFromFile(file);
			if (module != null) {
				Refactoring refactoring = new AcceleoPullUpRefactoring(module, this.name, file);
				RefactoringWizard wizard = new AcceleoPullUpWizard(refactoring, this.name);
				this.runWizard(wizard, this.fWindow.getShell(), this.name);
			}
		}
	}
}
