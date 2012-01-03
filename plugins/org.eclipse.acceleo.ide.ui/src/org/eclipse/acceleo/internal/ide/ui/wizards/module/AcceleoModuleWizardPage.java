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
package org.eclipse.acceleo.internal.ide.ui.wizards.module;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * The Acceleo module wizard page.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoModuleWizardPage extends WizardPage implements IAcceleoModuleCompositeListener {

	/**
	 * The Acceleo module composite.
	 */
	private AcceleoModuleComposite acceleoModuleComposite;

	/**
	 * The container from the initialization.
	 */
	private String container = ""; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	public AcceleoModuleWizardPage() {
		super(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Name")); //$NON-NLS-1$
		setTitle(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Title1")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Description", //$NON-NLS-1$
				IAcceleoConstants.MTL_FILE_EXTENSION));
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
	}

	/**
	 * The constructor.
	 * 
	 * @param container
	 *            The container.
	 */
	public AcceleoModuleWizardPage(String container) {
		super(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Name")); //$NON-NLS-1$
		setTitle(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Title1")); //$NON-NLS-1$
		setDescription(AcceleoUIMessages.getString("AcceleoNewTemplateWizardPage.Description", //$NON-NLS-1$
				IAcceleoConstants.MTL_FILE_EXTENSION));
		setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$

		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.module.IAcceleoModuleCompositeListener#applyToStatusLine(org.eclipse.core.runtime.IStatus)
	 */
	public void applyToStatusLine(IStatus status) {
		String message = status.getMessage();
		if (message != null && message.length() == 0) {
			message = null;
		}
		switch (status.getSeverity()) {
			case IStatus.OK:
				setMessage(message, IMessageProvider.NONE);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.WARNING:
				setMessage(message, IMessageProvider.WARNING);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.INFO:
				setMessage(message, IMessageProvider.INFORMATION);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			default:
				setMessage(null);
				setErrorMessage(message);
				setPageComplete(false);
				break;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		acceleoModuleComposite = new AcceleoModuleComposite(parent, this);
		this.setControl(acceleoModuleComposite);
		if (!"".equals(container)) { //$NON-NLS-1$
			acceleoModuleComposite.setModuleContainer(container);
		}
	}

	/**
	 * Returns the Acceleo module.
	 * 
	 * @return the Acceleo module.
	 */
	public AcceleoModule getAcceleoModule() {
		return this.acceleoModuleComposite.getAcceleoModule();
	}
}
