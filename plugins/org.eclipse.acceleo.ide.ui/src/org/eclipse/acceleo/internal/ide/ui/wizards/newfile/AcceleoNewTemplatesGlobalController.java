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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

/**
 * The global controller of the 'AcceleoNewTemplatesWizard' wizard. It manages the 'master' composite and the
 * controllers of the 'details' composite. The 'master' composite contains the widgets to create and delete
 * the templates. The 'details' composite contains the widgets to manage one template settings.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class AcceleoNewTemplatesGlobalController {

	/**
	 * List of the 'details' composite controllers.
	 */
	protected List<AcceleoNewTemplatesWizardController> controllers;

	/**
	 * The current controller of the page.
	 */
	protected AcceleoNewTemplatesWizardController currentController;

	/**
	 * The 'master' composite. This composite contains the useful widgets to create and delete the templates.
	 */
	protected AbstractAcceleoNewTemplatesMasterComposite viewMasterComposite;

	/**
	 * The 'details' composite. This composite contains the useful widgets to manage one template settings.
	 */
	protected AcceleoNewTemplatesDetailsComposite viewDetailsComposite;

	/**
	 * The 'new' wizard page for Acceleo templates.
	 */
	private AcceleoNewTemplatesWizardPage page;

	/**
	 * The current selection. It was the workspace selection when the page has been opened.
	 */
	private String selectedContainer = ""; //$NON-NLS-1$

	/**
	 * The master composite of the 'AcceleoNewTemplatesWizard' wizard.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private class AcceleoNewTemplatesMasterComposite extends AbstractAcceleoNewTemplatesMasterComposite {

		/**
		 * Constructor.
		 * 
		 * @param parent
		 *            a widget which will be the parent of the new instance (cannot be null)
		 * @param controller
		 *            is the controller
		 * @param showTemplates
		 *            indicates if the templates are shown on the left of the composite
		 */
		public AcceleoNewTemplatesMasterComposite(Composite parent,
				AcceleoNewTemplatesGlobalController controller, boolean showTemplates) {
			super(parent, controller, showTemplates);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AbstractAcceleoNewTemplatesMasterComposite#addController(org.eclipse.jface.viewers.ISelection)
		 */
		@Override
		public void addController(ISelection selection) {
			CreateTemplateData data;
			if (selection instanceof IStructuredSelection
					&& ((IStructuredSelection)selection).getFirstElement() instanceof AcceleoNewTemplatesWizardController) {
				AcceleoNewTemplatesWizardController controller = (AcceleoNewTemplatesWizardController)((IStructuredSelection)selection)
						.getFirstElement();
				data = controller.getModel();
			} else {
				data = null;
			}
			addNewTemplate(data);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AbstractAcceleoNewTemplatesMasterComposite#removeController(org.eclipse.jface.viewers.ISelection)
		 */
		@Override
		public void removeController(ISelection selection) {
			if (selection instanceof StructuredSelection) {
				controllers.removeAll(((StructuredSelection)selection).toList());
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AbstractAcceleoNewTemplatesMasterComposite#acceleoSelectionChanged(org.eclipse.jface.viewers.ISelection)
		 */
		@Override
		protected void acceleoSelectionChanged(ISelection selection) {
			if (selection instanceof IStructuredSelection
					&& ((IStructuredSelection)selection).getFirstElement() instanceof AcceleoNewTemplatesWizardController) {
				currentController = (AcceleoNewTemplatesWizardController)((IStructuredSelection)selection)
						.getFirstElement();
				currentController.setViewDetailsComposite(viewDetailsComposite);
				currentController.setViewMasterComposite(viewMasterComposite);
				viewDetailsComposite.setController(currentController);
				currentController.initView();
			}
		}
	}

	/**
	 * Constructor.
	 */
	public AcceleoNewTemplatesGlobalController() {
		super();
		controllers = new ArrayList<AcceleoNewTemplatesWizardController>(10);
	}

	/**
	 * Gets the 'details' composite controllers.
	 * 
	 * @return the 'details' composite controllers
	 */
	public List<AcceleoNewTemplatesWizardController> getControllers() {
		return controllers;
	}

	/**
	 * Sets the wizard page.
	 * 
	 * @param newTemplateWizardPage
	 *            is the wizard page
	 */
	public void setWizardPage(AcceleoNewTemplatesWizardPage newTemplateWizardPage) {
		this.page = newTemplateWizardPage;
	}

	/**
	 * Set the current selected container.
	 * 
	 * @param selectedContainer
	 *            is the current selection
	 */
	public void setSelectedContainer(String selectedContainer) {
		this.selectedContainer = selectedContainer;
		viewDetailsComposite.setContainer(selectedContainer);
	}

	/**
	 * Creates the top level control for this dialog under the given parent composite.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param showTemplates
	 *            indicates if the template list is shown
	 */
	public void createView(Composite parent, boolean showTemplates) {
		if (viewMasterComposite == null) {
			viewMasterComposite = new AcceleoNewTemplatesMasterComposite(parent, this, showTemplates);
			viewMasterComposite.addController(null);
			viewMasterComposite.getTreeViewer().refresh();
		}
	}

	/**
	 * Gets the view.
	 * 
	 * @return the view
	 */
	public Composite getView() {
		return viewMasterComposite;
	}

	/**
	 * Creates a new template. It creates a new detail composite for this template and sets the current
	 * controller.
	 * 
	 * @param other
	 *            is the model to copy for the new template
	 */
	protected void addNewTemplate(CreateTemplateData other) {
		CreateTemplateData templateData;
		if (other == null) {
			templateData = initializeTemplateData();
		} else {
			templateData = copyTemplateData(other);
		}
		AcceleoNewTemplatesWizardController controller = new AcceleoNewTemplatesWizardController(templateData);
		controller.setGlobalController(this);
		// this isn't good for OSGi !!!
		if (currentController == null || controller.getClass() != currentController.getClass()) {
			controller.createView(viewMasterComposite);
			if (controller.getView() instanceof AcceleoNewTemplatesDetailsComposite) {
				viewDetailsComposite = (AcceleoNewTemplatesDetailsComposite)controller.getView();
			}
			viewMasterComposite.pack();
		} else {
			controller.setViewDetailsComposite(viewDetailsComposite);
			controller.setViewMasterComposite(viewMasterComposite);
		}
		viewDetailsComposite.setController(controller);
		viewDetailsComposite.setContainer(selectedContainer);
		viewDetailsComposite.initializeContainer();
		controllers.add(controller);
		currentController = controller;
		viewMasterComposite.getTreeViewer().refresh();
		viewMasterComposite.getTreeViewer().setSelection(new StructuredSelection(controller));
	}

	/**
	 * Creates and initializes the new model.
	 * 
	 * @return the new model
	 */
	private CreateTemplateData initializeTemplateData() {
		CreateTemplateData data = new CreateTemplateData();
		if (controllers.size() == 0) {
			data.setTemplateShortName(AcceleoNewTemplatesWizardPage.DEFAULT_TEMPLATE_NAME);
		} else {
			data.setTemplateShortName(AcceleoNewTemplatesWizardPage.DEFAULT_TEMPLATE_NAME
					+ (controllers.size() + 1));
		}
		data.setTemplateMetamodel(AcceleoNewTemplatesWizardPage.DEFAULT_METAMODEL_URI);
		data.setTemplateFileType(AcceleoNewTemplatesWizardPage.DEFAULT_METAMODEL_TYPE);
		data.setTemplateHasFileBlock(true);
		data.setTemplateIsMain(true);
		return data;
	}

	/**
	 * Creates and initializes a new model by copying the fields of another model.
	 * 
	 * @param other
	 *            is the model to copy for the new template
	 * @return the new model
	 */
	private CreateTemplateData copyTemplateData(CreateTemplateData other) {
		CreateTemplateData data = new CreateTemplateData();
		data.setTemplateContainer(other.getTemplateContainer());
		data.setTemplateShortName(other.getTemplateShortName() + "Copy"); //$NON-NLS-1$
		data.setTemplateMetamodel(other.getTemplateMetamodel());
		data.setTemplateFileType(other.getTemplateFileType());
		data.setTemplateHasFileBlock(other.getTemplateHasFileBlock());
		data.setTemplateIsMain(other.getTemplateIsMain());
		data.setTemplateIsInitialized(other.getTemplateIsInitialized());
		data.setTemplateExampleStrategy(other.getTemplateExampleStrategy());
		return data;
	}

	/**
	 * Updates the status of the page.
	 * 
	 * @param message
	 *            is the status message.
	 */
	public void updateStatus(String message) {
		page.setErrorMessage(message);
		page.setPageComplete(message == null);
	}

}
