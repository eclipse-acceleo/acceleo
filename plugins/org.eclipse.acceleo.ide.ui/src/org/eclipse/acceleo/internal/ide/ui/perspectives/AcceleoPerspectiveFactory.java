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
package org.eclipse.acceleo.internal.ide.ui.perspectives;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

/**
 * The Acceleo perspective factory generates the initial Acceleo page layout and visible action set for the
 * page.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoPerspectiveFactory implements IPerspectiveFactory {

	/**
	 * Constructor.
	 */
	public AcceleoPerspectiveFactory() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {
		final float oneQuarter = 0.25f;
		final float threeQuarter = 0.75f;

		String editorArea = layout.getEditorArea();

		IFolderLayout leftFolder = layout.createFolder("left", IPageLayout.LEFT, oneQuarter, editorArea); //$NON-NLS-1$
		leftFolder.addView(JavaUI.ID_PACKAGES);

		IFolderLayout bottomFolder = layout.createFolder(
				"bottom", IPageLayout.BOTTOM, threeQuarter, editorArea); //$NON-NLS-1$
		bottomFolder.addView("org.eclipse.acceleo.ui.interpreter.view"); //$NON-NLS-1$
		bottomFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottomFolder.addView("org.eclipse.pde.runtime.LogView"); //$NON-NLS-1$
		bottomFolder.addPlaceholder(NewSearchUI.SEARCH_VIEW_ID);
		bottomFolder.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		bottomFolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);

		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, threeQuarter, editorArea);

		layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
		layout.addActionSet(JavaUI.ID_ACTION_SET);
		layout.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);

		// views - acceleo
		layout.addShowViewShortcut("org.eclipse.acceleo.ide.ui.views.result.AcceleoResultView"); //$NON-NLS-1$
		layout.addShowViewShortcut("org.eclipse.acceleo.ide.ui.views.overrides.OverridesBrowser"); //$NON-NLS-1$
		layout.addShowViewShortcut("org.eclipse.acceleo.ide.ui.views.proposals.ProposalsBrowser"); //$NON-NLS-1$
		layout.addShowViewShortcut("org.eclipse.acceleo.ui.interpreter.view"); //$NON-NLS-1$

		// views - java
		layout.addShowViewShortcut(JavaUI.ID_PACKAGES);

		// views - console
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);

		// views - workbench
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);

		// actions - creation wizard
		layout.addNewWizardShortcut("org.eclipse.acceleo.ide.ui.wizards.newfile.AcceleoModuleWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.acceleo.ide.ui.wizards.newfile.AcceleoNewTemplatesWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.acceleo.ide.ui.wizards.newfile.main.AcceleoNewMainTemplateWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.acceleo.ide.ui.wizards.newproject.AcceleoNewProjectWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.acceleo.ide.ui.wizards.newproject.AcceleoNewProjectUIWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.acceleo.ide.ui.wizards.newproject.AcceleoConvertProjectWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file"); //$NON-NLS-1$
		layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard"); //$NON-NLS-1$

		layout.addPerspectiveShortcut("org.eclipse.acceleo.ide.ui.AcceleoPerspective"); //$NON-NLS-1$
	}

}
