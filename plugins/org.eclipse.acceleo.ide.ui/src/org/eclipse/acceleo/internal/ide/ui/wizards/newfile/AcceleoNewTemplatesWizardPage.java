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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * This is a 'new' wizard page for Acceleo templates. Its role is to create a new Acceleo file resource in the
 * provided container. If the container resource (a folder or a project) is selected in the workspace when the
 * wizard page is opened, it will accept it as the target container. The wizard page creates one file with the
 * extension "mtl". You can create more than one template with this page. You just have to indicate
 * (constructor) that you want to show the templates on the left of the page. This page is composed of 2
 * composites : the 'master' composite and the 'details' composite. The 'master' composite contains the
 * widgets to create and delete the templates. The 'details' composite contains the widgets to manage one
 * template settings.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewTemplatesWizardPage extends WizardPage {

	/**
	 * Default text in the 'Metamodel URI' text widget.
	 */
	public static final String DEFAULT_METAMODEL_URI = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$

	/**
	 * Default text in the "Template Name" text widget.
	 */
	public static final String DEFAULT_TEMPLATE_NAME = "generate"; //$NON-NLS-1$

	/**
	 * Default text in the "Template Example Path" text widget.
	 */
	public static final String DEFAULT_EXAMPLE_PATH = ""; //$NON-NLS-1$

	/**
	 * Default text in the "metamodelType" combo widget.
	 */
	public static final String DEFAULT_METAMODEL_TYPE = "Class"; //$NON-NLS-1$

	/**
	 * The workspace selection when the page has been opened.
	 */
	private String selectedContainer;

	/**
	 * The controllers of the 'details' composite.
	 */
	private List<AcceleoNewTemplatesWizardController> controllers;

	/**
	 * The global controller of the page.
	 */
	private AcceleoNewTemplatesGlobalController globalController;

	/**
	 * Indicates if we show the template on the left of the page. If not, it means that a single template can
	 * be created.
	 */
	private boolean showTemplates;

	/**
	 * Constructor.
	 * 
	 * @param selectedContainer
	 *            is the containing resource path in the workspace (can be null)
	 * @param showTemplates
	 *            indicates if we show the templates on the left of the page
	 */
	public AcceleoNewTemplatesWizardPage(String selectedContainer, boolean showTemplates) {
		super(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Name")); //$NON-NLS-1$
		this.showTemplates = showTemplates;
		this.selectedContainer = selectedContainer;
		controllers = new ArrayList<AcceleoNewTemplatesWizardController>();
		setTitle(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Title1")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Description", //$NON-NLS-1$
				IAcceleoConstants.MTL_FILE_EXTENSION));
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
	}

	/**
	 * One listener is notified when the current page of a wizard is changing. This method is called when a
	 * new 'template' page is opened.
	 * 
	 * @param previousPage
	 *            is the previous page of the wizard
	 */
	public void handleNewTemplatePageChanging(WizardPage previousPage) {
		if (previousPage instanceof WizardNewProjectCreationPage) {
			String projectName = ((WizardNewProjectCreationPage)previousPage).getProjectName();
			selectedContainer = new Path("/" + projectName).append("/src/").append(//$NON-NLS-1$ //$NON-NLS-2$
					projectName.replaceAll("\\.", "/")).append("files").toString(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			globalController.setSelectedContainer(selectedContainer);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		createMasterDetailBlock(parent);
		setControl(globalController.getView());
	}

	/**
	 * Create the 'master' composite.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	private void createMasterDetailBlock(Composite parent) {
		globalController = new AcceleoNewTemplatesGlobalController();
		globalController.setWizardPage(this);
		globalController.createView(parent, showTemplates);
		globalController.setSelectedContainer(selectedContainer);
		controllers = globalController.getControllers();
	}

	/**
	 * Gets the controllers.
	 * 
	 * @return all the controllers
	 */
	public List<AcceleoNewTemplatesWizardController> getControllers() {
		return controllers;
	}

}
