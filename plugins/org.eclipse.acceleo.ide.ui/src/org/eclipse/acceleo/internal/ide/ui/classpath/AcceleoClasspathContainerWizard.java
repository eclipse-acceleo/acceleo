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
package org.eclipse.acceleo.internal.ide.ui.classpath;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jdt.ui.wizards.NewElementWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * The Acceleo Classpath Container wizard.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoClasspathContainerWizard extends NewElementWizardPage implements IClasspathContainerPage, IClasspathContainerPageExtension {

	/**
	 * The Java project.
	 */
	@SuppressWarnings("unused")
	private IJavaProject javaProject;

	/**
	 * The classpath entry.
	 */
	private IClasspathEntry classpathEntry;

	/**
	 * The engine button.
	 */
	private Button engineButton;

	/**
	 * The parser button.
	 */
	private Button parserButton;

	/**
	 * The constructor.
	 * 
	 * @param pageName
	 *            the page name.
	 */
	public AcceleoClasspathContainerWizard(String pageName) {
		super(pageName);
		this.classpathEntry = JavaCore
				.newContainerEntry(AcceleoClasspathContainer.ACCELEO_CLASSPATH_CONTAINER_PATH_RUNTIME);
	}

	/**
	 * The constructor.
	 */
	public AcceleoClasspathContainerWizard() {
		super(AcceleoUIMessages.getString("AcceleoClasspathContainerWizard.PageName")); //$NON-NLS-1$
		setTitle(AcceleoUIMessages.getString("AcceleoClasspathContainerWizard.PageTitle")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoClasspathContainerWizard.PageDescription")); //$NON-NLS-1$
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$

		this.classpathEntry = JavaCore
				.newContainerEntry(AcceleoClasspathContainer.ACCELEO_CLASSPATH_CONTAINER_PATH_RUNTIME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

		engineButton = new Button(composite, SWT.CHECK);
		engineButton.setLayoutData(gridData);
		engineButton.setText(AcceleoUIMessages.getString("AcceleoClasspathContainerWizard.AcceleoEngine")); //$NON-NLS-1$

		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		ToolBar result = new ToolBar(composite, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		ToolItem item = new ToolItem(result, SWT.NONE);
		item.setImage(image);
		item.setToolTipText(AcceleoUIMessages
				.getString("AcceleoClasspathContainerWizard.AcceleoEngineDescription")); //$NON-NLS-1$

		parserButton = new Button(composite, SWT.CHECK);
		parserButton.setLayoutData(gridData);
		parserButton.setText(AcceleoUIMessages.getString("AcceleoClasspathContainerWizard.AcceleoParser")); //$NON-NLS-1$

		image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_LCL_LINKTO_HELP);
		result = new ToolBar(composite, SWT.FLAT | SWT.NO_FOCUS);
		result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		item = new ToolItem(result, SWT.NONE);
		item.setImage(image);
		item.setToolTipText(AcceleoUIMessages
				.getString("AcceleoClasspathContainerWizard.AcceleoParserDescription")); //$NON-NLS-1$

		setControl(composite);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPage#finish()
	 */
	public boolean finish() {
		try {
			IJavaProject[] javaProjects = new IJavaProject[] {getPlaceholderProject() };
			IClasspathContainer[] containers = {null };

			if (engineButton.getSelection() && !parserButton.getSelection()) {
				this.classpathEntry = JavaCore
						.newContainerEntry(AcceleoClasspathContainer.ACCELEO_CLASSPATH_CONTAINER_PATH_ENGINE);
			} else if (!engineButton.getSelection() && parserButton.getSelection()) {
				this.classpathEntry = JavaCore
						.newContainerEntry(AcceleoClasspathContainer.ACCELEO_CLASSPATH_CONTAINER_PATH_PARSER);
			}

			JavaCore.setClasspathContainer(this.classpathEntry.getPath(), javaProjects, containers, null);
		} catch (JavaModelException e) {
			AcceleoUIActivator.log(e, true);
			return false;
		}
		return true;
	}

	/**
	 * Returns a placeholder project.
	 * 
	 * @return A placeholder project.
	 */
	private static IJavaProject getPlaceholderProject() {
		String name = "####internal"; //$NON-NLS-1$
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		while (true) {
			IProject project = root.getProject(name);
			if (!project.exists()) {
				return JavaCore.create(project);
			}
			name += '1';
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPage#getSelection()
	 */
	public IClasspathEntry getSelection() {
		return this.classpathEntry;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPage#setSelection(org.eclipse.jdt.core.IClasspathEntry)
	 */
	public void setSelection(IClasspathEntry containerEntry) {
		if (containerEntry != null) {
			this.classpathEntry = containerEntry;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension#initialize(org.eclipse.jdt.core.IJavaProject,
	 *      org.eclipse.jdt.core.IClasspathEntry[])
	 */
	public void initialize(IJavaProject project, IClasspathEntry[] currentEntries) {
		this.javaProject = project;
	}
}
