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
package org.eclipse.acceleo.internal.ide.ui.builders.prefs;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoBuilderSettings;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;

/**
 * The preference page to configure the Acceleo compiler. We can define for instance the standard compliance
 * mode : full or pragmatic.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompilerPage extends PreferencePage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	/**
	 * The current selection in the worbench. It should be an IProject element.
	 */
	private IAdaptable element;

	/**
	 * The widget to check or not the pragmatic compliant mode (opposite of the fully OMG compliant mode).
	 */
	private Button pragmaticCompliance;

	/**
	 * Constructor.
	 */
	public AcceleoCompilerPage() {
		super();
		setDescription(AcceleoUIMessages.getString("AcceleoCompilerPage.Description")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		if (element instanceof IProject) {
			IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			if (pragmaticCompliance.getSelection()) {
				settings.setCompliance(AcceleoBuilderSettings.BUILD_PRAGMATIC_COMPLIANCE);
			} else {
				settings.setCompliance(AcceleoBuilderSettings.BUILD_FULL_OMG_COMPLIANCE);
			}
			try {
				settings.save();
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return super.performOk();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	public IAdaptable getElement() {
		return element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		this.element = element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		createComplianceGroup(composite);
		return composite;
	}

	/**
	 * Creates a group for compliance settings.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createComplianceGroup(Composite parent) {
		pragmaticCompliance = new Button(parent, SWT.CHECK);
		pragmaticCompliance.setText(AcceleoUIMessages.getString("AcceleoCompilerPage.PragmaticCompliance")); //$NON-NLS-1$
		GridData gridData = new GridData();
		pragmaticCompliance.setLayoutData(gridData);
		if (element instanceof IProject) {
			IProject project = (IProject)element;
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			if (AcceleoBuilderSettings.BUILD_FULL_OMG_COMPLIANCE == settings.getCompliance()) {
				pragmaticCompliance.setSelection(false);
			} else if (AcceleoBuilderSettings.BUILD_PRAGMATIC_COMPLIANCE == settings.getCompliance()) {
				pragmaticCompliance.setSelection(true);
			} else {
				pragmaticCompliance.setSelection(true);
			}
		} else {
			pragmaticCompliance.setSelection(true);
		}
	}
}
