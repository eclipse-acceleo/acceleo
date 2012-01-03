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

import java.util.EventObject;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Controller which manage one 'details' composite view and its model.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewTemplatesWizardController {

	/**
	 * Indicates if the template name has manually changed.
	 */
	protected boolean templateNameManualChange;

	/**
	 * The 'master' composite. This composite contains the useful widgets to create and delete the templates.
	 */
	private AbstractAcceleoNewTemplatesMasterComposite viewMasterComposite;

	/**
	 * The model.
	 */
	private CreateTemplateData model;

	/**
	 * The 'details' composite. This composite contains the useful widgets to manage one template settings.
	 */
	private AcceleoNewTemplatesDetailsComposite viewDetailsComposite;

	/**
	 * The global controller.
	 */
	private AcceleoNewTemplatesGlobalController globalController;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            the model to configure a new template
	 */
	public AcceleoNewTemplatesWizardController(CreateTemplateData model) {
		this.model = model;
	}

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public CreateTemplateData getModel() {
		return model;
	}

	/**
	 * Sets the 'master' composite. This composite contains the useful widgets to create and delete the
	 * templates.
	 * 
	 * @param viewMasterComposite
	 *            is the 'master' composite
	 */
	public void setViewMasterComposite(AbstractAcceleoNewTemplatesMasterComposite viewMasterComposite) {
		this.viewMasterComposite = viewMasterComposite;
	}

	/**
	 * Sets the 'details' composite. This composite contains the useful widgets to manage one template
	 * settings.
	 * 
	 * @param viewDetailsComposite
	 *            is the 'details' composite
	 */
	public void setViewDetailsComposite(AcceleoNewTemplatesDetailsComposite viewDetailsComposite) {
		this.viewDetailsComposite = viewDetailsComposite;
	}

	/**
	 * Sets the global controller.
	 * 
	 * @param globalController
	 *            the global controller
	 */
	public void setGlobalController(AcceleoNewTemplatesGlobalController globalController) {
		this.globalController = globalController;
	}

	/**
	 * Creates the top level control for this 'details' composite under the given parent composite.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	public void createView(Composite parent) {
		viewDetailsComposite = new AcceleoNewTemplatesDetailsComposite(parent, SWT.NONE);
		viewMasterComposite = (AbstractAcceleoNewTemplatesMasterComposite)parent;
	}

	/**
	 * Gets the view.
	 * 
	 * @return the view
	 */
	public Composite getView() {
		return viewDetailsComposite;
	}

	/**
	 * Fire property change event. A template information has been modified.
	 * 
	 * @param e
	 *            the event object
	 * @param eventType
	 *            the event type
	 */
	public void firePropertiesChanged(EventObject e, int eventType) {
		if (e instanceof ModifyEvent) {
			String text = ""; //$NON-NLS-1$
			if (e.getSource() instanceof Text) {
				text = ((Text)e.getSource()).getText();
			} else if (e.getSource() instanceof Combo) {
				text = ((Combo)e.getSource()).getText();
			}
			if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_NAME) {
				setTemplateName(text);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_PARENT_FOLDER) {
				model.setTemplateContainer(text);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_METAMODEL_URI) {
				model.setTemplateMetamodel(text);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_EXAMPLE_PATH) {
				model.setTemplateExamplePath(text);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_METAMODEL_TYPE) {
				model.setTemplateFileType(text);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_EXAMPLE_STRATEGY) {
				model.setTemplateExampleStrategy(text);
			}
		}
		if (e instanceof SelectionEvent) {
			String text = ""; //$NON-NLS-1$
			if (e.getSource() instanceof Combo) {
				text = ((Combo)e.getSource()).getText();
			}
			if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_METAMODEL_TYPE) {
				model.setTemplateFileType(text);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_IS_FILE) {
				model.setTemplateHasFileBlock(((Button)e.getSource()).getSelection());
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_HAS_MAIN) {
				model.setTemplateIsMain(((Button)e.getSource()).getSelection());
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_IS_INITIALISED) {
				model.setTemplateIsInitialized(((Button)e.getSource()).getSelection());
				String description;
				String examplePath;
				if (((Button)e.getSource()).getSelection()
						&& viewDetailsComposite.getTemplateExampleStrategy() != null) {
					description = viewDetailsComposite.getTemplateExampleStrategy().getDescription();
					examplePath = viewDetailsComposite.getTemplateExample();
				} else {
					description = ""; //$NON-NLS-1$
					examplePath = ""; //$NON-NLS-1$
				}
				model.setTemplateExampleStrategy(description);
				model.setTemplateExamplePath(examplePath);
			} else if (eventType == AcceleoNewTemplatesDetailsComposite.TEMPLATE_EXAMPLE_STRATEGY) {
				model.setTemplateExampleStrategy(text);
			}
		}
	}

	/**
	 * Sets the name of the template and refresh the tree viewer.
	 * 
	 * @param name
	 *            is the new template name
	 */
	private void setTemplateName(String name) {
		model.setTemplateShortName(name);
		viewMasterComposite.getTreeViewer().refresh();
	}

	/**
	 * Initializes the view.
	 */
	public void initView() {
		initView(true);
	}

	/**
	 * Initializes the view.
	 * 
	 * @param initializeTemplate
	 *            indicates if the template can be initialized with the content of an example
	 */
	public void initView(boolean initializeTemplate) {
		viewDetailsComposite.setTemplateContainer(model.getTemplateContainer());
		viewDetailsComposite.setTemplateName(model.getTemplateShortName());
		String metamodelType = model.getTemplateFileType();
		viewDetailsComposite.setMetamodelURI(model.getTemplateMetamodel());
		viewDetailsComposite.setMetamodelType(metamodelType);
		viewDetailsComposite.setTemplateHasFileButtonState(model.getTemplateHasFileBlock());
		viewDetailsComposite.setTemplateHasMainButtonState(model.getTemplateIsMain());
		viewDetailsComposite.setTemplateIsInitializeButtonState(model.getTemplateIsInitialized());
		viewDetailsComposite.setTemplateExamplePath(model.getTemplateExamplePath());
		viewDetailsComposite.setTemplateExampleStrategy(model.getTemplateExampleStrategy());
		if (!initializeTemplate) {
			viewDetailsComposite.advancedButton.setVisible(false);
		}
		dialogChanged();
	}

	/**
	 * Validates the changes on the page.
	 */
	public void dialogChanged() {
		IPath containerPath = new Path(viewDetailsComposite.getTemplateContainer());
		String fileName = viewDetailsComposite.getTemplateName();
		if (containerPath.segmentCount() == 0) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.MissingContainer")); //$NON-NLS-1$
		} else if (ResourcesPlugin.getWorkspace().getRoot().getProject(containerPath.segment(0)).exists()
				&& !ResourcesPlugin.getWorkspace().getRoot().getProject(containerPath.segment(0))
						.isAccessible()) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.ReadOnly")); //$NON-NLS-1$
		} else if (fileName.length() == 0) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.MissingFileName")); //$NON-NLS-1$
		} else if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.InvalidFileName")); //$NON-NLS-1$
		} else if (ResourcesPlugin.getWorkspace().getRoot().exists(
				containerPath.append(fileName).addFileExtension(IAcceleoConstants.MTL_FILE_EXTENSION))) {
			updateStatus(AcceleoUIMessages.getString(
					"AcceleoNewTemplateWizardPage.Error.ExistingFile", fileName)); //$NON-NLS-1$
		} else {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(containerPath.append(fileName));
			if (file.getProject().isAccessible()
					&& new AcceleoProject(file.getProject()).getOutputFilePath(file) == null) {
				updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.JavaFolder")); //$NON-NLS-1$
			} else {
				if (viewDetailsComposite.getMetamodelField().isEnabled()) {
					updateStatus(null);
				} else if (viewDetailsComposite.getMetamodelURI().length() == 0) {
					updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.MissingURI")); //$NON-NLS-1$
				} else if (!isValidMetamodelURI(viewDetailsComposite.getMetamodelURI())) {
					updateStatus(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Error.InvalidURI")); //$NON-NLS-1$
				} else {
					updateStatus(null);
				}
			}
		}
	}

	/**
	 * Indicates if the given metamodel URI exists. It cans be multiple metamodels if the value contains a
	 * comma separator. In this case, all the URIs must exist.
	 * 
	 * @param uris
	 *            the metamodel URI
	 * @return true if the given metamodel URI exists
	 */
	private boolean isValidMetamodelURI(String uris) {
		StringTokenizer st = new StringTokenizer(uris, ","); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			String uri = st.nextToken().trim();
			if (ModelUtils.getEPackage(uri) == null && !uri.endsWith(".ecore")) { //$NON-NLS-1$
				return false;
			}
		}
		return uris.trim().length() > 0;
	}

	/**
	 * Updates the status of the page.
	 * 
	 * @param message
	 *            is the error message.
	 */
	private void updateStatus(String message) {
		globalController.updateStatus(message);
	}

}
